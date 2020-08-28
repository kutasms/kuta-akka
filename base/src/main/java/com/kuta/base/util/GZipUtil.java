package com.kuta.base.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.kuta.base.entity.KSFConstants;

/**
 * gzip工具类
 * */
public class GZipUtil {
	
	/**
	 * 压缩
	 * @param src 待压缩的字符串
	 * @param encoding 字符编码方式
	 * @return 压缩后的byte数组
	 * */
    public static byte[] compress(String src, String encoding) {
        if (src == null || src.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(src.getBytes(encoding));
            gzip.close();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
    
    /**
	 * 压缩
	 * @param src 待压缩的字符串
	 * @return 压缩后的byte数组
	 * */
    public static byte[] compress(String str) throws IOException {  
        return compress(str, KSFConstants.ENCODE_UTF_8);  
    }
    
    /**
     * 解压缩
     * @param bytes 待解压缩的byte数组
     * @return 解压后的byte数组
     * */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ungzip.close();
            out.close();
			in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return out.toByteArray();
    }
    
    /**
     * 解压缩
     * @param bytes 待解压缩的byte数组
     * @param encoding 字符编码方式
     * @return 解压后的字符串
     * */
    public static String uncompressToString(byte[] bytes, String encoding) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
            return out.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解压缩
     * @param bytes 待解压缩的byte数组
     * @return 解压后的字符串
     * */
    public static String uncompressToString(byte[] bytes) {  
        return uncompressToString(bytes, KSFConstants.ENCODE_UTF_8);  
    } 
}
