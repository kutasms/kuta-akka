package com.kuta.data.mongo.pojo;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.kuta.base.database.mongodb.KutaMongoEntity;

/**
 * 	程序重要的运行日志
 * @author rhina
 */
public class RunningLog extends KutaMongoEntity {
	
	private String nodeName;
	private String nodeHost;
	private Integer nodePort;
	private Long threadId;
	private String topic;
	private String detail;
	private Date created;
	private LogLevel level;
	private String className;
	private String stackTrace;
	
	
	
	public RunningLog(String nodeName, String nodeHost, Integer nodePort, Long threadId, String topic, String detail,
			Date created, LogLevel level, String className, String stackTrace) {
		super();
		this.nodeName = nodeName;
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
		this.threadId = threadId;
		this.topic = topic;
		this.detail = detail;
		this.created = created;
		this.level = level;
		this.className = className;
		this.stackTrace = stackTrace;
	}

	public RunningLog(String nodeName, String nodeHost, Integer nodePort, Long threadId, String topic, String detail,
			LogLevel level, String className, String stackTrace) {
		super();
		this.nodeName = nodeName;
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
		this.threadId = threadId;
		this.topic = topic;
		this.detail = detail;
		this.created = new Date();
		this.level = level;
		this.className = className;
		this.stackTrace = stackTrace;
	}
	
	public RunningLog(String nodeName, String nodeHost, Integer nodePort, Long threadId, String topic, String detail,
			Date created, String className, Throwable throwable) {
		super();
		this.nodeName = nodeName;
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
		this.threadId = threadId;
		this.topic = topic;
		this.detail = detail;
		this.created = created;
		this.level = LogLevel.ERROR;
		this.className = className;
		this.stackTrace = ExceptionUtils.getStackTrace(throwable);
	}
	public RunningLog(String nodeName, String nodeHost, Integer nodePort, Long threadId, String topic, String detail,
			Date created, LogLevel level, String className, Throwable throwable) {
		super();
		this.nodeName = nodeName;
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
		this.threadId = threadId;
		this.topic = topic;
		this.detail = detail;
		this.created = created;
		this.level = level;
		this.className = className;
		this.stackTrace = ExceptionUtils.getStackTrace(throwable);
	}
	
	/**
	 * 	获取类名称
	 * */
	public String getClassName() {
		return className;
	}
	
	/**
	 * 	获取创建时间
	 * */
	public Date getCreated() {
		return created;
	}
	/**
	 * 	获取详细信息
	 * */
	public String getDetail() {
		return detail;
	}
	/**
	 * 	获取日志级别
	 * */
	public LogLevel getLevel() {
		return level;
	}
	/**
	 *	获取节点HOST信息
	 * */
	public String getNodeHost() {
		return nodeHost;
	}
	/**
	 * 	获取节点名称
	 * */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * 	获取节点端口号
	 * */
	public Integer getNodePort() {
		return nodePort;
	}
	/**
	 * 	获取堆栈跟踪信息
	 * */
	public String getStackTrace() {
		return stackTrace;
	}
	/**
	 * 	获取线程编号
	 * */
	public Long getThreadId() {
		return threadId;
	}
	/**
	 * 	获取主题
	 * */
	public String getTopic() {
		return topic;
	}
	/**
	 * 	设置类名称
	 * */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 	设置创建时间
	 * */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * 	设置详细信息
	 * */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * 	设置日志级别
	 * */
	public void setLevel(LogLevel level) {
		this.level = level;
	}
	/**
	 * 	设置节点HOST信息
	 * */
	public void setNodeHost(String nodeHost) {
		this.nodeHost = nodeHost;
	}
	/**
	 * 	设置节点名称
	 * */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * 	设置节点端口号
	 * */
	public void setNodePort(Integer nodePort) {
		this.nodePort = nodePort;
	}
	/**
	 * 	设置堆栈跟踪信息
	 * */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	/**
	 * 	设置线程编号
	 * */
	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}
	/**
	 * 	设置主题
	 * */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
