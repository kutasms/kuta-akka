package com.kuta.base.util;

/**
 * String处理工具
 * */
public class KutaStringUtil {
	private final static String UNDERLINE = "_";

    /***
     * 下划线命名转为驼峰命名
     *
     * @param src
     *        下划线命名的字符串
     * @return 驼峰命名的字符串
     */

    public static String underlineToHump(String src) {
        StringBuilder result = new StringBuilder();
        String a[] = src.split(UNDERLINE);
        for (String s : a) {
            if (!src.contains(UNDERLINE)) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param src
     *        驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    public static String humpToUnderline(String src) {
        StringBuilder sb = new StringBuilder(src);
        int temp = 0;//定位
        if (!src.contains(UNDERLINE)) {
            for (int i = 0; i < src.length(); i++) {
                if (Character.isUpperCase(src.charAt(i))) {
                    sb.insert(i + temp, UNDERLINE);
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }
}
