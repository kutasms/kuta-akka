package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 投票完成消息
 * */
public class ElectCompleted extends KutaSerialMessage {

	/**
	 * 名称
	 * */
	private String name;
	/**
	 * 获取名称
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
}
