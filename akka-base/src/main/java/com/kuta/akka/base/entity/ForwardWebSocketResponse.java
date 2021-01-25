package com.kuta.akka.base.entity;

import java.io.Serializable;

/**
 * 转发回复消息
 * */
public class ForwardWebSocketResponse extends KutaWebSocketResponse implements Serializable{

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -7716001258830617118L;

	/**
	 * 用户编号
	 * */
	private Integer pid;
	
	/**
	 * 是否保存离线消息
	 * */
	private boolean saveOffline = true;
	
	/**
	 * 获取用户编号
	 * */
	public Integer getPid() {
		return pid;
	}

	/**
	 * 设置用户编号
	 * */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public boolean isSaveOffline() {
		return saveOffline;
	}

	public void setSaveOffline(boolean saveOffline) {
		this.saveOffline = saveOffline;
	}
}
