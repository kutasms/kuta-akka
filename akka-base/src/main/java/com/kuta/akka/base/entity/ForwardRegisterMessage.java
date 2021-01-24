package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

import akka.actor.ActorRef;

/**
 * 	转发注册消息
 * */
public class ForwardRegisterMessage extends KutaSerialMessage {
	
	private ActorRef target;
	private WebsocketMessageType messageType;
	private Integer code;
	private Integer uid;
	private Integer did;
	private String serial;

	public ActorRef getTarget() {
		return target;
	}

	public void setTarget(ActorRef target) {
		this.target = target;
	}



	public WebsocketMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(WebsocketMessageType messageType) {
		this.messageType = messageType;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
