package com.kuta.data.akka.pojo;

import com.kuta.base.collection.KutaHashSet;
/**
 * Akka节点信息
 * */
public class AkkaNodeInfo {
	
	private String name;
	private KutaHashSet<String> roles;
	private String hostport;
	private String host;
	private Integer port;
	
	/**
	 * 	获取HOST信息
	 * */
	public String getHost() {
		return host;
	}
	/**
	 * 	获取HOST和端口信息
	 * */
	public String getHostport() {
		return hostport;
	}
	/**
	 * 	获取节点名称
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 	获取端口信息
	 * */
	public Integer getPort() {
		return port;
	}
	/**
	 * 	获取角色集合
	 * */
	public KutaHashSet<String> getRoles() {
		return roles;
	}
	/**
	 * 	设置HOST信息
	 * */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 	设置HOST和端口信息
	 * */
	public void setHostport(String hostport) {
		this.hostport = hostport;
	}
	/**
	 * 	设置节点名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 	设置端口信息
	 * */
	public void setPort(Integer port) {
		this.port = port;
	}
	/**
	 * 	设置角色集合信息
	 * */
	public void setRoles(KutaHashSet<String> roles) {
		this.roles = roles;
	}
	
}
