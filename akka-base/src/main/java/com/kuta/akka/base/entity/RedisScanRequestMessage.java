package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 数据扫描请求消息
 * */
public class RedisScanRequestMessage extends KutaSerialMessage {
	/**
	 * 名称
	 * */
	private String name;
	/**
	 * 模板信息
	 * */
	private String pattern;
	/**
	 * 每次扫描长度
	 * */
	private Integer scanSize;
	/**
	 * 每次扫描休眠时间
	 * */
	private Long sleep;
	/**
	 * 每次消费长度
	 * */
	private Integer consumeSize;
	/**
	 * 获取名称
	 * @return 名称
	 * */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取模板信息
	 * @return 模板信息
	 * */
	public String getPattern() {
		return pattern;
	}

	/**
	 * 设置模板信息
	 * @param pattern 模板信息
	 * */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 获取扫描长度
	 * @return 扫描长度
	 * */
	public Integer getScanSize() {
		return scanSize;
	}

	/**
	 * 设置扫描长度
	 * @param scanSize 扫描长度
	 * */
	public void setScanSize(Integer scanSize) {
		this.scanSize = scanSize;
	}

	/**
	 * 获取每次消费长度
	 * @return 每次消费长度
	 * */
	public Integer getConsumeSize() {
		return consumeSize;
	}

	/**
	 * 设置每次消费长度
	 * @param consumeSize 每次消费长度
	 * */
	public void setConsumeSize(Integer consumeSize) {
		this.consumeSize = consumeSize;
	}

	/**
	 * 获取每次消费休眠时间
	 * @return 每次消费休眠时间
	 * */
	public Long getSleep() {
		return sleep;
	}
	/**
	 * 设置每次消费休眠时间
	 * @param sleep 每次消费休眠时间
	 * */
	public void setSleep(Long sleep) {
		this.sleep = sleep;
	}

}
