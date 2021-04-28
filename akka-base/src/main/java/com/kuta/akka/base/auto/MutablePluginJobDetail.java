package com.kuta.akka.base.auto;

import org.quartz.JobDetail;

import akka.actor.ActorRef;

public interface MutablePluginJobDetail<T extends PluginTaskData> extends PluginJobDetail<T>, JobDetail {

	public void setDataSource(PluginDataSource<T> dataSource);

	public void setPluginJobClass(Class<? extends PluginJob> clazz);
	
	public void setPluginKey(PluginJobKey pluginKey);
	
	public void setReceiver(ActorRef receiver);
}
