package com.kuta.akka.base.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * WebSocket回复消息
 * */
public class WebSocketResponse implements Serializable {
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4106145497077300790L;
	
	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 数据
	 */
	private Object data;
	
	/**
	 * 命令编号
	 */
	private Integer code;
	
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
	 * 获取消息
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取状态
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
		
		return JSONObject.toJSONString(this);
	}
	
	/**
	 * 获取数据
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * 设置数据
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取命令编号
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 设置命令编号
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
}
