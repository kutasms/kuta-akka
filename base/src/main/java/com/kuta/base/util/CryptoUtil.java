package com.kuta.base.util;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.aliyuncs.utils.Base64Helper;

/**
 * 加密工具类
 * */
public class CryptoUtil {

		/**
		 * SHA
		 * */
	    public static final String KEY_SHA = "SHA";
	    /**
	     * MD5
	     * */
	    public static final String KEY_MD5 = "MD5";
	 
	    /**
	     * MAC算法可选以下多种算法
	     * 
	     * <pre>
	     * HmacMD5 
	     * HmacSHA1 
	     * HmacSHA256 
	     * HmacSHA384 
	     * HmacSHA512
	     * </pre>
	     */
	    public static final String KEY_MAC = "HmacMD5";
	 
	 
	    /**
	     * SHA加密
	     * 
	     * @param data 待加密的byte数组
	     * @return 加密后的byte数组
	     * @throws Exception 发生异常时抛出
	     */
	    public static byte[] encryptSHA(byte[] data) throws Exception {
	        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
	        sha.update(data);
	        return sha.digest();
	    }
	 
	    /**
	     * 初始化HMAC密钥
	     * 
	     * @return HMAC密钥
	     * @throws Exception 发生异常时抛出
	     */
	    public static String initMacKey() throws Exception {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
	        SecretKey secretKey = keyGenerator.generateKey();
	        return Base64Util.encode(secretKey.getEncoded());
	    }
	 
	    /**
	     * HMAC加密
	     * 
	     * @param data 待加密的byte数组
	     * @param key 秘钥
	     * @return 加密后的byte数组
	     * @throws Exception 发生异常时抛出
	     */
	    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
	 
	        SecretKey secretKey = new SecretKeySpec(Base64Util.decode(key), KEY_MAC);
	        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
	        mac.init(secretKey);
	 
	        return mac.doFinal(data);
	 
	    }

}
