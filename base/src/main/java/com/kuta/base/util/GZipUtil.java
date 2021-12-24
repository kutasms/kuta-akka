package com.kuta.base.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.kuta.base.entity.KutaConstants;

/**
 * Gzip compression and decompression tool class
 * */
public class GZipUtil {
	
	/**
	 * Compress string
	 * @param src String to be compressed
	 * @param encoding Character encoding method
	 * @return Compressed byte array
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
	 * Compress string
	 * @param src String to be compressed
	 * @return Compressed byte array
	 * */
    public static byte[] compress(String str) throws IOException {  
        return compress(str, KutaConstants.ENCODE_UTF_8);  
    }
    
    /**
     * Decompress byte array
     * @param bytes Byte array to be decompressed
     * @return Decompressed byte array
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
     * Decompress byte array
     * @param bytes Byte array to be decompressed
     * @param encoding Character encoding method
     * @return Decompressed string
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
     * Decompress byte array
     * @param bytes Byte array to be decompressed
     * @return Decompressed string
     * */
    public static String uncompressToString(byte[] bytes) {  
        return uncompressToString(bytes, KutaConstants.ENCODE_UTF_8);  
    } 
}
