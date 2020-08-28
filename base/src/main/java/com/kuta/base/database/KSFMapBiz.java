package com.kuta.base.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.cache.JedisUtil;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public abstract class KSFMapBiz<T extends KutaDBEntity, TKey extends Number> extends KutaAbstractBiz<T, TKey>{


	public KSFMapBiz(String cacheName) {
		super(cacheName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cache(T ins, Pipeline pipe, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		Map<String, String> map = KutaBeanUtil.bean2Map(ins);
		if (!KutaUtil.isEmptyMap(map)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			else {
				throw new KutaIllegalArgumentException("请传入JEDIS.KEY格式化参数");
			}
			pipe.hset(cacheName, map);
		}
	}

	@Override
	public void cache(T ins, Jedis jedis, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		Map<String, String> map = KutaBeanUtil.bean2Map(ins);
		if (!KutaUtil.isEmptyMap(map)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			else {
				throw new IllegalArgumentException("请传入JEDIS.KEY格式化参数");
			}
			jedis.hset(cacheName, map);
		}
	}
	
	@Override
	public void cache(T ins, Jedis jedis, String cacheKey) {
		// TODO Auto-generated method stub
		Map<String, String> map = KutaBeanUtil.bean2Map(ins);
		if (!KutaUtil.isEmptyMap(map)) {
			jedis.hset(cacheKey, map);
		}
	}
	@Override
	public void cache(T ins, Pipeline pipeline, String cacheKey) {
		// TODO Auto-generated method stub
		Map<String, String> map = KutaBeanUtil.bean2Map(ins);
		if (!KutaUtil.isEmptyMap(map)) {
			pipeline.hset(cacheKey, map);
		}
	}

	@Override
	public void cache(T ins, Object... args) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		Map<String, String> map = KutaBeanUtil.bean2Map(ins);
		if (!KutaUtil.isEmptyMap(map)) {
			String cacheName = CACHE_KEY;
			if (!KutaUtil.isValueNull(args)) {
				cacheName = formatCacheKey(args);
			}
			JedisUtil.mapBatchSet(cacheName, map);
		}
	}

	@Override
	public T getOne(SqlSession session, TKey key, Jedis jedis, String cacheKey) throws Exception{
		
		Map<String, String> map = jedis.hgetAll(cacheKey);
		if (KutaUtil.isEmptyMap(map)) {
			T ins = get(session, key);
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			
			cache(ins, jedis, cacheKey);
			return ins;
		}
		return KutaBeanUtil.map2Bean(map, entityClazz);
	}

	
	@Override
	public T getOne(TKey key, Jedis jedis, String cacheKey) throws Exception {
		// TODO Auto-generated method stub

		Map<String, String> map = jedis.hgetAll(cacheKey);
		if (KutaUtil.isEmptyMap(map)) {
			T ins = KutaSQLUtil.exec(x->{
				T obj = get(x, key);
				if(KutaUtil.isValueNull(obj)) {
					return null;
				}
				return obj;
			});
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			cache(ins, jedis, cacheKey);
			return ins;
		}
		return KutaBeanUtil.map2Bean(map, entityClazz);
	}
	
	@Override
	public T getOne(JSONObject param, Jedis jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
		String cacheKey = CACHE_KEY;
		if(!KutaUtil.isValueNull(args)) {
			cacheKey = formatCacheKey(args);
		}
		else {
			throw new IllegalArgumentException("请传入JEDIS.KEY格式化参数");
		}
		Map<String, String> map = jedis.hgetAll(cacheKey);
		if (KutaUtil.isEmptyMap(map)) {
			T ins = KutaSQLUtil.exec(x->{
				List<T> list = select(x, param);
				if(KutaUtil.isEmptyColl(list)) {
					return null;
				}
				return list.get(0);
			});
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			cache(ins, jedis, args);
			return ins;
		}
		return KutaBeanUtil.map2Bean(map, entityClazz);
	}

	@Override
	public T getOne(JSONObject param,SqlSession session, Jedis jedis, Object... args) throws Exception{
		// TODO Auto-generated method stub
				String cacheKey = CACHE_KEY;
				if(!KutaUtil.isValueNull(args)) {
					cacheKey = formatCacheKey(args);
				}
				else {
					throw new IllegalArgumentException("请传入JEDIS.KEY格式化参数");
				}
				Map<String, String> map = jedis.hgetAll(cacheKey);
				if (KutaUtil.isEmptyMap(map)) {
					List<T> list = select(session, param);
					if(KutaUtil.isEmptyColl(list)) {
						return null;
					}
					cache(list.get(0), jedis, args);
					return list.get(0);
				}
				return KutaBeanUtil.map2Bean(map, entityClazz);
	}
	
	@Override
	public T getOne(TKey key, SqlSession session, Jedis jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
				String cacheKey = CACHE_KEY;
				if(!KutaUtil.isValueNull(args)) {
					cacheKey = formatCacheKey(args);
				}
				else {
					throw new IllegalArgumentException("请传入JEDIS.KEY格式化参数");
				}
				Map<String, String> map = jedis.hgetAll(cacheKey);
				if (KutaUtil.isEmptyMap(map)) {
					T ins = get(session, key);
					if(KutaUtil.isValueNull(ins)) {
						return null;
					}
					cache(ins, jedis, args);
					return ins;
				}
				return KutaBeanUtil.map2Bean(map, entityClazz);
	}
	
	@Override
	public T getOne(TKey key, Jedis jedis, Object... args) throws Exception {
		// TODO Auto-generated method stub
		String cacheKey = CACHE_KEY;
		if(!KutaUtil.isValueNull(args)) {
			cacheKey = formatCacheKey(args);
		}
		else {
			throw new IllegalArgumentException("请传入JEDIS.KEY格式化参数");
		}
		Map<String, String> map = jedis.hgetAll(cacheKey);
		if (KutaUtil.isEmptyMap(map)) {
			T ins = KutaSQLUtil.exec(x->{
				T obj = get(x, key);
				if(KutaUtil.isValueNull(obj)) {
					return null;
				}
				return obj;
			});
			if(KutaUtil.isValueNull(ins)) {
				return null;
			}
			cache(ins, jedis, args);
			return ins;
		}
		return KutaBeanUtil.map2Bean(map, entityClazz);
	}
	
	
}
