package com.kuta.base.util;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.kuta.base.entity.KutaConstants;
import com.kuta.base.exception.KutaError;
import com.kuta.base.exception.KutaIllegalArgumentException;
/**
 * AES加密算法工具类
 * */
public class AESUtil {
	
	/**
	 * key长度不是16位的字符描述
	 * */
	private static final String KEY_LENGTH_NOT_16 = "Key长度不是16位";
	
	
	
	/**
	 * 执行AES加密
	 * @param src 需加密的字符串
	 * @param secretKey 秘钥
	 * @return 加密的字符串
	 * @throws Exception 当发生异常时抛出
	 * */
    public static String Encrypt(String src, String secretKey) throws Exception {
        if (secretKey == null) {
            throw new KutaIllegalArgumentException(String.format(KutaError.ARGUMENT_NOT_EMPTY.getDesc(), KutaConstants.SECRET_KEY));
        }
        // 判断Key是否为16位
        if (secretKey.length() != 16) {
        	throw new KutaIllegalArgumentException(KEY_LENGTH_NOT_16);
        }
        byte[] raw = secretKey.getBytes(KutaConstants.ENCODE_UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KutaConstants.AES_ENCRYPTION_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KutaConstants.AES_CIPHER);//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes(KutaConstants.ENCODE_UTF_8));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
	 * 执行AES解密
	 * @param src 需解密的字符串
	 * @param secretKey 秘钥
	 * @return 解密的字符串
	 * @throws Exception 当发生异常时抛出
	 * */
    public static String Decrypt(String src, String secretKey) throws Exception {
        try {
        	if (secretKey == null) {
                throw new KutaIllegalArgumentException(String.format(KutaError.ARGUMENT_NOT_EMPTY.getDesc(), KutaConstants.SECRET_KEY));
            }
            // 判断Key是否为16位
            if (secretKey.length() != 16) {
            	throw new KutaIllegalArgumentException(KEY_LENGTH_NOT_16);
            }
            byte[] raw = secretKey.getBytes(KutaConstants.ENCODE_UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KutaConstants.AES_ENCRYPTION_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KutaConstants.AES_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(src);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,KutaConstants.ENCODE_UTF_8);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}
