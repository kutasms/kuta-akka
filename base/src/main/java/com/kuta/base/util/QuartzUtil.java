package com.kuta.base.util;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.Calendar;
import org.quartz.TriggerUtils;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * Quartz相关工具类
 * */
public class QuartzUtil {
	/**
	 * 计算之后‘numTimes’次执行时间
	 * */
	public static List<Date> fireTimes(String cronExp, int numTimes) throws ParseException{
		return fireTimes(cronExp, numTimes, new BaseCalendar());
	}
	/**
	 * 计算之后‘numTimes’次执行时间
	 * */
	public static List<Date> fireTimes(String cronExp, int numTimes,Calendar calendar) throws ParseException{
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
		cronTriggerImpl.setCronExpression(cronExp);
		return TriggerUtils.computeFireTimes(cronTriggerImpl, calendar, numTimes);
	}
}
