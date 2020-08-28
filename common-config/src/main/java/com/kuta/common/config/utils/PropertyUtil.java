package com.kuta.common.config.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性工具类
 * <p>
 * 使用此工具可快速获取配置信息
 * </p>
 */
public class PropertyUtil {
	/**
	 * 属性集
	 */
	private static Properties pro;
	/**
	 * 属性集缓存
	 */
	private static Map<String, Properties> proMap;
	/**
	 * 默认App配置文件名称
	 */
	private static final String APP_PROPERTIES = "app.properties";
	/**
	 * 文件加载错误信息
	 */
	private static final String FILE_LOAD_ERROR = "加载文件失败";
	/**
	 * 文件名称格式化字符串
	 */
	private static final String FILE_NAME_PATTERN = "%s.properties";
	/**
	 * 注释信息格式化字符串
	 */
	private static final String COMMIT_PATTERN = "update %s value";
	/**
	 * utf-8字符编码
	 */
	private static final String ENCODE_UTF_8 = "utf-8";
	static {
		proMap = new HashMap<String, Properties>();
		// 获取App(默认配置文件)配置
		InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(APP_PROPERTIES);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		pro = new Properties();
		try {
			pro.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(FILE_LOAD_ERROR);
		}
	}

	/**
	 * 获取app.properties文件的相关配置
	 * 
	 * @param key
	 *            属性键
	 * @return 属性值
	 */
	public static String getAppProperty(String key) {
		return pro.get(key).toString();
	}

	/**
	 * 更新配置信息
	 * 
	 * @param prefix
	 *            文件前缀 如 email.properties则传入email
	 * @param key
	 *            属性键
	 * @param value
	 *            属性值
	 */
	public static void updateProperty(String prefix, String key, String value) {
		Properties properties = getProperties(prefix);

		properties.setProperty(key, value);
		OutputStream out;
		try {
			out = new FileOutputStream(String.format(FILE_NAME_PATTERN, prefix));
			properties.store(out, String.format(COMMIT_PATTERN, key));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取属性集
	 * 
	 * @param prefix
	 *            文件前缀 如 email.properties则传入email
	 * @return 属性集
	 */
	public static Properties getProperties(String prefix) {
		Properties properties = null;
		if (proMap.containsKey(prefix)) {
			properties = proMap.get(prefix);
			return properties;
		} else {
			InputStreamReader isr = null;

			try {
				InputStream in = PropertyUtil.class.getClassLoader()
						.getResourceAsStream(String.format(FILE_NAME_PATTERN, prefix));
				properties = new Properties();
				isr = new InputStreamReader(in, ENCODE_UTF_8);
				properties.load(isr);
				proMap.put(prefix, properties);
				return properties;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(FILE_LOAD_ERROR);
				return null;
			}
		}
	}

	/***
	 * 获取配置信息
	 * 
	 * @param prefix
	 *            文件前缀 如 email.properties则传入email
	 * @param key
	 *            配置key信息
	 * @return 配置集
	 */
	public static String getProperty(String prefix, String key) {
		Properties properties = null;
		if (proMap.containsKey(prefix)) {
			properties = proMap.get(prefix);
			return properties.getProperty(key);
		} else {
			properties = new Properties();
			InputStreamReader reader = null;
			try {
				InputStream in = PropertyUtil.class.getClassLoader()
						.getResourceAsStream(String.format(FILE_NAME_PATTERN, prefix));
				properties = new Properties();
				reader = new InputStreamReader(in, ENCODE_UTF_8);
				properties.load(reader);
				proMap.put(prefix, properties);
				return properties.getProperty(key);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(FILE_LOAD_ERROR);
				return null;
			}
		}
	}
	/**
	 * 获取Integer类型属性值
	 * @param prefix 文件前缀 如 email.properties则传入email
	 * @param key 属性键
	 * @return Integer类型属性值
	 * */
	public static Integer getInteger(String prefix, String key) {
		return Integer.parseInt(getProperty(prefix, key));
	}
	/**
	 * 获取Long类型属性值
	 * @param prefix 文件前缀 如 email.properties则传入email
	 * @param key 属性键
	 * @return Long类型属性值
	 * */
	public static Long getLong(String prefix, String key) {
		return Long.parseLong(getProperty(prefix, key));
	}
	/**
	 * 获取Boolean类型属性值
	 * @param prefix 文件前缀 如 email.properties则传入email
	 * @param key 属性键
	 * @return Boolean类型属性值
	 * */
	public static Boolean getBoolean(String prefix, String key) {
		return Boolean.parseBoolean(getProperty(prefix, key));
	}
}
