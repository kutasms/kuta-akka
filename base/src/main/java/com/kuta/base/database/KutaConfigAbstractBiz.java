package com.kuta.base.database;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.common.KutaCommonSettings;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaRedisUtil;

import redis.clients.jedis.Transaction;

/**
 * <b>抽象配置数据处理器</b>
 * <p>如果你的数据类似key-value配置，那么可以实现此类以实现对配置数据的缓存和数据库中数据的管理。</p>
 * */
public abstract class KutaConfigAbstractBiz<T extends KutaDBEntity> {

	/**
	 * 缓存键
	 * */
	protected String CACHE_KEY = "UNKNOWN_CACHE_KEY";
	
	
	/**
	 * 构造函数
	 * @param cacheKey 缓存键名
	 * */
	public KutaConfigAbstractBiz(String cacheKey) {
		// TODO Auto-generated constructor stub
		this.CACHE_KEY = String.format("%s_%s", KutaCommonSettings.getCacheKeyPrefix(), cacheKey);
	}

	public String getCacheKey() {
		return CACHE_KEY;
	}
	
	/**
	 * 从数据库中加载配置数据，并将数据组装为一个String。
	 * @param session 数据库连接
	 * @return 包含所有相关配置的json字符串
	 * */
	protected abstract String getJson(SqlSession session);
	protected String getJson(SqlSession session, int pageNum, int pageSize) {
		return getJson(session);
	}
	/**
	 * 更新配置数据至数据库
	 * @param session 数据库连接
	 * @param map 需要更新的键值对
	 * @return 受影响的数据行数
	 * */
	protected abstract int update(SqlSession session,Map<String, String> map);
	public abstract int updateWithOptimisticLock(SqlSession session,T entity);
	/**
	 * 从数据库中加载配置数据并将数据转换为HashMap
	 * @param session 数据库连接
	 * @return 键值对
	 * */
	protected abstract Map<String, String> getMap(SqlSession session);
	
	protected Map<String, String> getMap(SqlSession session, int pageNum, int pageSize){
		return getMap(session);
	}
	
	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson() throws Exception {
		KutaSQLUtil.exec(x -> {
			try {
				KutaRedisUtil.exec(redis -> {
					redis.set(CACHE_KEY, getJson(x));
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson(int pageNum, int pageSize) throws Exception {
		KutaSQLUtil.exec(x -> {
			try {
				KutaRedisUtil.exec(redis -> {
					redis.set(CACHE_KEY, getJson(x, pageNum, pageSize));
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson(int expire) throws Exception {
		KutaSQLUtil.exec(x -> {
			try {
				KutaRedisUtil.exec(redis -> {
					redis.setex(CACHE_KEY, expire, getJson(x));
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson(int expire, int pageNum, int pageSize) throws Exception {
		KutaSQLUtil.exec(x -> {
			try {
				KutaRedisUtil.exec(redis -> {
					redis.setex(CACHE_KEY, expire, getJson(x, pageNum, pageSize));
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson(SqlSession session, JedisClient jedis, int expire, int pageNum, int pageSize) throws Exception {
		jedis.setex(CACHE_KEY, expire, getJson(session, pageNum, pageSize));
	}
	
	public String getCollJson(DataSessionFactory f, int expire, int pageNum, int pageSize) throws Exception {
		String data = f.getJedis().get(CACHE_KEY);
		if(data == null) {
			String json = getJson(f.getSqlSession(), pageNum, pageSize);
			if(json == null) {
				return null;
			}
			f.getJedis().setex(CACHE_KEY, expire, json);
			return json;
		}
		return data;
	}
	
	
	/**
	 * 	将所有相关配置数据以hashmap的形式加载到缓存
	 *	已销毁，请使用cacheAllToHash
	 * */
	@Deprecated
	public void cacheAllWithHash() throws Exception {
		KutaSQLUtil.exec(session -> {
			try {
				KutaRedisUtil.exec(jedis -> {
					Map<String, String> map = getMap(session);
					jedis.hset(CACHE_KEY, map);
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * */
	public void cacheAllWithHash(int expire) throws Exception {
		KutaSQLUtil.exec(session -> {
			try {
				KutaRedisUtil.exec(jedis -> {
					Map<String, String> map = getMap(session);
					jedis.hset(CACHE_KEY, map);
					jedis.expire(CACHE_KEY, expire);
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(SqlSession session,JedisClient jedis) throws Exception {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(SqlSession session,JedisClient jedis, int expire) throws Exception {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, expire);
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(SqlSession session,JedisClient jedis, int expire, int pageNum, int pageSize) throws Exception {
		Map<String, String> map = getMap(session, pageNum, pageSize);
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, expire);
	}
	public boolean fullValid(SqlSession session,JedisClient jedis, String key) {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, 30 * 60);
		return map.containsKey(key);
	}
	public boolean fullValid(DataSessionFactory f, String key) {
		Map<String, String> map = getMap(f.getSqlSession());
		f.getJedis().hset(CACHE_KEY, map);
		f.getJedis().expire(CACHE_KEY, 30 * 60);
		return map.containsKey(key);
	}
	public boolean fullValid(DataSessionFactory f, String key, int expire) {
		Map<String, String> map = getMap(f.getSqlSession());
		f.getJedis().hset(CACHE_KEY, map);
		f.getJedis().expire(CACHE_KEY, expire);
		return map.containsKey(key);
	}
	
	public void cacheAllToHash(SqlSession session,Transaction jedis) throws Exception {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, 30 * 60);
	}

	public void cacheAllToHash(SqlSession session,Transaction jedis, int expire) throws Exception {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, expire);
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(JedisClient jedis) throws Exception {
		KutaSQLUtil.exec(session -> {
			Map<String, String> map = getMap(session);
			jedis.hset(CACHE_KEY, map);
			jedis.expire(CACHE_KEY, 30 * 60);
		});
	}
	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(JedisClient jedis, int expire) throws Exception {
		KutaSQLUtil.exec(session -> {
			Map<String, String> map = getMap(session);
			jedis.hset(CACHE_KEY, map);
			jedis.expire(CACHE_KEY, expire);
		});
	}
	public void cacheAllToHash(Transaction jedis) throws Exception {
		KutaSQLUtil.exec(session -> {
			Map<String, String> map = getMap(session);
			jedis.hset(CACHE_KEY, map);
			jedis.expire(CACHE_KEY, 30 * 60);
		});
	}
	public void cacheAllToHash(Transaction jedis, int expire) throws Exception {
		KutaSQLUtil.exec(session -> {
			Map<String, String> map = getMap(session);
			jedis.hset(CACHE_KEY, map);
			jedis.expire(CACHE_KEY, expire);
		});
	}
	
	/**
	 * 保存数据至数据库并且更新缓存数据
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param map 需要保存的键值对
	 * */
	public void dbCache(SqlSession session,JedisClient jedis,Map<String, String> map) {
		jedis.hset(CACHE_KEY, map); 
		jedis.expire(CACHE_KEY, 30 * 60);
		update(session,map);
	}
	/**
	 * 保存数据至数据库并且更新缓存数据
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param map 需要保存的键值对
	 * */
	public void dbCache(SqlSession session,JedisClient jedis,Map<String, String> map, int expire) {
		jedis.hset(CACHE_KEY, map);
		jedis.expire(CACHE_KEY, expire);
		update(session,map);
	}
	
	public int dbCacheWithOptimisticLock(SqlSession session,JedisClient jedis, T entity, int expire) {
		int result = updateWithOptimisticLock(session, entity);
		if(result > 0) {
			jedis.hset(CACHE_KEY, KutaBeanUtil.bean2Map(entity));
			jedis.expire(CACHE_KEY, expire);
		}
		return result;
	}
}
