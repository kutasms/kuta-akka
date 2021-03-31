package com.kuta.akka.base;

import java.util.TimeZone;

import javax.enterprise.inject.New;

import org.quartz.Calendar;
import org.quartz.Scheduler;

import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

import akka.japi.Option;

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
//		extension.schedule(name, receiver, msg)
	}
	
	@Override
	public void postStop() {
		cluster.unsubscribe(self());
		extension.shutdown(false);
	}
}
