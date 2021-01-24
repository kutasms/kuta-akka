package com.kuta.akka.base.entity;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.kuta.akka.base.serialization.KutaSerialMessage;

public class KutaResponse extends KutaSerialMessage{
	/**
	 * 消息
	 * */
	protected String message;
	/**
	 * 状态码
	 * */
	protected Integer status;
	/**
	 * 关联命令
	 * */
	protected Integer code;
	/**
	 * 包含的数据对象
	 * */
	protected Object data;
	/**
	 * 处理此请求消耗时间
	 * */
	private Long elapsedTime;
	
	
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
	public Object getData() {
		return data;
	}
	/**
	 * 设置数据
	 * */
	public void setData(Object data) {
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
	
	/**
	 * 获取json筛选器
	 */
	@JSONField(serialize = false)
	private List<String> filters;
	
	/**
	 * 设置json筛选器
	 */
	public void setFilters(List<String> filters) {
		this.filters = filters;
	}
	public List<String> getFilters(){
		return this.filters;
	}
	
	
	
	/**
	 * 格式化为json字符串
	 */
	public String toJSONString() {
		if(this.filters != null) {
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
			filter.getExcludes().addAll(this.filters);
			return JSONObject.toJSONString(this, filter);
		}
		
		return JSONObject.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss SSS");
	}
}
