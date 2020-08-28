package com.kuta.base.database;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.util.KSFRedisUtil;

import redis.clients.jedis.Jedis;

/**
 * <b>抽象配置数据处理器</b>
 * <p>如果你的数据类似key-value配置，那么可以实现此类以实现对配置数据的缓存和数据库中数据的管理。</p>
 * */
public abstract class KSFConfigAbstractBiz<T extends KSFDBEntity> {

	/**
	 * 缓存键
	 * */
	protected String CACHE_KEY = "UNKNOWN_CACHE_KEY";
	
	/**
	 * 构造函数
	 * @param cacheKey 缓存键名
	 * */
	public KSFConfigAbstractBiz(String cacheKey) {
		// TODO Auto-generated constructor stub
		this.CACHE_KEY = cacheKey;
	}

	/**
	 * 从数据库中加载配置数据，并将数据组装为一个String。
	 * @param session 数据库连接
	 * @return 包含所有相关配置的json字符串
	 * */
	protected abstract String getJson(SqlSession session);
	/**
	 * 更新配置数据至数据库
	 * @param session 数据库连接
	 * @param map 需要更新的键值对
	 * @return 受影响的数据行数
	 * */
	protected abstract int update(SqlSession session,Map<String, String> map);
	/**
	 * 从数据库中加载配置数据并将数据转换为HashMap
	 * @param session 数据库连接
	 * @return 键值对
	 * */
	protected abstract Map<String, String> getMap(SqlSession session);

	/**
	 * 将所有相关配置数据以json字符串的形式加载到缓存
	 * */
	public void cacheAllWithJson() throws Exception {
		KutaSQLUtil.exec(x -> {
			try {
				KSFRedisUtil.exec(redis -> {
					redis.set(CACHE_KEY, getJson(x));
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
	public void cacheAllWithHash() throws Exception {
		KutaSQLUtil.exec(session -> {
			try {
				KSFRedisUtil.exec(jedis -> {
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
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(SqlSession session,Jedis jedis) throws Exception {
		Map<String, String> map = getMap(session);
		jedis.hset(CACHE_KEY, map);
	}

	/**
	 * 将所有相关配置数据以hashmap的形式加载到缓存
	 * @param jedis redis连接
	 * */
	public void cacheAllToHash(Jedis jedis) throws Exception {
		KutaSQLUtil.exec(session -> {
			Map<String, String> map = getMap(session);
			jedis.hset(CACHE_KEY, map);
		});
	}
	
	/**
	 * 保存数据至数据库并且更新缓存数据
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param map 需要保存的键值对
	 * */
	public void dbCache(SqlSession session,Jedis jedis,Map<String, String> map) {
		jedis.hset(CACHE_KEY, map);
		update(session,map);
	}
}
