package com.kuta.akka.base.entity;

import org.apache.ibatis.session.SqlSession;

import com.kuta.akka.base.serialization.KutaSerialMessage;
import com.kuta.base.util.ThrowingConsumer;

/**
 * 请求进行数据库操作的消息
 * */
public class DBExecRequestMessage extends KutaSerialMessage {

	/**
	 * 消费器
	 * */
	private ThrowingConsumer<SqlSession,Exception> consumer;
	/**
	 * 获取消费器
	 * */
	public ThrowingConsumer<SqlSession,Exception> getConsumer() {
		return consumer;
	}
	/**
	 * 设置消费器
	 * */
	public void setConsumer(ThrowingConsumer<SqlSession,Exception> consumer) {
		this.consumer = consumer;
	}
}
