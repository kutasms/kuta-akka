package com.kuta.akka.base.util;

import com.kuta.akka.base.entity.DBExecRequestMessage;
import com.kuta.base.exception.KutaError;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaUtil;

import akka.actor.ActorRef;

/**
 * 数据库批量处理工具类
 * */
public class DatabaseBatchExceUtil {
	
	/**
	 * 执行器未设置提示消息
	 * */
	private static final String EXECUTOR_NOT_SET = "未设置dbExector";
	
	/**
	 * 执行器
	 * */
	private static ActorRef dbExector;
	/**
	 * 设置执行器
	 * */
	public static void setDBExector(ActorRef dbExector) throws KutaRuntimeException {
		DatabaseBatchExceUtil.dbExector = dbExector;
	}
	/**
	 * 数据执行消息
	 * @throws KutaRuntimeException 
	 * */
	public static void tell(DBExecRequestMessage msg) throws KutaRuntimeException {
		if(KutaUtil.isValueNull(dbExector)) {
			throw new NullPointerException(EXECUTOR_NOT_SET);
		}
		tell(msg, ActorRef.noSender());
	}
	/**
	 * 数据执行消息
	 * @param msg 消息
	 * @param sender 消息发送者
	 * */
	public static void tell(DBExecRequestMessage msg, ActorRef sender) {
		if(KutaUtil.isValueNull(dbExector)) {
			throw new NullPointerException(EXECUTOR_NOT_SET);
		}
		dbExector.tell(msg, sender);
	}
}
