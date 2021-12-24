package com.kuta.base.database;

import org.apache.ibatis.session.SqlSession;

import com.kuta.base.cache.JedisClient;

/**
 * 附带原子增加功能的数据处理器
 * */
public abstract class KutaMapWithIncrBiz<T extends KutaDBEntity, TKey extends Number> extends KutaMapBiz<T, TKey>{
	
	private final int CACHE_EXPIRE;
	/**
	 * 构造函数
	 * @param cacheKey 缓存键
	 * */
	public KutaMapWithIncrBiz(String cacheKey, int expire) {
		super(cacheKey);
		this.CACHE_EXPIRE = expire;
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
	@Deprecated
	public void incrCacheDB(SqlSession session,JedisClient jedis,T t, TKey key) throws Exception {
		
		incrDBByKey(session,t, key);
		getOneByKey(session, jedis, key);
		if(CACHE_EXPIRE != -1) {
			jedis.expire(formatCacheKeyByTKey(key), CACHE_EXPIRE);
		}
	}
	public void incrCacheDB(DataSessionFactory f,T t, TKey key) throws Exception {
		f.enableJedisTrans();
		incrDBByKey(f.getSqlSession(),t, key);
		getOneByKey(f, key);
		if(CACHE_EXPIRE != -1) {
			f.getJedis().expire(formatCacheKeyByTKey(key), CACHE_EXPIRE);
		}
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
	@Deprecated
	public void incrCacheDBWithArgs(SqlSession session,JedisClient jedis,T t,TKey key, Object... args) throws Exception {
		jedis.del(formatCacheKey(args));
		incrDBByKey(session, t, key);
		getOne(key, session, jedis, args);
		if(CACHE_EXPIRE != -1) {
			jedis.expire(formatCacheKey(args), CACHE_EXPIRE);
		}
	}
	
	public void incrCacheDBWithArgs(DataSessionFactory f,T t,TKey key, Object... args) throws Exception {
		f.enableJedisTrans();
		f.getJedis().del(formatCacheKey(args));
		incrDBByKey(f.getSqlSession(), t, key);
		getOne(f, key, args);
		if(CACHE_EXPIRE != -1) {
			f.getJedis().expire(formatCacheKey(args), CACHE_EXPIRE);
		}
	}
	
}
