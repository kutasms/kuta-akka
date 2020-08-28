package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 注册消息
 * */
public class RegistrationMessage extends KutaSerialMessage {

	/**
	 * 消息
	 * */
	private String message;
	
	/**
	 * 获取消息
	 * */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置消息
	 * */
	public void setMessage(String message) {
		this.message = message;
	}

}
