package com.kuta.base.exception;

import org.apache.http.util.Asserts;

public class KutaAsserts extends Asserts{

	private final static String NOT_EMAIL_MESSAGE = "'%s'不是标准的email格式";
	private final static String NOT_NUMBER ="'%s'不是数字";
	private final static String NOT_MOBILE_NUM = "'%s'不是手机号码";
	private final static String NOT_URL = "'%s'不是标准的Url";
	private final static String NOT_IP_ADDR = "'%s'不是标准的IP地址";
	private final static String NOT_IDCARD = "'%s'不是身份证号码";
	
	/**
	 * 非标准email格式
	 * */
	public static void notEmail(final String s) {
		if(!s.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_EMAIL_MESSAGE, s));
		}
	}
	
	/**
	 * 非数字
	 * */
	public static void notNumber(final String s) {
		if(!s.matches("^[1-9]+[0-9\\.]+$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_NUMBER, s));
		}
	}
	
	/**
	 * 非手机号码
	 * */
	public static void notMobileNum(final String s) {
		if(!s.matches("^1[345678]\\d{9}$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_MOBILE_NUM, s));
		}
	}
	
	/**
	 * 非url
	 * */
	public static void notUrl(final String s) {
		if(!s.matches("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_URL, s));
		}
	}
	/**
	 * 非ip地址
	 * */
	public static void notIpAddr(final String s) {
		if(!s.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_IP_ADDR, s));
		}
	}
	/**
	 * 身份证号码
	 * */
	public static void notIDCard(final String s) {
		if(!s.matches("^\\d{15}|\\d{18}$")) {
			throw new KutaIllegalArgumentException(String.format(NOT_IDCARD, s));
		}
	}
}
