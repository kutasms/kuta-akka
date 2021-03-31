package com.kuta.base.communication;

public class ResponseStatus {
	/**
	 * 	完成状态
	 * */
	public final static int OK = 0;
	
	/**
	 * 	未知错误
	 * */
	public final static int UNKNOWN_ERROR = 1001;
	/**
	 * 	未找到合适的actor处理消息
	 * */
	public final static int NON_MATCHED_ACTOR = 1002;
	/**
	 * 	无法解析的指令
	 * */
	public final static int UNKNOWN_CODE = 1003;
	
	/**
	 *	参数丢失
	 * */
	public final static int ERROR_ARGUMENT_LOSE = 1004;
	
	/**
	 * 	目标服务不存在
	 * */
	public final static int ERROR_TARGET_SERVICE_NOT_FOUND = 1005;
	
	/**
	 * 	错误的token
	 * */
	public final static int ERROR_ILLEGAL_TOKEN = 1006;
	
	/**
	 * 	账户已被登陆
	 * */
	public final static int ERROR_REPEAT_SIGNIN = 1007;
	
	/**
	 * token已过期
	 * */
	public final static int ERROR_TOKEN_EXPIRED = 1008;
	
	/**
	 * 	账号或者密码错误
	 * */
	public static final int ERROR_USER_ACC_PWD = 1009;
	/**
	 * 	账号重复错误
	 * */
	public static final int ERROR_USER_ACC_REPEAT = 1010;
	
	/**
	 * 	请至少提交一个修改项
	 * */
	public static final int ERROR_LEASTONE_MODIFY_OBJ = 1011;
	
	/**
	 * 未找到符合条件的数据
	 * */
	public static final int ERROR_DATA_NOT_FOUND = 1012;
	/**
	 * 参数错误
	 * */
	public final static int ERROR_ARGUMENT_FAULT= 1013;
	/**
	 *	系统通知
	 * */
	public final static int SYSTEM_NOTICE = 4001;
}
