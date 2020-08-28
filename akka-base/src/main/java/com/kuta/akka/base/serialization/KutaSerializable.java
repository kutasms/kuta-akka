package com.kuta.akka.base.serialization;

/**
 * Kuta序列化接口，实现此接口即可在akka集群通信中使用protobuff序列化
 * */
public interface KutaSerializable {
	/**
	 * 获取byte数据长度
	 * @return 数据长度
	 * */
	int getSize();
	/**
	 * 设置byte数据长度
	 * @param 数据长度
	 * */
	void setSize(int size);
}
