package com.kuta.base.database;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.exception.KutaRuntimeException;

import redis.clients.jedis.Pipeline;

public abstract class KutaExpireMapBiz<T extends KutaDBEntity, TKey extends Number> extends KutaMapBiz<T, TKey> {

	private int expire;
	
	public KutaExpireMapBiz(String cacheName,final int expire) {
		super(cacheName);
		this.expire = expire;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cache(T ins, Pipeline pipe, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		super.cache(ins, pipe, args);
		String cacheKey = formatCacheKey(args);
		pipe.expire(cacheKey, expire);
	}

	@Override
	public void cache(T ins, JedisClient jedis, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		super.cache(ins, jedis, args);
		String cacheKey = formatCacheKey(args);
		jedis.expire(cacheKey, expire);
	}

	@Override
	public void cache(T ins, JedisClient jedis, String cacheKey) {
		// TODO Auto-generated method stub
		super.cache(ins, jedis, cacheKey);
		jedis.expire(cacheKey, expire);
	}

	@Override
	public void cache(T ins, Pipeline pipeline, String cacheKey) {
		// TODO Auto-generated method stub
		super.cache(ins, pipeline, cacheKey);
		pipeline.expire(cacheKey, expire);
	}

}
