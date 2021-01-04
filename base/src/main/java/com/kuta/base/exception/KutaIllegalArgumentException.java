package com.kuta.base.exception;

/**
 * 参数错误异常
 * */
public class KutaIllegalArgumentException extends IllegalArgumentException {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3833034029371078989L;

	private int errorCode = -1;
	
	/**
	 * 获取错误码
	 * */
	public int getErrorCode() {
		return errorCode;
	}
	/**
	 * 构造函数
	 * */
	public KutaIllegalArgumentException() {
		super();
	}
	/**
	 * 构造函数
	 * @param message 消息
	 * */
	public KutaIllegalArgumentException(String message) {
		super(message);
	}
	
	public KutaIllegalArgumentException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
