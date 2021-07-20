package com.kuta.akka.base.entity;

import java.util.Map;

import com.kuta.akka.base.serialization.KutaSerialMessage;

import akka.actor.ActorRef;

public class GatewayStringMessage extends KutaSerialMessage {
	
	private Integer code;
	private String message;
	private ActorRef channel;
	private Map<String, String> urlParams;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getUrlParams() {
		return urlParams;
	}
	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}
	public ActorRef getChannel() {
		return channel;
	}
	public void setChannel(ActorRef channel) {
		this.channel = channel;
	}
}
