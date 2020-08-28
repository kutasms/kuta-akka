package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 计算完成消息
 * */
public class CalcFinishMessage extends KutaSerialMessage {

	/**
	 * 任务名称
	 * */
	private String name;
	/**
	 * 获取任务名称
	 * @return 任务名称
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置任务名称
	 * @param name 任务名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
}
