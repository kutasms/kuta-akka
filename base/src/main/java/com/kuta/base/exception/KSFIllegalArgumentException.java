package com.kuta.base.exception;

/**
 * 参数错误异常
 * */
public class KSFIllegalArgumentException extends IllegalArgumentException {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3833034029371078989L;

	/**
	 * 构造函数
	 * */
	public KSFIllegalArgumentException() {
		super();
	}
	/**
	 * 构造函数
	 * @param message 消息
	 * */
	public KSFIllegalArgumentException(String message) {
		super(message);
	}
}
