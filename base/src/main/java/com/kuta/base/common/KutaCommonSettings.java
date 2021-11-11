package com.kuta.base.common;

public class KutaCommonSettings {
	private static String cacheKeyPrefix = "AKKA";
	
	public static String getCacheKeyPrefix() {
		return cacheKeyPrefix;
	}
	
	/**
	 * This method provided a interface to change the prefix of cache key.
	 * */
	public static void setCacheKeyPrefix(String prefix) {
		cacheKeyPrefix = prefix;
	}
}
