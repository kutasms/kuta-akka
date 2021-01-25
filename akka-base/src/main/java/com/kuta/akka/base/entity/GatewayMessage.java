package com.kuta.akka.base.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 网关消息
 * */
public class GatewayMessage extends ClusterMessage {

	/**
	 * 	参数
	 * */
	private JSONObject param;
	
	/**
	 * 	协议，websocket/http
	 * */
	private String protocol = "http";
	
	/**
	 * 获取参数
	 * */
	public JSONObject getParams() {
		return param;
	}
	/**
	 * 设置参数
	 * @param param json参数
	 * */
	public void setParams(JSONObject param) {
		this.param = param;
	}
	/**
	 * 	获取协议
	 * */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * 	设置协议
	 * */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
}
