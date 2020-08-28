package com.kuta.akka.base.serialization;

import java.io.NotSerializableException;

import com.kuta.base.serialization.ProtostuffUtil;

import akka.actor.Address;
import akka.actor.ExtendedActorSystem;
import akka.cluster.Cluster;
import akka.serialization.SerializerWithStringManifest;

/**
 * KutaProtobuff序列化器
 * */
public class KutaSerializer extends SerializerWithStringManifest {

	/**
	 * 可用于序列化和反序列化actor的system
	 * */
	final ExtendedActorSystem system;

	/**
	 * 本节点远程通信Address
	 * */
	final Address selfAddress;
	/**
	 * 构造函数
	 * */
	public KutaSerializer(ExtendedActorSystem system) {
		this.system = system;
	    selfAddress = Cluster.get(system).selfAddress();
	}
	
	@Override
	public Object fromBinary(byte[] bytes, String manifest) throws NotSerializableException {
		// TODO Auto-generated method stub
		try {
			KutaSerializable serializable = (KutaSerializable)ProtostuffUtil.deserialize(bytes, Class.forName(manifest), this.system);
			serializable.setSize(bytes.length);
			return serializable;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotSerializableException();
		}
	}

	@Override
	public int identifier() {
		// TODO Auto-generated method stub
		return 153668554;
	}

	@Override
	public String manifest(Object o) {
		// TODO Auto-generated method stub
		return o.getClass().getName();
	}

	@Override
	public byte[] toBinary(Object o) {
		return ProtostuffUtil.serialize(o, system);
	}

}
