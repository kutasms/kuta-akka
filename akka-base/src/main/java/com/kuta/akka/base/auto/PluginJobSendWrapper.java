package com.kuta.akka.base.auto;

import com.alibaba.fastjson.JSONObject;

public class PluginJobSendWrapper<T> {
	private T receiver;
	private Object message;
	public T getReceiver() {
		return receiver;
	}
	
	public PluginJobSendWrapper(T recevier, Object message) {
		this.receiver = recevier;
		this.message = message;
	}
	
	public void setReceiver(T receiver) {
		this.receiver = receiver;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	
	public String getJSONMessage() {
		return JSONObject.toJSONString(this.message);
	}
}
