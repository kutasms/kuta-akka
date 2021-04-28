package com.kuta.akka.base.auto;

import java.util.Date;

import org.quartz.CronTrigger;

public interface PluginCronTrigger extends PluginTrigger, CronTrigger {
	
	/**
	 * 根据时间执行一次任务，并且只执行一次
	 * */
	String getCron(Date time);
}
