package com.kuta.base.serialization;

import java.io.IOException;


import akka.actor.ActorRef;
import akka.actor.Address;
import akka.cluster.Cluster;
import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Pipe;
import io.protostuff.WireFormat.FieldType;
import io.protostuff.runtime.Delegate;

/**
 * protostuff序列化时需要用到的actor代理
 * */
public class ActorRefDelegate implements Delegate<ActorRef>{

	/**
	 * 获取字段类型
	 * @return 字段类型
	 * */
	@Override
	public FieldType getFieldType() {
		// TODO Auto-generated method stub
		return FieldType.STRING;
	}

	/**
	 * 从protostuff的input中获取已序列化的actor对象
	 * <p>当远程通信时需要将path解析为actor，需要使用resolveActorRef方法</p>
	 * @param input protostuff的input对象
	 * @throws IOException IO异常时抛出
	 * @return 已序列化的actor引用
	 * */
	@Override
	public ActorRef readFrom(Input input) throws IOException {
		// TODO Auto-generated method stub
		String s = input.readString();
		ActorRef serializedRef = ProtostuffUtil.actorSystem.provider().resolveActorRef(s);
		return serializedRef;
	}

	/**
	 * 将actor引用转换为string写入输出流中
	 * @param output 输出流
	 * @param number 字段编号
	 * @param value 需要序列化的值
	 * @param repeated 此参数作用未知
	 * @throws IOException IO异常时抛出
	 * */
	@Override
	public void writeTo(Output output, int number, ActorRef value, boolean repeated) throws IOException {
		// TODO Auto-generated method stub
		Address selfAddress = Cluster.get(ProtostuffUtil.actorSystem).selfAddress();
		String serializedRef = value.path().toSerializationFormatWithAddress(selfAddress);
		output.writeString(number, serializedRef, repeated);
	}

	/**
	 * 此方法暂时不知道做什么用
	 * */
	@Override
	public void transfer(Pipe pipe, Input input, Output output, int number, boolean repeated) throws IOException {
		// TODO Auto-generated method stub
		String s = input.readString();
		output.writeString(number, s, repeated);
	}

	/**
	 * 获取目标类型
	 * @return 返回actor类型
	 * */
	@Override
	public Class<?> typeClass() {
		// TODO Auto-generated method stub
		return ActorRef.class;
	}



}
