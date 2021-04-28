package com.kuta.akka.base.auto;

import java.io.Serializable;

import org.quartz.JobDetail;

import com.kuta.akka.base.KutaActor;

import akka.actor.ActorRef;

public interface PluginJobDetail<T extends PluginTaskData> extends JobDetail,Cloneable, Serializable {
	
	 PluginJobKey getPluginKey();
	 PluginDataSource<T> getDataSource();
	 Class<? extends PluginJob>getPluginJobClass();
	 ActorRef getReceiver();
}
