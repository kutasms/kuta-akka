package com.kuta.akka.base.entity;

import java.util.Set;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * Redis数据扫描结果信息
 * */
public class RedisScanResultMessage extends KutaSerialMessage {

	/**
	 * 名称
	 * */
	private String name;
	/**
	 * 模板信息
	 * */
	private String pattern;
	/**
	 * 来源
	 * */
	private String source;
	/**
	 * 键集合
	 * */
	private Set<String> values;
	
	/**
	 * 获取键集合
	 * */
	public Set<String> getValues() {
		return values;
	}
	/**
	 * 设置键集合
	 * */
	public void setValues(Set<String> values) {
		this.values = values;
	}
	
	/**
	 * 获取任务名称
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置任务名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取来源
	 * */
	public String getSource() {
		return source;
	}
	/**
	 * 设置来源
	 * */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取模板信息
	 * */
	public String getPattern() {
		return pattern;
	}
	/**
	 * 设置模板信息
	 * */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
