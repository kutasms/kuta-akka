package com.kuta.base.entity;

import akka.actor.ActorRef;

/**
 * 	实体包装器
 * */
public class EntityWrapper<T> {
	private T entity;
	private ActorRef actor;
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public ActorRef getActor() {
		return actor;
	}
	public void setActor(ActorRef actor) {
		this.actor = actor;
	}
	public EntityWrapper(T entity, ActorRef actorRef){
		this.entity = entity;
		this.actor = actorRef;
	}
}
