package com.kuta.akka.base.auto;

import com.kuta.data.mysql.pojo.PluginOrganization;
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

import akka.actor.AbstractActor.ActorContext;

public class PluginSchedulerWrapper {
	private QuartzSchedulerExtension extension;
	private ActorContext context;
	
	private void plan(PluginOrganization plugin) {
		
	}
	
//	private <T> PluginDataSource<T> generateDataSource(
//			PluginOrganization plugin){
//		return null;
//	}
	
	public QuartzSchedulerExtension getExtension() {
		return this.extension;
	}
	
	public void startNow() {
		
	}
}
