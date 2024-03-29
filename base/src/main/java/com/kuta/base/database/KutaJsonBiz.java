package com.kuta.base.database;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisUtil;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaUtil;

import redis.clients.jedis.Pipeline;

public abstract class KutaJsonBiz<T extends KutaDBEntity,TKey extends Number> extends KutaAbstractBiz<T, TKey>{

	public KutaJsonBiz(String cacheName) {
		super(cacheName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cache(T ins, Pipeline pipe, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		String json = JSONObject.toJSONString(ins);
		if(!KutaUtil.isEmptyString(json)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			pipe.hset(cacheName, KutaUtil.longToBase64(ins.getCacheField().longValue()), json);
		}
	}

	@Override
	public void cache(T ins, JedisClient jedis, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		String json = JSONObject.toJSONString(ins);
		if(!KutaUtil.isEmptyString(json)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			jedis.hset(cacheName, KutaUtil.longToBase64(ins.getCacheField().longValue()), json);
		}
	}

	@Override
	protected int remove(SqlSession session, TKey key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cache(T ins, JedisClient jedis, String cacheKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cache(T ins, Pipeline pipeline, String cacheKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getOne(TKey key, JedisClient jedis, String cacheKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getOne(SqlSession session, TKey key, JedisClient jedis, String cacheKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getOne(TKey key, SqlSession session, JedisClient jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(SqlSession session, TKey key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(SqlSession session, T record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SqlSession session, T record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cache(T ins, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		String json = JSONObject.toJSONString(ins);
		if(!KutaUtil.isEmptyString(json)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			JedisUtil.mapSet(cacheName, ins.getCacheField(), json);
		}
	}

	@Override
	@Deprecated
	public T getOne(JSONObject param, JedisClient jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
		String cacheKey = CACHE_KEY;
		if(!KutaUtil.isValueNull(args)) {
			cacheKey = formatCacheKey(args);
		}
		Long field = param.getLong("cache_key");
		String json = jedis.hget(cacheKey, KutaUtil.longToBase64(field));
		if(KutaUtil.isEmptyString(json)) {
			T ins = KutaSQLUtil.exec(x->{
				List<T> list = select(x, param);
				if(KutaUtil.isEmptyColl(list)) {
					return null;
				}
				cache(list.get(0), args);
				return list.get(0);
			});
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			cache(ins, jedis, args);
			return ins;
		}
		return JSONObject.parseObject(json, entityClazz);
	}

	@Override
	@Deprecated
	public T getOne(TKey key, JedisClient jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
		String cacheKey = CACHE_KEY;
		if(!KutaUtil.isValueNull(args)) {
			cacheKey = formatCacheKey(args);
		}
		String json = jedis.hget(cacheKey, KutaUtil.longToBase64(key.longValue()));
		if(KutaUtil.isEmptyString(json)) {
			T ins = KutaSQLUtil.exec(x->{
				T obj = get(x, key);
				if(KutaUtil.isValueNull(obj)) {
					return null;
				}
				cache(obj, args);
				return obj;
			});
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			cache(ins, jedis, args);
			return ins;
		}
		return JSONObject.parseObject(json, entityClazz);
	}
	
}
