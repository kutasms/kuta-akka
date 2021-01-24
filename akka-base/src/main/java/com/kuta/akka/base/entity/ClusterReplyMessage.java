package com.kuta.akka.base.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 	集群中回复消息
 * */
public class ClusterReplyMessage extends ClusterMessage {
	private Integer status;
	private String message;
	private JSONObject data;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
}
