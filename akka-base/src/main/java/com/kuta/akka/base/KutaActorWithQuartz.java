package com.kuta.akka.base;

import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

/**
 * <pre>
 * Quartz - 定时任务管理模块
 * 通过此Actor将Quartz引入akka集群
 * </pre>
 * */
public abstract class KutaActorWithQuartz extends KutaActor {

	/**
	 * Quartz调度扩展
	 * */
	protected QuartzSchedulerExtension extension;

	@Override
	public void postStop() throws Exception {
		// TODO Auto-generated method stub
		super.postStop();
		extension.shutdown(false);
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		extension = QuartzSchedulerExtension.get(getContext().getSystem());
	}

	

}
