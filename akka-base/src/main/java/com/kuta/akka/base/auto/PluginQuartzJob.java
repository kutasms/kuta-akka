package com.kuta.akka.base.auto;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.kuta.base.exception.KutaRuntimeException;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.event.EventStream;

public abstract  class PluginQuartzJob implements PluginJob {

	protected abstract PluginJobSendWrapper<?> beforeExecute(JobExecutionContext context);
	protected abstract void afterExecute(JobExecutionContext context);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		PluginJobSendWrapper<?> result = beforeExecute(context);
		Class<?> clazz = result.getReceiver().getClass();

		if(clazz.equals(ActorRef.class)) {
			((ActorRef)result.getReceiver()).tell(result.getMessage(), ActorRef.noSender());
		}
		else if(clazz.equals(ActorSelection.class)) {
			((ActorSelection)result.getReceiver()).tell(result.getMessage(), ActorRef.noSender());
		} 
		else if(clazz.equals(EventStream.class)) {
			((EventStream)result.getReceiver()).publish(result.getMessage());
		} else {
			throw new KutaRuntimeException("任务未找到合适的接收者, 任务参数:" + result.getJSONMessage());
		}
		afterExecute(context);
	}

}
