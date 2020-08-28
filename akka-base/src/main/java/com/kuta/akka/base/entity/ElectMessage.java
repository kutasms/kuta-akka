package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

import akka.actor.ActorRef;

/**
 * 投票信息
 * <p>相同角色的多个节点共同发起投票</p>
 * */
public class ElectMessage extends KutaSerialMessage{
	
	/**
	 * 角色
	 * */
	private String role;
	/**
	 * 种子数
	 * */
	private Integer seed;
	/**
	 * Actor
	 * */
	private ActorRef actorRef;
	/**
	 * 名称
	 * */
	private String name;
	/**
	 * host和端口信息
	 * */
	private String hostport;
	
	/**
	 * 获取种子数
	 * */
	public Integer getSeed() {
		return seed;
	}
	/**
	 * 设置种子数
	 * */
	public void setSeed(Integer seed) {
		this.seed = seed;
	}
	/**
	 * 获取actor
	 * */
	public ActorRef getActorRef() {
		return actorRef;
	}
	/**
	 * 设置actor
	 * */
	public void setActorRef(ActorRef actorRef) {
		this.actorRef = actorRef;
	}
	/**
	 * 获取名称
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取角色
	 * */
	public String getRole() {
		return role;
	}
	/**
	 * 设置角色
	 * */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * 获取host和端口信息
	 * */
	public String getHostport() {
		return hostport;
	}
	/**
	 * 设置host和端口信息
	 * */
	public void setHostport(String hostport) {
		this.hostport = hostport;
	}
}
