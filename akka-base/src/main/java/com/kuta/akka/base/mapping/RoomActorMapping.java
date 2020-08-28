package com.kuta.akka.base.mapping;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;

/**
 * 房间Actor映射
 * */
public class RoomActorMapping {
	
	/**
	 * 映射Map
	 * */
	private final Map<Long, ActorRef> mapping;
	
	/**
	 * 构造函数
	 * */
	public RoomActorMapping() {
		mapping = new HashMap<Long, ActorRef>();
	}
	/**
	 * 获取映射
	 * @param roomId 房间编号
	 * @return actor引用
	 * */
	public ActorRef get(Long roomId) {
		return mapping.get(roomId);
	}
	
	/**
	 * 加入映射
	 * @param roomId 房间编号
	 * @param actor actor引用
	 * */
	public void put(Long roomId, ActorRef actor) {
		mapping.put(roomId, actor);
	}
	
	/**
	 * 删除映射
	 * @param roomId 房间编号
	 * */
	public void remove(Long roomId) {
		mapping.remove(roomId);
	}
	
	/**
	 * 是否包含actor
	 * @param actor actor引用
	 * @return true:是,false:否
	 * */
	public boolean contains(ActorRef actor) {
		return mapping.containsValue(actor);
	}
	
	/**
	 * 删除映射
	 * @param actor actor引用
	 * */
	public void terminal(ActorRef actor) {
		
		for(Map.Entry<Long, ActorRef> entry : mapping.entrySet()) {
			if(entry.getValue().equals(actor)) {
				mapping.remove(entry.getKey(),entry.getValue());
			}
		}
	}
}
