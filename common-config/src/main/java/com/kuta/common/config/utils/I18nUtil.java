package com.kuta.common.config.utils;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class I18nUtil {
	
	private static boolean initialized = false;
	private static Locale locale = null;
	private static Map<String, ResourceBundle> map;
	
	public static void initialize(String locale) {
		if(!initialized) {
			I18nUtil.locale = new Locale(locale);
			map = new ConcurrentHashMap<String, ResourceBundle>();
		}
		initialized = true;
	}
	
	public static String get(String prefix, String key) {
		if(!initialized) {
			throw new RuntimeException("please initialize the I18nUtil class.");
		}
		if(map.containsKey(prefix)) {
			ResourceBundle bundle = map.get(prefix);
			return bundle.getString(key);
		} else {
			ResourceBundle rb = ResourceBundle.getBundle(String.format("locale/%s", prefix), locale);
			map.put(prefix, rb);
			return rb.getString(key);
		}
	}
}
