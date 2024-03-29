package com.kuta.base.database.mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.kuta.base.common.KutaCommonSettings;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaStringUtil;

/**
 * MYSQL数据导入REDIS的SQL文件生成工具
 * */
public class RedisImportSqlFileGenerator {
	
	/**
	 * 获取类定义的所有非static变量名
	 * @param clazz mybatis生成的实体类型
	 * @return 获取的字段集合
	 * */
	private static <T> List<String> getFiledNames(Class<T> clazz){
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
		List<String> fieldNames = getFiledNames(clazz);
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
		builder.append(String.format("CONCAT('%s_%s_',%s) AS redis_key,\r\n", KutaCommonSettings.getCacheKeyPrefix(), clazz.getSimpleName().toLowerCase(), cacheKey));
		
		for(int i=0;i<fieldNames.size();i++) {
			String underLineName = KutaStringUtil.humpToUnderline(fieldNames.get(i));
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
	
	public static <T> void generateContainsWhere(String cacheKey1, String cacheKey2, String tableName, Class<T> clazz,String primaryKey) throws IOException{
		String path = RedisImportSqlFileGenerator.class.getClassLoader().getResource("").getPath();
		generateContainsWhere(clazz, tableName, path, null, cacheKey1, cacheKey2, primaryKey);
	}
	
	public static <T> void generateContainsWhere(String cacheKeyPrefix,String cacheKey1, String cacheKey2, String tableName,Class<T> clazz,String primaryKey) throws IOException{
		String path = RedisImportSqlFileGenerator.class.getClassLoader().getResource("").getPath();
		generateContainsWhere(clazz,tableName, path, cacheKeyPrefix, cacheKey1, cacheKey2, primaryKey);
	}
	
	public static <T> void generateContainsWhere(Class<T> clazz,String tableName, String outPath,String cacheKey1, String cacheKey2,String primaryKey) throws IOException{
		generateContainsWhere(clazz, tableName,outPath, null, cacheKey1, cacheKey2, primaryKey);
	}
	
	
	
	public static <T> void generateContainsWhere(Class<T> clazz,String tableName,String outPath,String cacheKeyPrefix,String cacheKey1, String cacheKey2,String primaryKey) throws IOException {
//		List<String> fieldNames = getFiledNames(clazz);
		Field[] orgFields = KutaBeanUtil.getAllFields(clazz);
		String path = outPath + clazz.getSimpleName() + ".sql";
		List<Field> lst = new ArrayList<Field>();
		for(int i=0;i<orgFields.length;i++) {
			lst.add(orgFields[i]);
		}
		lst.removeIf(f->Modifier.isStatic(f.getModifiers()) || f.getName().equals("cacheField"));
		Field[] fields = lst.toArray(new Field[0]);
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT CONCAT(\r\n");
		builder.append(String.format("\"*%s\\r\\n\",\r\n", 2 + fields.length * 2));
		builder.append("'$', LENGTH(redis_cmd), '\\r\\n',redis_cmd, '\\r\\n','$', LENGTH(redis_key), '\\r\\n',redis_key, '\\r\\n',\r\n");

		for(int i=0;i<fields.length;i++) {
			if(fields[i] == null) {
				continue;
			}
			if(i < fields.length-1) {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r\\n'\r\n", 
						fields[i].getName(),fields[i].getName(),fields[i].getName(),fields[i].getName()));
			}
			else {
				builder.append(String.format("'$', LENGTH(k_%s), '\\r\\n',k_%s, '\\r\\n','$', LENGTH(v_%s), '\\r\\n', v_%s, '\\r')\r\n", 
						fields[i].getName(),fields[i].getName(),fields[i].getName(),fields[i].getName()));
			}
		}
		builder.append("FROM (\r\n" + 
				"SELECT\r\n" + 
				"'HMSET' AS redis_cmd, \r\n");
		builder.append(String.format("CONCAT('%s_%s_',`%s`,'_',`%s`) AS redis_key,\r\n", KutaCommonSettings.getCacheKeyPrefix(), cacheKeyPrefix == null ? clazz.getSimpleName().toLowerCase() : cacheKeyPrefix, cacheKey1, cacheKey2));
		
		for(int i=0;i<fields.length;i++) {
			if(fields[i] == null) {
				continue;
			}
			String underLineName = KutaStringUtil.humpToUnderline(fields[i].getName());
			String pattern = "'%s' AS k_%s,`%s` AS v_%s%s\r\n";
			if(fields[i].getType().equals(Date.class)) {
				pattern = "'%s' AS k_%s, SUBSTRING(DATE_FORMAT(`%s`, '%%Y-%%m-%%d %%H:%%m:%%s.%%f'),1,23) AS v_%s%s\r\n";
			}
			if(i < fields.length-1) {
				
				builder.append(String.format(pattern, 
						fields[i].getName(),
						fields[i].getName(),
						underLineName,
						fields[i].getName(),","));
			}
			else {
				builder.append(String.format(pattern, 
						fields[i].getName(),
						fields[i].getName(),
						underLineName,
						fields[i].getName(),""));
			}
		}
		builder.append(String.format(" FROM %s where `%s`>=${1} && %s<=${2}\r\n",tableName ==null ? clazz.getSimpleName() : tableName, primaryKey,primaryKey) + 
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
		List<String> fieldNames = getFiledNames(clazz);
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
		builder.append(String.format("CONCAT('%s_%s_',%s) AS redis_key,\r\n", KutaCommonSettings.getCacheKeyPrefix(), clazz.getSimpleName().toLowerCase(), cacheKey));
		
		for(int i=0;i<fieldNames.size();i++) {
			String underLineName = KutaStringUtil.humpToUnderline(fieldNames.get(i));
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
