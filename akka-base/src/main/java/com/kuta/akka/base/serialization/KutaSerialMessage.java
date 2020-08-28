package com.kuta.akka.base.serialization;

/**
 * Kuta序列化消息
 * */
public class KutaSerialMessage implements KutaSerializable {

	/**
	 * byte数据长度
	 * */
	private int _dataSize = 0;
	
	/**
	 * 获取byte数据长度
	 * */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return _dataSize;
	}

	/**
	 * 设置byte数据长度
	 * */
	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub
		_dataSize = size;
	}

}
