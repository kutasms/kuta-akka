package com.kuta.akka.base.entity;

import com.alibaba.fastjson.JSONObject;


/**
 * Json格式响应消息
 * <p>当使用http访问时需使用此消息响应</p>
 * */
public class KutaHttpResponse extends KutaResponse{

	/**
	 * 响应消息为HttpCode:200
	 * */
	public static KutaHttpResponse ok(){
		return new KutaHttpResponse(null, 0);
	}
	
	/**
	 * 构造函数
	 * @param data 返回数据
	 * @param status 状态
	 * @param message 消息
	 * */
	public KutaHttpResponse(JSONObject data,Integer status, String message) {
		this.data = data;
		this.status = status;
		this.message = message;
	}
	
	/**
	 * 构造函数
	 * @param data 返回数据
	 * @param status 状态
	 * @param message 消息
	 * @param code 命令编号
	 * */
	public KutaHttpResponse(JSONObject data,Integer status, String message,Integer code) {
		this.data = data;
		this.status = status;
		this.message = message;
		this.code = code;
	}
	
	/**
	 * 构造函数
	 * @param data 返回数据
	 * @param status 状态
	 * */
	public KutaHttpResponse(JSONObject data,Integer status) {
		this.data = data;
		this.status = status;
	}
	
	/**
	 * 构造函数
	 * */
	public KutaHttpResponse() {
		
	}
	
	
}
