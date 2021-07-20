package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

import akka.actor.ActorRef;

/**
 * 用户在线离线活跃信息
 * */
public class ActiveMessage extends KutaSerialMessage {

	/**
	 * 用户消息通道
	 * */
	private ActorRef channel;
	/**
	 * 用户编号
	 * */
	private Integer pid;
	/**
	 * 是否活跃，true:在线，false:离线
	 * */
	private Boolean active = false;
	/**
	 * 	连接通道编号
	 * */
	private Integer uid;
	/**
	 * 获取用户消息通道
	 * @return 消息通道
	 * */
	public ActorRef getChannel() {
		return channel;
	}
	/**
	 * 设置用户消息通道
	 * @param channel 消息通道
	 * */
	public void setChannel(ActorRef channel) {
		this.channel = channel;
	}
	/**
	 * 获取用户编号
	 * @return 用户编号
	 * */
	public Integer getPid() {
		return pid;
	}
	/**
	 * 设置用户编号
	 * @param pid 用户编号
	 * */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取用户是否活跃
	 * @return true:在线，false:离线
	 * */
	public Boolean getActive() {
		return active;
	}
	/**
	 * 设置用户是否活跃
	 * @param active true:在线，false:离线
	 * */
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
