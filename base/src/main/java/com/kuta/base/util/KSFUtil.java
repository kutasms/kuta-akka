package com.kuta.base.util;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KSFUtil {
	/**
	 * 检查值是否为null,如果为null返回true,不为null返回false
	 *
	 * @param obj 展开的对象数组
	 * @return 是否为空
	 */
	public static boolean isValueNull(Object... obj) {
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] == null || "".equals(obj[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查字符串是否为空或者null,如果为空或者null返回true,否则返回false
	 *
	 * @param s 字符串
	 * @return 是否为空或者null
	 */
	public static boolean isEmptyString(String s) {
		if(null==s || s.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 检查Map是否为空或者null,如果为空或者null返回true,否则返回false
	 *
	 * @param s map
	 * @return 是否为空或者null
	 */
	public static boolean isEmptyMap(Map<?,?> map) {
		if(map == null || map.size() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 检查Collection是否为空或者null,如果为空或者null返回true,否则返回false
	 *
	 * @param coll 集合
	 * @return 是否为空或者null
	 */
	public static boolean isEmptyColl(Collection<?> coll) {
		if(coll == null || coll.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将int转换为byte数组
	 * @param i int值
	 * @return byte数组
	 * */
	public static byte[] intToBytes(int i) {  
        byte[] targets = new byte[4];  
        targets[3] = (byte) (i & 0xFF);  
        targets[2] = (byte) (i >> 8 & 0xFF);  
        targets[1] = (byte) (i >> 16 & 0xFF);  
        targets[0] = (byte) (i >> 24 & 0xFF);  
        return targets;  
    }  
	/**
	 * 将byte数组转换为int
	 * 
	 * @param b byte数组
	 * @return int值
	 * */
	public static int bytesToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
	
	/**
	 * 将long转换为byte数组
	 * @param x long值
	 * @return byte数组
	 * */
	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, x);
        return buffer.array();
    }

	/**
	 * 将byte数组转换为long
	 * 
	 * @param b byte数组
	 * @return long值
	 * */
    public static long bytesToLong(byte[] bytes) {
    	ByteBuffer buf = ByteBuffer.allocate(bytes.length).put(bytes);
    	buf.flip();//need flip 
        return buf.getLong();
    }
    /**
	 * 将int转换为Base64字符串
	 * <p>使用此方法转换后的Base64字符串是等长的</p>
	 * <p>可用于redis中键设置</p>
	 * @param i int值
	 * @return String类型值
	 * */
	public static String intToBase64(int i) {
		return new String(Base64.getEncoder().encode(intToBytes(i)));
	}
	/**
	 * 将Base64字符串转换为Integer
	 * @param base64Str base64字符串
	 * @return Integer值
	 * */
	public static Integer base64ToInt(String base64Str) {
		return bytesToInt(Base64.getDecoder().decode(base64Str));
	}
	
	/**
	 * 将long转换为Base64字符串
	 * <p>使用此方法转换后的Base64字符串是等长的</p>
	 * <p>可用于redis中键设置</p>
	 * @param l long值
	 * @return String类型值
	 * */
	public static String longToBase64(long l) {
		return new String(Base64.getEncoder().encode(longToBytes(l)));
	}
	
	/**
	 * 将Base64字符串转换为Long
	 * @param base64Str base64字符串
	 * @return Long值
	 * */
	public static Long base64ToLong(String base64Str) {
		return bytesToLong(Base64.getDecoder().decode(base64Str));
	}
	
}
