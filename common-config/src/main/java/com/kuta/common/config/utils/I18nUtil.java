package com.kuta.common.config.utils;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class I18nUtil {
	
	private static boolean initialized = false;
	private static Locale locale = null;
	
	public static void initialize(String locale) {
		if(!initialized) {
			if(locale.contains("-")) {
				String[] ls = locale.split("-");
				I18nUtil.locale = new Locale(ls[0], ls[1]);
			}
			else {
				I18nUtil.locale = new Locale(locale);
			}
		}
		initialized = true;
	}
	
	public static String get(String prefix, String key) {
		if(!initialized) {
			throw new RuntimeException("please initialize the I18nUtil class.");
		}
//		String currDir = System.getProperty("user.dir");
//		String sourceBaseName = String.format("%s/app-config/locale/%s",currDir, prefix);
		String sourceBaseName = String.format("locale/%s", prefix);
//		System.out.println(sourceBaseName);
		ResourceBundle rb = ResourceBundle.getBundle(sourceBaseName, locale, I18nUtil.class.getClassLoader());
		return rb.getString(key);
	}
}
