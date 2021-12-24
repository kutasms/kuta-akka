package com.kuta.base.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Print tool
 * */
public class KutaConsoleUtil {
	/**
	 * Print object
	 * @param object Java object
	 * */
	public static void printObj(Object object) {
		System.out.println(JSONObject.toJSONString(object));
	}
	/**
	 * Print object
	 * @param object Java object
	 * @param prettyFormat Print in formatted JSON format
	 * */
	public static void printObj(Object object, boolean prettyFormat) {
		System.out.println(JSONObject.toJSONString(object,prettyFormat));
	}
}
