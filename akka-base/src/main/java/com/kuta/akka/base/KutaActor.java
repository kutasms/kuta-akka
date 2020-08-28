package com.kuta.akka.base;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.entity.RegistrationMessage;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * 基础Actor，包含logger,uuid,ack消息处理
 */
public abstract class KutaActor extends AbstractActor {
	/**
	 * 日志接口
	 */
	protected LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
	/**
	 * GUID生成器
	 */
	protected final UUID uuid = UUID.randomUUID();
	/**
	 * 节点注册消息
	 */
	private final static String REGISTRATION_MSG = "[{}]节点收到节点注册信息,发送方:{}";

	/**
	 * 抽象封装基础消息处理，并将消息分发至此函数
	 * 
	 * @param rb
	 *            消息封装
	 */
	public abstract void onReceive(ReceiveBuilder rb);

	/**
	 * 节点/Actor注册事件
	 * 
	 * @param msg
	 *            注册消息
	 */
	public abstract void onRegister(RegistrationMessage msg);

	private static final String UNHANDLED_MESSAGE = "未处理消息:{}";
	
	/**
	 * akka消息处理函数
	 * @return 消息处理封装
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		ReceiveBuilder builder = receiveBuilder();
		builder.match(RegistrationMessage.class, msg -> {
			// 处理actor注册信息
			logger.info(REGISTRATION_MSG, self(), sender());
			onRegister(msg);
		});
		onReceive(builder);
		builder.matchAny(msg -> {
			if (msg.equals("ack")) {
				return;
			}
			logger.info(UNHANDLED_MESSAGE, JSONObject.toJSONString(msg));
		});
		return builder.build();
	}

}
