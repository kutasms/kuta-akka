package com.kuta.akka.base.entity;

import akka.actor.ActorRef;

/**
 * 比赛已创建消息
 * */
public class RaceCreatedMessage {
	
	/**
	 * 比赛已创建消息
	 * */
	private Integer raceId;
	
	/**
	 * 比赛已创建消息
	 * */
	private ActorRef actorRef;
	
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
	
	/**
	 * 获取Actor
	 * */
	public ActorRef getActorRef() {
		return actorRef;
	}
	
	/**
	 * 设置Actor
	 * */
	public void setActorRef(ActorRef actorRef) {
		this.actorRef = actorRef;
	}
}
