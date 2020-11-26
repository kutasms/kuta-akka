package com.kuta.base.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisPoolUtil;
import com.kuta.base.cache.JedisUtil;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public abstract class KutaMapBiz<T extends KutaDBEntity, TKey extends Number> extends KutaAbstractBiz<T, TKey>{


	public KutaMapBiz(String cacheName) {
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
	public void cache(T ins, JedisClient jedis, Object... args) throws KutaRuntimeException {
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
	public void cache(T ins, JedisClient jedis, String cacheKey) {
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
	public void cacheByKey(T entity, JedisClient jedis) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		TKey key = getKey(entity);
		Map<String, String> map = KutaBeanUtil.bean2Map(entity);
		if (!KutaUtil.isEmptyMap(map)) {
			jedis.hset(formatCacheKeyByTKey(key), map);
		}
	}
	
	
//	@Override
//	public TKey getKey(T t) {
//		// TODO Auto-generated method stub
//		java.lang.reflect.Field[] fields = KutaBeanUtil.getAllFields(t);
//		for (int i = 0; i < fields.length; i++) {
//			PrimaryKey primaryKey = fields[i].getAnnotation(PrimaryKey.class);
//			if(primaryKey != null) {
//				try {
//					fields[i].setAccessible(true);
//					@SuppressWarnings("unchecked")
//					TKey result = (TKey) fields[i].get(t);
//					return result;
//				} catch (IllegalArgumentException | IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return null;
//	}



	@Override
	public T getOne(SqlSession session, TKey key, JedisClient jedis, String cacheKey) throws Exception{
		
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
	public T getOne(TKey key, JedisClient jedis, String cacheKey) throws Exception {
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
	public T getOne(JSONObject param, JedisClient jedis, Object... args) throws Exception {
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
	public T getOne(JSONObject param,SqlSession session, JedisClient jedis, Object... args) throws Exception{
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
	public T getOne(TKey key, SqlSession session, JedisClient jedis, Object... args) throws Exception {
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
	public T getOne(TKey key, JedisClient jedis, Object... args) throws Exception {
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
