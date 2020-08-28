package com.kuta.akka.base.entity;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.serialization.KutaSerialMessage;


/**
 * Json格式响应消息
 * <p>当使用http访问时需使用此消息响应</p>
 * */
public class HttpResponseMessage extends KutaSerialMessage{

	/**
	 * 响应消息为HttpCode:200
	 * */
	public static HttpResponseMessage ok(){
		return new HttpResponseMessage(null, 0);
	}
	/**
	 * 响应消息为HttpCode:200
	 * */
	private JSONObject data;
	/**
	 * ok
	 * */
	private String message = "ok";
	/**
	 * 状态码
	 * */
	private Integer status;
	/**
	 * 命令编号
	 * */
	private Integer code;
	/**
	 * 处理此请求消耗时间
	 * */
	private Long elapsedTime;
	
	/**
	 * 构造函数
	 * @param data 返回数据
	 * @param status 状态
	 * @param message 消息
	 * */
	public HttpResponseMessage(JSONObject data,Integer status, String message) {
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
	public HttpResponseMessage(JSONObject data,Integer status, String message,Integer code) {
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
	public HttpResponseMessage(JSONObject data,Integer status) {
		this.data = data;
		this.status = status;
	}
	
	/**
	 * 构造函数
	 * */
	public HttpResponseMessage() {
		
	}
	
	/**
	 * 获取消息
	 * */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置消息
	 * */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取数据
	 * */
	public JSONObject getData() {
		return data;
	}
	/**
	 * 设置数据
	 * */
	public void setData(JSONObject data) {
		this.data = data;
	}
	/**
	 * 获取状态
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取命令编号
	 * */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置命令编号
	 * */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取消耗时间
	 * */
	public Long getElapsedTime() {
		return elapsedTime;
	}
	/**
	 * 设置消耗时间
	 * */
	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
