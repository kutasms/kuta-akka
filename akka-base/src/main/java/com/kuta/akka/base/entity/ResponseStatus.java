package com.kuta.akka.base.entity;

public class ResponseStatus {
	/**
	 * 完成状态
	 * */
	public final static int OK = 0;
	
	/**
	 * 未知错误
	 * */
	public final static int UNKNOWN_ERROR = -1;
	/**
	 * 未找到合适的actor处理消息
	 * */
	public final static int NON_MATCHED_ACTOR = -2;
}
