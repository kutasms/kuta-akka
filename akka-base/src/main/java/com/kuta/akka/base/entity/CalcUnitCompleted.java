package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * <pre>
 * 计算单元完成事件
 * 消息处理器完成一个消息的处理工作后将发送此消息至分发器
 * 当分发器收到此消息时将分批新的任务至计算器
 * </pre>
 * */
public class CalcUnitCompleted extends KutaSerialMessage {

}
