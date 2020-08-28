package com.kuta.base.util;

/**
 * 二进制相关工具
 * */
public class KutaByteUtil {
	/**
	 * 将byte数组转换为16进制字符串
	 * @param src 待转换的byte数组
	 * @return 16进制字符串
	 * */
	public static String bytesToHexString(byte[] src){  
		if (src == null || src.length <= 0) {  
	        return null;  
	    }  
		StringBuilder stringBuilder = new StringBuilder("");  
	    
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();
	}
	
	/**
	 * 将int类型转换为byte数组，并且只取前length个byte
	 * @param target 目标int值
	 * @param length 前length个byte
	 * @return 转换后的byte数组
	 * */
	public static byte[] intHex(int target, int length) {
	    byte[] array = new byte[length];
	    while (length > 0){
	        length--;
	        array[length] = (byte)(target >> 8*(array.length-length-1) & 0xFF);
	    }
	    return array;
	}
	
	/**
	 * byte数组转换为long类型
	 * @param bytes 目标byte数组
	 * @return long值
	 * */
	public static long bytesToLong(byte[] bytes) {
		 long  values = 0;   
		    for (int i = 0; i < 6; i++) {    
		        values <<= 8; values |= (bytes[i] & 0xff);   
		    }   
		    return values; 
    }
	/**
	 * 将long类型转换为byte数组，并且只取前length个byte
	 * @param target 目标long值
	 * @param length 前length个byte
	 * @return 转换后的byte数组
	 * */
	public static byte[] longHex(long target, int length) {
	    byte[] array = new byte[length];
	    while (length > 0){
	        length--;
	        array[length] = (byte)(target >> 8*(array.length-length-1) & 0xFF);
	    }
	    return array;
	}
}
