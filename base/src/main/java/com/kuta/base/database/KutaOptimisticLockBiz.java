package com.kuta.base.database;

import java.lang.reflect.Field;

import org.apache.ibatis.session.SqlSession;

import com.kuta.base.util.KutaBeanUtil;

public abstract class KutaOptimisticLockBiz<T extends KutaDBEntity, TKey extends Number> extends KutaExpireMapBiz<T, TKey>{

	public KutaOptimisticLockBiz(String cacheName, int expire) {
		super(cacheName, expire);
		// TODO Auto-generated constructor stub
	}
	
	public abstract int updateWithOptimisticLock(SqlSession session, T entity);
	
	public int dbCacheWithOptimisticLock(DataSessionFactory f, T entity) {
		removeCache(f.getJedis(true), getKey(entity));
		Field[] fields = KutaBeanUtil.getAllFields(entity);
		for(Field field : fields) {
			field.setAccessible(true);
			if(field.getName().equals("opVersion")) {
				try {
					long opVersion = Long.parseLong(field.get(entity).toString());
					field.set(entity, opVersion + 1L);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					logger.error("The opversion parameter must be of type long",e);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error("havn't permission to operate on the opversion field.");
				}
			}
		}
		return updateWithOptimisticLock(f.getSqlSession(), entity);
	}
}
