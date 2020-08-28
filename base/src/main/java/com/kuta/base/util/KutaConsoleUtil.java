package com.kuta.base.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 控制台工具
 * */
public class KutaConsoleUtil {
	/**
	 * 打印对象
	 * @param object Java对象
	 * */
	public static void printObj(Object object) {
		System.out.println(JSONObject.toJSONString(object));
	}
	/**
	 * 打印对象
	 * @param object Java对象
	 * @param prettyFormat 是否打印为格式化的json格式
	 * */
	public static void printObj(Object object, boolean prettyFormat) {
		System.out.println(JSONObject.toJSONString(object,prettyFormat));
	}
}
