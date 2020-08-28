package com.kuta.base.exception;

/**
 * KSF框架运行时错误
 * */
public class KSFRuntimeException extends Exception {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -6063790021082301762L;
	
	/**
	 * 构造函数
	 * */
	public KSFRuntimeException() {
		super();
	}
	
	/**
	 * 构造函数
	 * @param message 消息
	 * */
	public KSFRuntimeException(String message) {
		super(message);
	}
}
