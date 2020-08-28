package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 扫描开始消息
 * */
public class ScanStartedMessage extends KutaSerialMessage {

	/**
	 * 任务名称
	 * */
	private String name;
	
	/**
	 * 获取任务名称
	 * */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置任务名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
}
