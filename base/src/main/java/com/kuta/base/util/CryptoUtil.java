package com.kuta.base.util;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption tool class
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
	     * The MAC algorithm can be selected from the following algorithms:
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
	     * Sha encryption
	     * 
	     * @param data Byte array to be encrypted
	     * @return Encrypted byte array
	     * @throws Exception Thrown when an exception occurs
	     */
	    public static byte[] encryptSHA(byte[] data) throws Exception {
	        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
	        sha.update(data);
	        return sha.digest();
	    }
	 
	    /**
	     * Initialize HMAC key
	     * 
	     * @return HMAC key
	     * @throws Exception Thrown when an exception occurs
	     */
	    public static String initMacKey() throws Exception {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
	        SecretKey secretKey = keyGenerator.generateKey();
	        return Base64Util.encode(secretKey.getEncoded());
	    }
	 
	    /**
	     * HMAC encryption
	     * 
	     * @param data Byte array to be encrypted
	     * @param key Secret key
	     * @return Encrypted byte array
	     * @throws Exception Thrown when an exception occurs
	     */
	    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
	 
	        SecretKey secretKey = new SecretKeySpec(Base64Util.decode(key), KEY_MAC);
	        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
	        mac.init(secretKey);
	 
	        return mac.doFinal(data);
	 
	    }

}
