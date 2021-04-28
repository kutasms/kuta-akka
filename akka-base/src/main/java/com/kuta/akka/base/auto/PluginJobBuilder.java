package com.kuta.akka.base.auto;

import java.util.UUID;

import org.quartz.JobDataMap;
import org.quartz.JobKey;

import akka.actor.ActorRef;

public class PluginJobBuilder<T extends PluginTaskData> {

	private PluginJobKey pluginKey;
	private Class<? extends PluginJob> jobClass;
	private String desc;
	private JobDataMap jobDataMap;
	private boolean durability;
	private boolean shouldRecover;
	private ActorRef receiver;
	private PluginTaskDataBuilder<?> dataBuilder;
	public static PluginJobBuilder<PluginTaskData> newJob() {
		return new PluginJobBuilder<PluginTaskData>();
	}
	
	public PluginJobBuilder<T> withDesc(String desc){
		this.desc = desc;
		return this;
	}
	
	public PluginJobBuilder<T> withPluginJobKey(PluginJobKey key){
		this.pluginKey = key;
		return this;
	}
	
	public PluginJobBuilder<T> withReceiver(ActorRef receiver){
		this.receiver = receiver;
		return this;
	}
	
	
	@SuppressWarnings("unchecked")
	public <TE extends T> PluginJobBuilder<TE> withBuilder(PluginTaskDataBuilder<TE> builder){
		this.dataBuilder = builder;
		return (PluginJobBuilder<TE>)this;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public PluginJobDetail<T> build(){
		if(this.dataBuilder == null) {
			this.dataBuilder = JSONPluginTaskDataBuilder.newBuilder();
		}
		PluginJobDetailImpl<?> impl = this.dataBuilder.build();
		
		impl.setJobClass(jobClass);
		impl.setDescription(desc);
		impl.setJobDataMap(jobDataMap);
		impl.setPluginJobClass(jobClass);
		if(pluginKey == null) {
			PluginJobKey key = new PluginJobKey();
			key.setKey(new JobKey(String.format("AKKA_%s", UUID.randomUUID().toString().replace("-", "")), null));
		} else {
			impl.setPluginKey(pluginKey);
		}
		impl.setDurability(durability);
		impl.setRequestsRecovery(shouldRecover);
		impl.setReceiver(receiver);
		return (PluginJobDetail<T>)impl;
	}
}
