package com.kuta.data.mongo.dao;

import com.kuta.base.database.mongodb.KutaMongoDao;
import com.kuta.data.mongo.pojo.RunningLog;

public class RunningLogDao extends KutaMongoDao<RunningLog> {

	public static RunningLogDao get() {
		return new RunningLogDao();
	}
}
