package com.kuta.base.exception;

/**
 * KSF框架运行时错误
 * */
public class KutaRuntimeException extends Exception {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -6063790021082301762L;
	
	/**
	 * 构造函数
	 * */
	public KutaRuntimeException() {
		super();
	}
	
	/**
	 * 构造函数
	 * @param message 消息
	 * */
	public KutaRuntimeException(String message) {
		super(message);
	}
}
