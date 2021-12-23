package com.kuta.base.database;

import org.apache.ibatis.session.SqlSession;

public abstract class KutaOptimisticLockBiz<T extends KutaDBEntity, TKey extends Number> extends KutaExpireMapBiz<T, TKey>{

	public KutaOptimisticLockBiz(String cacheName, int expire) {
		super(cacheName, expire);
		// TODO Auto-generated constructor stub
	}
	
	public abstract int updateWithOptimisticLock(SqlSession session, T entity);
	
	public int dbCacheWithOptimisticLock(DataSessionFactory f, T entity) {
		removeCache(f.getJedis(true), getKey(entity));
		return updateWithOptimisticLock(f.getSqlSession(), entity);
	}
}
