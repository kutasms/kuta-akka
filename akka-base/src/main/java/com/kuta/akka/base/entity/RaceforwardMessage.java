package com.kuta.akka.base.entity;

/**
 * 比赛转发消息
 * */
public class RaceforwardMessage extends GatewayMessage {

	/**
	 * 比赛编号
	 * */
	private Integer raceId;
	
	/**
	 * 获取比赛编号
	 * */
	public Integer getRaceId() {
		return raceId;
	}
	
	/**
	 * 设置比赛编号
	 * */
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

}
