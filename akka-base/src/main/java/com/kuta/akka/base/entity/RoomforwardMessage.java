package com.kuta.akka.base.entity;

/**
 * 房间转发消息
 * */
public class RoomforwardMessage extends GatewayMessage {

	/**
	 * 房间编号
	 * */
	private Long roomId;
	
	/**
	 * 获取房间编号
	 * */
	public Long getRoomId() {
		return roomId;
	}
	
	/**
	 * 设置房间编号
	 * */
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
