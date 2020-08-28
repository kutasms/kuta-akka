package com.kuta.akka.base.entity;

/**
 * 任务分发消息
 * */
public class DistributeMessage {
	/**
	 * 消息
	 * */
	private String message;
	/**
	 * 模板
	 * */
	private String pattern;
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
