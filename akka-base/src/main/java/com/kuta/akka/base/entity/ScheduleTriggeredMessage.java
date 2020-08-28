package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 调度已触发消息
 * */
public class ScheduleTriggeredMessage extends KutaSerialMessage {

	/**
	 * 调度名称
	 * */
	private String name;
	
	/**
	 * 获取调度名称
	 * */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置调度名称
	 * */
	public void setName(String name) {
		this.name = name;
	}


}
