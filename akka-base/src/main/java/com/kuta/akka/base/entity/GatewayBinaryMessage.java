package com.kuta.akka.base.entity;

/**
 * 网关二进制消息
 * */
public class GatewayBinaryMessage extends GatewayMessage {
	private int sid;
	private byte[] data;
	private int did;
	
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
	public int getSid() {
		return sid;
	}
	/**
	 * 设置用户编号
	 * */
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
}
