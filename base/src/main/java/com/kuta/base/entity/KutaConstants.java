package com.kuta.base.entity;

/**
 * KSF框架全局常量定义
 * */
public class KutaConstants {
	/**
	 * 默认字符编码
	 * */
	public static final String ENCODE_UTF_8 = "UTF-8";
	/**
	 * AES加密算法
	 * */
	public static final String AES_ENCRYPTION_ALGORITHM = "AES";
	/**
	 * AES算法/模式/补码方式
	 * */
	public static final String AES_CIPHER = "AES/ECB/PKCS5Padding";
	/**
	 * KEY
	 * */
	public static final String KEY = "key";
	/**
	 * VALUE
	 * */
	public static final String VALUE = "val";
	/**
	 * 秘钥
	 * */
	public static final String SECRET_KEY = "secretKey";
	
	/**
	 * ISO_8859_1编码
	 * */
	public static final String ENCODE_ISO_8859_1 = "ISO-8859-1";
	
	/**
	 * 向所有用户广播消息
	 * */
	public static final Integer BROADCAST_ALL = -1;
	
	/**
	 * [SQL]like语句模板
	 * */
	public final static String SQL_LIKE_TEMPLATE = "%%s%";
	/**
	 * [SQL]like语句模板(只有右侧百分号)
	 * */
	public final static String SQL_LIKE_TEMP_ONLY_RIGHT = "%s%";
	/**
	 * [SQL]order by *** asc
	 * */
	public final static String SQL_ORDER_BY_ASC = "asc";
	/**
	 * [SQL]order by *** desc
	 * */
	public final static String SQL_ORDER_BY_DESC = "desc";
	
	public final static String ERROR_USER_ACC_PWD = "用户名或密码错误";
	public final static String ERROR_USER_ACC_REPEAT = "用户名重复，请设置其他用户名";
	public final static String ERROR_TOKEN_NOT_FOUND = "未找到该token相关信息";
	public final static String ERROR_SIGNIN_REPEAT = "请勿重复登录";
	public final static String ERROR_LEASTONE_MODIFY_OBJ = "至少提交一个修改项";
	public final static String ERROR_ARGUMENT_LOSE = "参数%s丢失";
}
