package com.kuta.akka.base.mapping;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;

/**
 * 比赛Actor映射
 * */
public class RaceActorMapping {
	
	/**
	 * 映射Map
	 * */
	private final Map<Integer, ActorRef> mapping;
	/**
	 * 构造函数
	 * */
	public RaceActorMapping() {
		mapping = new HashMap<Integer, ActorRef>();
	}
	/**
	 * 获取映射
	 * @param raceId 比赛编号
	 * @return actor引用
	 * */
	public ActorRef get(Integer raceId) {
		return mapping.get(raceId);
	}
	/**
	 * 加入映射
	 * @param raceId 比赛编号
	 * @param actor actor引用
	 * */
	public void put(Integer raceId, ActorRef actor) {
		mapping.put(raceId, actor);
	}
}
