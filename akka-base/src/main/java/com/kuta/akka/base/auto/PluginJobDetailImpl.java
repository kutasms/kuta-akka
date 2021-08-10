package com.kuta.akka.base.auto;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.impl.JobDetailImpl;

import akka.actor.ActorRef;

public class PluginJobDetailImpl<T extends PluginTaskData> extends JobDetailImpl implements PluginJobDetail<T> {

	private PluginJobKey pluginKey;
	
	private Class<? extends PluginJob> pluginJobClass;

	private PluginDataSource<T> dataSource;
	
	private ActorRef receiver;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4317308521882468063L;

	@Override
	public JobKey getKey() {
		// TODO Auto-generated method stub
		return super.getKey();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return super.getDescription();
	}


	@Override
	public Class<? extends Job> getJobClass() {
		// TODO Auto-generated method stub
		return super.getJobClass();
	}

	@Override
	public JobDataMap getJobDataMap() {
		// TODO Auto-generated method stub
		return super.getJobDataMap();
	}

	@Override
	public boolean isDurable() {
		// TODO Auto-generated method stub
		return super.isDurable();
	}

	@Override
	public boolean isPersistJobDataAfterExecution() {
		// TODO Auto-generated method stub
		return super.isPersistJobDataAfterExecution();
	}

	@Override
	public boolean isConcurrentExectionDisallowed() {
		// TODO Auto-generated method stub
		return super.isConcurrentExectionDisallowed();
	}

	@Override
	public boolean requestsRecovery() {
		// TODO Auto-generated method stub
		return super.requestsRecovery();
	}

	@Override
	public JobBuilder getJobBuilder() {
		// TODO Auto-generated method stub
		return super.getJobBuilder();
	}

	@Override
	public PluginJobKey getPluginKey() {
		// TODO Auto-generated method stub
		return this.pluginKey;
	}

	public void setPluginKey(PluginJobKey pluginKey) {
		this.pluginKey = pluginKey;
		if (pluginKey.getKey() != null) {
			super.setKey(pluginKey.getKey());
		}
	}


	@Override
	public Class<? extends PluginJob> getPluginJobClass() {
		// TODO Auto-generated method stub
		return this.pluginJobClass;
	}

	public void setPluginJobClass(Class<? extends PluginJob> clazz) {
		this.pluginJobClass = clazz;
		super.setJobClass(clazz);
	}


	@Override
	public PluginDataSource<T> getDataSource() {
		// TODO Auto-generated method stub
		return this.dataSource;
	}
	
	public void setDataSource(PluginDataSource<T> dataSource) {
		this.dataSource = dataSource;
		super.getJobDataMap().put(PluginJobInternalParamNames.MESSAGE.toString(), dataSource.getData());
		super.getJobDataMap().put(PluginJobInternalParamNames.RECEIVER.toString(), receiver);
		super.getJobDataMap().put(PluginJobInternalParamNames.ENTITY.toString(), dataSource.getEntity());
		super.getJobDataMap().put(PluginJobInternalParamNames.REPORT_PARAMS.toString(),dataSource.getHttpReports());
		super.getJobDataMap().put(PluginJobInternalParamNames.REQUEST_PARAMS.toString(),dataSource.getHttpRequests());
		super.getJobDataMap().put(PluginJobInternalParamNames.MODE.toString(), dataSource.getMode().toString());
	}

	@Override
	public ActorRef getReceiver() {
		// TODO Auto-generated method stub
		return this.receiver;
	}
	
	public void setReceiver(ActorRef receiver) {
		this.receiver = receiver;
	}
	
}
