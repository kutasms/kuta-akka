package com.kuta.akka.base.auto;

import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;

/**
 * 插件分发器
 * */
public class PluginDispatcher {
	public void test() {
		TriggerBuilder.newTrigger().withSchedule(
				CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
	}
}
