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
	public final static String SQL_LIKE_TEMPLATE = "%%%s%%";
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
	
	/**
	 * 	数字0
	 * */
	public final static Integer INT_ZERO = 0;
	
	/**
	 * http协议
	 * */
	public final static String PROTOCAL_HTTP = "http";
	/**
	 * websocket协议
	 * */
	public final static String PROTOCAL_WEBSOCKET = "websocket";
	/**
	 * appkey
	 * */
	public final static String APP_KEY = "app_key";
	/**
	 * appsecret
	 * */
	public final static String APP_SECRET = "app_secret";
	
	public final static String ERROR_USER_ACC_PWD = "用户名或密码错误";
	public final static String ERROR_USER_ACC_REPEAT = "用户名重复，请设置其他用户名";
	public final static String ERROR_TOKEN_NOT_FOUND = "未找到该token相关信息";
	public final static String ERROR_SIGNIN_REPEAT = "请勿重复登录";
	public final static String ERROR_LEASTONE_MODIFY_OBJ = "至少提交一个修改项";
	public final static String ERROR_ARGUMENT_LOSE = "参数%s丢失";
	public final static String ERROR_TARGET_SERVICE_NOT_FOUND = "目标服务不存在";
	public final static String ERROR_TOKEN_EXPIRE = "token已过期";
	public final static String ERROR_UNKNOWN = "未知错误";
	public final static String ERROR_DATA_NOT_FOUND = "未找到符合条件的数据";
	
	public final static String HEADER_TOKEN = "X-Token";
	
	public final static String PARAM_CODE = "code";
	public final static String PARAM_ACTION = "action";
	public final static String PARAM_APP_VERSION = "app_ver";
	public final static String PARAM_APP_ID = "app_id";
	public final static String PARAM_DEVICE = "device";
	public final static String PARAM_DEVICE_SERIAL = "serial";
	public final static String PARAM_CMD = "cmd";
	public final static String PARAM_CMD_TYPE = "cmd_type";
	public final static String PARAM_PAGE_SIZE = "page_size";
	public final static String PARAM_PAGE_NUM = "page_num";
	public final static String PARAM_TYPE = "type";
	public final static String PARAM_DEFAULT_VAL = "default_val";
	public final static String PARAM_REMARK = "remark";
	public final static String PARAM_USER_NAME = "username";
	public final static String PARAM_PASSWORD = "password";
	public final static String PARAM_TOKEN = "token";
	public final static String PARAM_WIDTH = "width";
	public final static String PARAM_HEIGHT = "height";
	public final static String PARAM_NAME = "name";
	public final static String PARAM_NICK = "nick";
	public final static String PARAM_DESC = "desc";
	public final static String PARAM_DETAIL = "detail";
	public final static String PARAM_ALIAS = "alias";
	public final static String PARAM_LEVEL = "level";
	public final static String PARAM_ADDRESS = "address";
	public final static String PARAM_DEEP = "deep";
	public final static String PARAM_IDX = "idx";
	public final static String PARAM_CLASSIFY = "classify";
	public final static String PARAM_PHONE = "phone";
	public final static String PARAM_AVATAR = "avatar";
	public final static String PARAM_KEY = "key";
	public final static String PARAM_PARENT_ID = "parent_id";
	public final static String PARAM_STATUS = "status";
	public final static String PARAM_IS_ONLINE = "is_online";
	public final static String PARAM_MAP = "map";
	public final static String PARAM_LIST = "list";
	public final static String PARAM_LAST_ONLINE = "last_online";
	public final static String PARAM_INDEX = "index";
	public final static String PARAM_JSON = "json";
	public final static String PARAM_UID = "uid";
	public final static String PARAM_DISPLAY = "display";
	public final static String PARAM_TOTAL = "total";
	public final static String PARAM_DATA = "data";
	public final static String PARAM_CATEGORY = "category";
	public final static String PARAM_TIME_TYPE = "time_type";
	public final static String PARAM_CREATED = "created";
	public final static String PARAM_TIME = "time";
	public final static String PARAM_OWNER = "owner";
	public final static String PARAM_MODIFIER = "modifier";
	public final static String PARAM_AUTHOR = "author";
	public final static String PARAM_DIRECTION = "direction";
}
