package com.kuta.akka.base.entity;

import akka.actor.ActorRef;

/**
 * 	已连接通道封装
 * */
public class ChannelLinked {
	private ActorRef channel;
	private ActorRef proxy;
	public ActorRef getChannel() {
		return channel;
	}
	public void setChannel(ActorRef channel) {
		this.channel = channel;
	}
	public ActorRef getProxy() {
		return proxy;
	}
	public void setProxy(ActorRef proxy) {
		this.proxy = proxy;
	}
}
