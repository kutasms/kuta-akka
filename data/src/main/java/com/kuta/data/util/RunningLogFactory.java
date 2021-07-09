package com.kuta.data.util;

import java.util.Date;

import com.kuta.data.akka.pojo.AkkaNodeInfo;
import com.kuta.data.mongo.dao.RunningLogDao;
import com.kuta.data.mongo.pojo.LogLevel;
import com.kuta.data.mongo.pojo.RunningLog;

public class RunningLogFactory {
	public static void write(String topic,String detail, LogLevel level, Class<?> clazz,AkkaNodeInfo nodeInf) {
		RunningLogDao.get().insert(
				new RunningLog(
						nodeInf.getName(), 
						nodeInf.getHost(), 
						nodeInf.getPort(), 
						Thread.currentThread().getId(), 
						topic, 
						detail, 
						new Date(), level, clazz.getName(), ""));
	}
	
	public static void write(String topic,
			String detail, 
			LogLevel level, 
			Class<?> clazz,
			Throwable throwable,
			AkkaNodeInfo nodeInf) {
		RunningLogDao.get().insert(
				new RunningLog(
						nodeInf.getName(), 
						nodeInf.getHost(), 
						nodeInf.getPort(), 
						Thread.currentThread().getId(), 
						topic, 
						detail, 
						new Date(), level, clazz.getName(), throwable));
	}
}
