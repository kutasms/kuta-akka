package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

import akka.actor.ActorRef;

/**
 * 集群消息，包含用户消息通道和代理
 * */
public class ClusterMessage extends KutaSerialMessage {
	/**
	 * 用户消息通道
	 * */
	private ActorRef channel;
	/**
	 * 代理
	 * */
	private ActorRef proxy;
	/**
	 * 命令编号
	 * */
	private Integer code;
	
	/**
	 * 获取用户通道
	 * @return 用户通道
	 * */
	public ActorRef getChannel() {
		return channel;
	}
	/**
	 * 设置用户通道
	 * @param channel 用户通道
	 * */
	public void setChannel(ActorRef channel) {
		this.channel = channel;
	}
	/**
	 * 获取代理
	 * @return 代理
	 * */
	public ActorRef getProxy() {
		return proxy;
	}
	/**
	 * 设置代理
	 * @param proxy 代理
	 * */
	public void setProxy(ActorRef proxy) {
		this.proxy = proxy;
	}
	/**
	 * 获取命令编号
	 * */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置命令编号
	 * @param code 命令编号
	 * */
	public void setCode(Integer code) {
		this.code = code;
	}
}
