package com.kuta.base.util;

/**
 * Binary data formatting tool
 * */
public class KutaByteUtil {
	/**
	 * Convert byte array to hexadecimal string
	 * @param src Byte array to be converted
	 * @return Hexadecimal string
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
	 * Convert int type to byte array, and only take the first length bytes
	 * @param target Target int value
	 * @param length The length of the value to get
	 * @return Converted byte array
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
	 * Convert byte array to long type
	 * @param bytes Target byte array
	 * @return Long value
	 * */
	public static long bytesToLong(byte[] bytes) {
		 long  values = 0;   
		    for (int i = 0; i < 6; i++) {    
		        values <<= 8; values |= (bytes[i] & 0xff);   
		    }   
		    return values; 
    }
	/**
	 * Convert long type to byte array, and only take the first length bytes
	 * @param target Target long value
	 * @param length The length of the value to get
	 * @return Converted byte array
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
