package com.kuta.akka.base.auto;

import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

public class PluginSchedulerWrapper {
	private QuartzSchedulerExtension extension;
//	private ActorContext context;
	
//	private void plan(PluginOrganization plugin) {
//		
//	}
	
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
