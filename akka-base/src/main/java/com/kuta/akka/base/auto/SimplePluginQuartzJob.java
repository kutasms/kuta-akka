package com.kuta.akka.base.auto;

import org.quartz.JobExecutionContext;

import akka.actor.ActorRef;

public class SimplePluginQuartzJob extends PluginQuartzJob {

	@Override
	protected PluginJobSendWrapper<?> beforeExecute(JobExecutionContext context) {
		// TODO Auto-generated method stub
		Object message = context.get(PluginJobInternalParamNames.MESSAGE.toString());
		Object receiver = context.get(PluginJobInternalParamNames.RECEIVER.toString());
		
		return new PluginJobSendWrapper<ActorRef>((ActorRef)receiver, message);
	}

	@Override
	protected void afterExecute(JobExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

}
