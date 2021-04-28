package com.kuta.akka.base.auto;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension;

/**
 * 插件调度器
 * 
 * */
public class PluginScheduler {
	
	public enum Mode{
		
	}
	
	private PluginScheduler() {
		
	}
	
	private QuartzSchedulerExtension extension = null;
	private PluginDataSource dataSource = null;
	
	
	
	public PluginScheduler withExtension(QuartzSchedulerExtension extension) throws SchedulerException {
		this.extension = extension;
		TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("")).build();
		CronTrigger cronTrigger;
		JobDetail detail;
		Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
		//		this.extension.scheduler().
		PluginDataSource<JSONTaskData> data = null;
		PluginJobBuilder
		.newJob()
		.withBuilder(
				JSONPluginTaskDataBuilder
				.newBuilder()
				.withDataSource(data))
		.build();
		return this;
	}

	
}
