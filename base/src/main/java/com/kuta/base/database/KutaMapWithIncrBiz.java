package com.kuta.base.database;

import org.apache.ibatis.session.SqlSession;

import redis.clients.jedis.Jedis;

/**
 * 附带原子增加功能的数据处理器
 * */
public abstract class KutaMapWithIncrBiz<T extends KSFDBEntity, TKey extends Number> extends KSFMapBiz<T, TKey>{
	/**
	 * 构造函数
	 * @param cacheKey 缓存键
	 * */
	public KutaMapWithIncrBiz(String cacheKey) {
		super(cacheKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 数据库字段值增加指定数量
	 * <p>请在外部传入数据库连接</p>
	 * @param session 数据库连接
	 * @param t 数据实体，请为实体中需要更新的字段赋值
	 * @param key 数据主键
	 * */
	protected abstract void incrDBByKey(SqlSession session,T t, TKey key);
	
	/**
	 * 缓存和数据库指定列增加值
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param t 数据实体
	 * @param key 数据主键值
	 * @throws Exception 内部异常
	 * */
	public void incrCacheDB(SqlSession session,Jedis jedis,T t, TKey key) throws Exception {
		cacheIncr(jedis, t, key);
		incrDBByKey(session,t, key);
	}
	
	/**
	 * 缓存和数据库指定列增加值
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param t 数据实体
	 * @param key 数据主键值
	 * @param args 缓存键格式化参数
	 * @throws Exception 内部异常
	 * */
	public void incrCacheDBWithArgs(SqlSession session,Jedis jedis,T t,TKey key, Object... args) throws Exception {
		cacheIncr(jedis, t, args);
		incrDBByKey(session, t, key);
	}
}
