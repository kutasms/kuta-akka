package com.kuta.base.database.mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import com.kuta.base.util.KSFStringUtil;
import com.kuta.base.util.KSFUtil;

/**
 * MYSQL数据导入REDIS的SQL文件生成工具
 * */
public class RedisImportSqlFileGenerator {
	
	/**
	 * 获取类定义的所有非static变量名
	 * @param clazz mybatis生成的实体类型
	 * @return 获取的字段集合
	 * */
	private static <T> List<String> getFileds(Class<T> clazz){
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		List<String> names = new ArrayList<String>();
		Arrays.asList(fields).forEach(field ->{
			boolean isStatic = Modifier.isStatic(field.getModifiers());
			if(!isStatic) {
				names.add(field.getName());
			}
		});
		return names;
	}
	
	/**
	 * 生成带where语句的sql文件
	 * @param clazz mybatis生成的实体类型
	 * @param cacheKey 缓存键
	 * @param primaryKey 主键名称
	 * @throws IOException 当出现IO错误时上报异常
	 * */
	public static <T> void generateContainsWhere(Class<T> clazz,String cacheKey,String primaryKey) throws IOException {
		String path = RedisImportSqlFileGenerator.class.getClassLoader().getResource("").getPath();
		generateContainsWhere(clazz,path,cacheKey,primaryKey);
	}
	/**
	 * 生成带where语句的sql文件
	 * @param clazz mybatis生成的实体类型
	 * @param outPath sql文件输出路径
	 * @param cacheKey 缓存键
	 * @param primaryKey 主键名称
	 * @throws IOException 当出现IO错误时上报异常
	 * */
	public static <T> void generateContainsWhere(Class<T> clazz,String outPath,String cacheKey,String primaryKey) throws IOException {
		List<String> fieldNames = getFileds(clazz);
		String path = outPath + clazz.getSimpleName() + ".sql";
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT CONCAT(\r\n");
		builder.append(String.format("\"*%s\\r\\n\",\r\n", 2 + fieldNames.size() * 2));
		builder.append("'$', LENGTH(redis_cmd), '\\r\\n',redis_cmd, '\\r\\n','$', LENGTH(redis_key), '\\r\\n',redis_key, '\\r\\n',\r\n");

		for(int i=0;i<fieldNames.size();i++) {
			if(i < fieldNames.size()-1) {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r\\n'\r\n", 
						fieldNames.get(i),fieldNames.get(i),fieldNames.get(i),fieldNames.get(i)));
			}
			else {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r')\r\n", 
						fieldNames.get(i),fieldNames.get(i),fieldNames.get(i),fieldNames.get(i)));
			}
		}
		builder.append("FROM (\r\n" + 
				"SELECT\r\n" + 
				"'HMSET' AS redis_cmd, \r\n");
		builder.append(String.format("CONCAT('%s_',%s) AS redis_key,\r\n", clazz.getSimpleName().toLowerCase(), cacheKey));
		
		for(int i=0;i<fieldNames.size();i++) {
			String underLineName = KSFStringUtil.humpToUnderline(fieldNames.get(i));
			if(i < fieldNames.size()-1) {
				builder.append(String.format("'%s' AS k_%s,`%s` AS v_%s,\r\n", 
						fieldNames.get(i),
						fieldNames.get(i),
						underLineName,
						fieldNames.get(i)));
			}
			else {
				builder.append(String.format("'%s' AS k_%s,`%s` AS v_%s\r\n", 
						fieldNames.get(i),
						fieldNames.get(i),
						underLineName,
						fieldNames.get(i)));
			}
		}
		builder.append(String.format(" FROM %s where %s>${1} && %s<=${2}\r\n",clazz.getSimpleName(), primaryKey,primaryKey) + 
				" ) AS t");
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(writer);
			out.write(builder.toString());
		}
		finally {
			out.close();
			writer.close();
		}
	}
	
	/**
	 * 生成sql文件
	 * @param clazz mybatis生成的实体类型
	 * @param cacheKey 缓存键
	 * @param primaryKey 主键名称
	 * @throws IOException 当出现IO错误时上报异常
	 * */
	public static <T> void generate(Class<T> clazz,String cacheKey,String primaryKey) throws IOException {
		String path = RedisImportSqlFileGenerator.class.getClassLoader().getResource("").getPath();
		generate(clazz,path,cacheKey,primaryKey);
	}
	
	/**
	 * 生成sql文件
	 * @param clazz mybatis生成的实体类型
	 * @param outPath sql文件输出路径
	 * @param cacheKey 缓存键
	 * @param primaryKey 主键名称
	 * @throws IOException 当出现IO错误时上报异常
	 * */
	public static <T> void generate(Class<T> clazz,String outPath,String cacheKey,String primaryKey) throws IOException {
		List<String> fieldNames = getFileds(clazz);
		String path = outPath + clazz.getSimpleName() + ".sql";
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT CONCAT(\r\n");
		builder.append(String.format("\"*%s\\r\\n\",\r\n", 2 + fieldNames.size() * 2));
		builder.append("'$', LENGTH(redis_cmd), '\\r\\n',redis_cmd, '\\r\\n','$', LENGTH(redis_key), '\\r\\n',redis_key, '\\r\\n',\r\n");

		for(int i=0;i<fieldNames.size();i++) {
			if(i < fieldNames.size()-1) {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r\\n'\r\n", 
						fieldNames.get(i),fieldNames.get(i),fieldNames.get(i),fieldNames.get(i)));
			}
			else {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r')\r\n", 
						fieldNames.get(i),fieldNames.get(i),fieldNames.get(i),fieldNames.get(i)));
			}
		}
		builder.append("FROM (\r\n" + 
				"SELECT\r\n" + 
				"'HMSET' AS redis_cmd, \r\n");
		builder.append(String.format("CONCAT('%s_',%s) AS redis_key,\r\n", clazz.getSimpleName().toLowerCase(), cacheKey));
		
		for(int i=0;i<fieldNames.size();i++) {
			String underLineName = KSFStringUtil.humpToUnderline(fieldNames.get(i));
			if(i < fieldNames.size()-1) {
				builder.append(String.format("'%s' AS k_%s,`%s` AS v_%s,\r\n", 
						fieldNames.get(i),
						fieldNames.get(i),
						underLineName,
						fieldNames.get(i)));
			}
			else {
				builder.append(String.format("'%s' AS k_%s,`%s` AS v_%s\r\n", 
						fieldNames.get(i),
						fieldNames.get(i),
						underLineName,
						fieldNames.get(i)));
			}
		}
		builder.append(String.format(" FROM %s \r\n",clazz.getSimpleName(), primaryKey,primaryKey) + 
				" ) AS t");
		File file = new File(path);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(writer);
			out.write(builder.toString());
		}
		finally {
			out.close();
			writer.close();
		}
	}
}
