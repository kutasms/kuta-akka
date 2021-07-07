package com.kuta.akka.base.entity;

import java.util.Map;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 	数据扫描开始通知
 * */
public class DataScanStartNotice extends KutaSerialMessage{
	
	private String taskName;
	private Map<String, Object> params;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
