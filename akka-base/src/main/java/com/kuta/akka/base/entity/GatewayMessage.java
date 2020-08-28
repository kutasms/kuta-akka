package com.kuta.akka.base.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 网关消息
 * */
public class GatewayMessage extends ClusterMessage {

	/**
	 * 参数
	 * */
	private JSONObject param;
	/**
	 * 命令编号
	 * */
	private Integer code;
	
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
