package com.kuta.akka.base.entity;

/**
 * 网关二进制消息
 * */
public class GatewayBinaryMessage extends GatewayMessage {
	private int uid;
	private byte[] data;
	
	/**
	 * 获取数据
	 * */
	public byte[] getData() {
		return data;
	}
	/**
	 * 设置数据
	 * */
	public void setData(byte[] data) {
		this.data = data;
	}
	/**
	 * 获取用户编号
	 * */
	public int getUid() {
		return uid;
	}
	/**
	 * 设置用户编号
	 * */
	public void setUid(int uid) {
		this.uid = uid;
	}
}
