package com.kuta.akka.base;

import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

/**
 * 附带集群信息和Quartz扩展的Actor
 * */
public abstract class KutaActorWithClusterQuartz extends KutaActorWithCluster {
	
	/**
	 * Quartz调度扩展
	 * */
	protected QuartzSchedulerExtension extension;
	
	@Override
	public void preStart() {
		// TODO Auto-generated method stub
		super.preStart();
		extension = QuartzSchedulerExtension.get(getContext().getSystem());
	}
	
	@Override
	public void postStop() {
		cluster.unsubscribe(self());
		extension.shutdown(false);
	}
}
