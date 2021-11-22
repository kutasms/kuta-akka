package com.kuta.base.database;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.common.KutaCommonSettings;
import com.kuta.base.exception.KutaError;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaRedisUtil;
import com.kuta.base.util.KutaTimeUtil;
import com.kuta.base.util.KutaUtil;

import redis.clients.jedis.Pipeline;

/**
 * <b>抽象数据处理器，包括对redis，mysql数据的处理</b>
 * <p>包括对缓存的读取、写入，对数据库的增删改查；</p>
 * <p>本抽象类实现了部分基础的业务组合，像缓存并更新、插入数据并缓存等；</p>
 * <p><b style='color:#ff0000;'>请注意如果在外部已经声明了数据库连接、redis连接，请选择能传入连接的方法。</b></p>
 * */
public abstract class KutaAbstractBiz<T extends KutaDBEntity, TKey extends Number> {

	
	/**
	 * slf4j日志接口
	 * */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 实体对象的类型
	 * */
	protected final Class<T> entityClazz;
	/**
	 * 缓存的键，这是一个模板，在实际使用中会使用相关字符替换其中的占位符
	 * */
	protected final String CACHE_KEY;
	/**
	 * 实体对应数据表的主键数据类型
	 * */
	private final Class<TKey> fieldClazz;

	
	/**
	 * 构造函数
	 * @param cacheKey 缓存键
	 * */
	@SuppressWarnings("unchecked")
	public KutaAbstractBiz(String cacheKey) {
		this.CACHE_KEY = String.format("%s_%s", KutaCommonSettings.getCacheKeyPrefix(),cacheKey);
		this.entityClazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		
		this.fieldClazz = (Class<TKey>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	
	/**
	 * 使用指定的参数集合格式化缓存键
	 * @param args 缓存键格式化参数
	 * @return String 已格式化的缓存键，可直接用于数据的读写
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	public String formatCacheKey(Object... args) throws KutaRuntimeException {
		if (!KutaUtil.isValueNull(args)) {
			String result = String.format(CACHE_KEY, args);
			return result;
		}
		throw new KutaRuntimeException(
				String.format(
						KutaError.CACHE_KEY_FORMAT_ERROR.getDesc(), args == null ? 
								KutaError.NOTHING_FOUND.getDesc() : 
									Arrays.deepToString(args)));
	}
	
	/**
	 * <p>删除缓存和数据库中的数据</p>
	 * <p>先删除数据库中的数据并返回受影响的数据行数</p>
	 * <p>由于mybatis批量删除可能不会返回受影响行数，所以直接删除缓存内容</p>
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param key 数据主键值
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	@Deprecated
	public int remove(SqlSession session,JedisClient jedis, TKey key, Object... args) throws KutaRuntimeException {
		int result = this.remove(session, key);
		String cacheKey = formatCacheKey(args);
		jedis.del(cacheKey);
		return result;
	}
	
	/**
	 * <p>删除缓存和数据库中的数据</p>
	 * <p>先删除数据库中的数据并返回受影响的数据行数</p>
	 * <p>由于mybatis批量删除可能不会返回受影响行数，所以直接删除缓存内容</p>
	 * @param factory 数据session工厂
	 * @param key 数据主键值
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	public int remove(DataSessionFactory factory, TKey key, Object... args) throws KutaRuntimeException {
		int result = this.remove(factory.getSqlSession(), key);
		String cacheKey = formatCacheKey(args);
		factory.getJedis().del(cacheKey);
		return result;
	}
	
	/**
	 * 删除缓存
	 * @param jedis redis连接
	 * @param key 数据主键
	 * */
	public void removeCache(JedisClient jedis,TKey key) {
		String cacheKey = formatCacheKeyByTKey(key);
		jedis.del(cacheKey);
	}
	/**
	 * 删除缓存
	 * @param jedis redis连接
	 * @param args 参数列表
	 * */
	public void removeCacheByArgs(JedisClient jedis, Object... args) {
		String cacheKey = formatCacheKey(args);
		jedis.del(cacheKey);
	}
	
	/**
	 * <p>删除缓存和数据库中的数据</p>
	 * <p>先删除数据库中的数据并返回受影响的数据行数</p>
	 * <p>由于mybatis批量删除可能不会返回受影响行数，所以直接删除缓存内容</p>
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param key 数据主键值
	 * @return 受影响的数据行数
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	public int remove(SqlSession session,JedisClient jedis, TKey key) throws KutaRuntimeException {
		int result = this.remove(session, key);
		String cacheKey = formatCacheKeyByTKey(key);
		jedis.del(cacheKey);
		return result;
	}
	/**
	 * <p>删除缓存和数据库中的数据</p>
	 * <p>先删除数据库中的数据并返回受影响的数据行数</p>
	 * <p>由于mybatis批量删除可能不会返回受影响行数，所以直接删除缓存内容</p>
	 * @param factory 数据Session工厂
	 * @param jedis redis连接
	 * @param key 数据主键值
	 * @return 受影响的数据行数
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	public int remove(DataSessionFactory factory, TKey key) throws KutaRuntimeException {
		int result = this.remove(factory.getSqlSession(), key);
		String cacheKey = formatCacheKeyByTKey(key);
		factory.getJedis().del(cacheKey);
		return result;
	}
	
	/**
	 * <p>删除缓存和数据库中的数据</p>
	 * <p>先删除数据库中的数据并返回受影响的数据行数</p>
	 * @param key 数据主键值
	 * @return 受影响的数据行数
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	@Deprecated
	public int remove(TKey key) throws Exception {
		return KutaSQLUtil.exec(session->{
			return KutaRedisUtil.exec(jedis->{
				int result = this.remove(session, key);
				String cacheKey = formatCacheKeyByTKey(key);
				jedis.del(cacheKey);
				return result;
			});
		});
	}

	/**
	 * 抽象的删除数据方法，在实现类中实现此方法以支持从数据库中删除数据
	 * @param session 数据库连接
	 * @param key 数据主键值
	 * @return 受影响的数据行数
	 * */
	protected abstract int remove(SqlSession session, TKey key);
	
	/**
	 * 以管线的方式将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param pipeline redis数据管线
	 * @param args 缓存键格式化参数
	 * @throws Exception 内部异常
	 * */
	public abstract void cache(T entity, Pipeline pipeline, Object... args) throws Exception;

	/**
	 * 在外部redis连接中，将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param jedis redis连接
	 * @param args 缓存键格式化参数
	 * @throws Exception 内部异常
	 * */
	public abstract void cache(T entity, JedisClient jedis, Object... args) throws Exception;
	
	/**
	 * 在内部redis连接中，将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param args 缓存键格式化参数
	 * @throws Exception 内部异常
	 * */
	public abstract void cache(T entity, Object... args) throws Exception;

	/**
	 * 在外部redis连接中，将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param jedis redis连接
	 * @param cacheKey 缓存键
	 * */
	public abstract void cache(T entity, JedisClient jedis, String cacheKey);
	/**
	 * 以管线的方式将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param pipeline redis数据管线
	 * @param cacheKey 缓存键
	 * */
	public abstract void cache(T entity, Pipeline pipeline, String cacheKey);
	
	/**
	 * 将实体对象写入缓存
	 * @param entity 数据对象实体
	 * @param jedis redis连接
	 * */
	public abstract void cacheByKey(T entity, JedisClient jedis) throws KutaRuntimeException;
	/**
	 * 将实体对象写入缓存并设置过期时间
	 * @param entity 数据对象实体
	 * @param jedis redis连接
	 * @param seconds 过期秒数
	 * */
	public abstract void cacheByKeyEx(T entity, JedisClient jedis, int seconds) throws KutaRuntimeException;
	/**
	 * <p>获取一个数据对象，仅传入redis连接，如果需要在内部创建数据库连接</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param param 需要查询的json键值对
	 * @param jedis redis连接
	 * @param args 缓存键格式化参数
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(JSONObject param, JedisClient jedis, Object... args) throws Exception;
	/**
	 * <p>获取一个数据对象，数据库连接和redis连接都从外部传递</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param param 需要查询的json键值对
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param args 缓存键格式化参数
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(JSONObject param, SqlSession session,JedisClient jedis, Object... args) throws Exception;
	/**
	 * <p>获取一个数据对象，仅传入redis连接，如果需要在内部创建数据库连接</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param key 数据主键，根据此主键查询
	 * @param jedis redis连接
	 * @param args 缓存键格式化参数
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(TKey key, JedisClient jedis, Object... args) throws Exception;
	/**
	 * <p>获取一个数据对象,仅传入redis连接，如果需要在内部创建数据库连接</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param key 数据主键，根据此主键查询
	 * @param jedis redis连接
	 * @param cacheKey 缓存键
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(TKey key, JedisClient jedis, String cacheKey) throws Exception;
	/**
	 * 从实体中获取主键值
	 * @param t 数据实体
	 * @return 主键值
	 * */
	public abstract TKey getKey(T t);
	
	/**
	 * <p>获取一个数据对象,数据库连接和redis连接都从外部传递</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param session 数据库连接
	 * @param key 数据主键，根据此主键查询
	 * @param jedis redis连接
	 * @param cacheKey 缓存键
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(SqlSession session,TKey key, JedisClient jedis, String cacheKey) throws Exception;
	/**
	 * <p>获取一个数据对象</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param factory 数据session工厂
	 * @param key 数据主键，根据此主键查询
	 * @param cacheKey 缓存键
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(DataSessionFactory factory,TKey key, String cacheKey) throws Exception;
	public abstract T getOne(DataSessionFactory factory,TKey key, Object... args) throws Exception;
	/**
	 * <p>获取一个数据对象,数据库连接和redis连接都从外部传递</p>
	 * <p>先从redis中获取，如果找不到则从数据库中获取</p>
	 * <p>如果成功从数据库中加载数据，则将数据加入缓存，然后返回该数据</p>
	 * @param key 数据主键，根据此主键查询
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param args 缓存键格式化参数
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T getOne(TKey key, SqlSession session, JedisClient jedis, Object... args) throws Exception;
	/**
	 * 从数据库中直接查询一个数据对象
	 * @param session 数据库连接
	 * @param key 数据主键，根据此主键查询
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public abstract T get(SqlSession session, TKey key) throws Exception;

	/**
	 * 向数据库插入一条数据
	 * @param session 数据库连接
	 * @param entity 数据对象
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public abstract int insert(SqlSession session, T entity) throws Exception;

	/**
	 * 更新数据库中的一条数据
	 * @param session 数据库连接
	 * @param entity 数据对象
	 * @return 受影响的数据行数 
	 * @throws Exception 内部异常
	 * */
	public abstract int update(SqlSession session, T entity) throws Exception;

	/**
	 * 从数据库中查询指定条件的数据
	 * @param session 数据库连接
	 * @param param 查询条件,json键值对
	 * @return 满足条件的数据列表
	 * @throws Exception 内部异常
	 * */
	public abstract List<T> select(SqlSession session, JSONObject param) throws Exception;

	/**
	 * 使用base64加密的数据主键查询一条数据
	 * @param key 数据主键，根据此主键查询
	 * @param jedis redis连接
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public T getOneByKey(TKey key, JedisClient jedis) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		return getOne(key, jedis, cacheKey);
	}
	/**
	 * 使用base64加密的数据主键查询一条数据，内部创建redis连接
	 * @param key 数据主键，根据此主键查询
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public T getOneByKey(TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		return KutaRedisUtil.exec(jedis -> {
			return getOne(key, jedis, cacheKey);
		});
	}
	/**
	 * 使用base64加密的数据主键查询一条数据
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param key 数据主键，根据此主键查询
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public T getOneByKey(SqlSession session, JedisClient jedis, TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		return getOne(session, key, jedis, cacheKey);
	}
	/**
	 * 使用base64加密的数据主键查询一条数据
	 * @param factory 数据session工厂
	 * @param key 数据主键，根据此主键查询
	 * @return T 获取的数据对象
	 * @throws Exception 内部异常
	 * */
	public T getOneByKey(DataSessionFactory factory, TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		return getOne(factory, key, cacheKey);
	}
	/**
	 * 将数据主键以base64作为field写入缓存，并写入数据库
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体
	 * @param key 数据主键
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int dbCacheWithKey(SqlSession session,JedisClient jedis,T entity, TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		return dbCache(session,jedis,entity, cacheKey);
	}
	/**
	 * 将数据主键以base64作为field写入缓存，并写入数据库
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int dbCacheWithKey(SqlSession session,JedisClient jedis,T entity) throws Exception {
		TKey key = getKey(entity);
		if(key == null) {
			throw new KutaIllegalArgumentException("entity.key is null.");
		}
		String cacheKey = formatCacheKeyByTKey(getKey(entity));
		return dbCache(session,jedis,entity, cacheKey);
	}
	/**
	 * 将数据主键以base64作为field写入缓存，并写入数据库
	 * @param factory 数据Session工厂,用于生成SqlSession和JedisClient
	 * @param entity 数据实体
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public int dbCacheWithKey(DataSessionFactory factory,T entity) throws Exception {
		TKey key = getKey(entity);
		if(key == null) {
			throw new KutaIllegalArgumentException("entity.key is null.");
		}
		String cacheKey = formatCacheKeyByTKey(getKey(entity));
		return dbCache(factory, entity, cacheKey);
	}
	
	/**
	 * 将数据写入缓存，并写入数据库
	 * @param entity 数据实体
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int dbCache(T entity, Object... args) throws Exception {
		int result = KutaSQLUtil.exec(x -> {
			return update(x, entity);
		});
		cache(entity, args);
		return result;
	}
	
	/**
	 * 将数据写入缓存，并写入数据库(外部传入数据库和缓存连接)
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体
	 * @param cacheKey 已格式化的缓存键
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int dbCache(SqlSession session,JedisClient jedis,T entity, String cacheKey) throws Exception {
		TKey key = getKey(entity);
		if(key == null) {
			throw new KutaIllegalArgumentException("请设置实体的主键");
		}
		int result = update(session, entity);
		cache(entity,jedis, cacheKey);
		return result;
	}
	/**
	 * 将数据写入缓存，并写入数据库
	 * @param factory 数据session工厂
	 * @param entity 数据实体
	 * @param cacheKey 已格式化的缓存键
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public int dbCache(DataSessionFactory factory,T entity, String cacheKey) throws Exception {
		TKey key = getKey(entity);
		if(key == null) {
			throw new KutaIllegalArgumentException("请设置实体的主键");
		}
		int result = update(factory.getSqlSession(), entity);
		cache(entity,factory.getJedis(), cacheKey);
		return result;
	}
	/**
	 * 将数据写入缓存，并写入数据库(外部传入数据库和缓存连接)
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体
	 * @param args 缓存参数集
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int dbCacheWithArgs(SqlSession session,JedisClient jedis,T entity,Object... args) throws Exception {
		String cacheKey = formatCacheKey(args);
		return dbCache(session,jedis,entity,cacheKey);
	}
	/**
	 * 将数据写入缓存，并写入数据库
	 * @param session 数据session工厂
	 * @param entity 数据实体
	 * @param args 缓存参数集
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public int dbCacheWithArgs(DataSessionFactory factory,T entity,Object... args) throws Exception {
		String cacheKey = formatCacheKey(args);
		return dbCache(factory, entity, cacheKey);
	}
	/**
	 * 插入数据至数据库并将数据缓存
	 * @param entity 数据实体 
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int insertCache(T entity, Object... args) throws Exception {
		int result = KutaSQLUtil.exec(x -> {
			return insert(x, entity);
		});
		cache(entity, args);
		return result;
	}

	/**
	 * 插入数据至数据库并将数据缓存(外部传入数据库和缓存连接)
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体 
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int insertCache(SqlSession session,JedisClient jedis, T entity, Object... args) throws Exception {
		int result = insert(session, entity);
		cache(entity,jedis,args);
		return result;
	}
	/**
	 * 插入数据至数据库并将数据缓存(外部传入数据库和缓存连接)
	 * @param factory 数据session工厂
	 * @param entity 数据实体 
	 * @param args 缓存键格式化参数
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public int insertCache(DataSessionFactory factory, T entity, Object... args) throws Exception {
		int result = insert(factory.getSqlSession(), entity);
		cache(entity,factory.getJedis(),args);
		return result;
	}
	/**
	 * 插入数据至数据库并将数据缓存(外部传入数据库和缓存连接)
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param entity 数据实体 
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public int insertCache(SqlSession session,JedisClient jedis, T entity) throws Exception {
		int result = insert(session, entity);
		cache(entity,jedis, formatCacheKeyByTKey(getKey(entity)));
		return result;
	}
	/**
	 * 插入数据至数据库并将数据缓存(外部传入数据库和缓存连接)
	 * @param factory 数据session工厂
	 * @param entity 数据实体 
	 * @return 受影响的数据行数
	 * @throws Exception 内部异常
	 * */
	public int insertCache(DataSessionFactory factory, T entity) throws Exception {
		int result = insert(factory.getSqlSession(), entity);
		cache(entity,factory.getJedis(), formatCacheKeyByTKey(getKey(entity)));
		return result;
	}
	/**
	 * 使用json键值对查询数据
	 * @param param json键值对(当使用field无法从redis中查询数据时使用此条件从数据库中加载数据)
	 * @param field 查询键
	 * @param keyArgs 缓存键格式化参数
	 * @return 查询结果
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public String query(JSONObject param, String field, Object... keyArgs) throws Exception {
		return KutaRedisUtil.exec(jedis -> {
			String cacheKey = CACHE_KEY;
			if (KutaUtil.isValueNull(keyArgs)) {
				cacheKey = formatCacheKey(keyArgs);
			}
			if (jedis.exists(cacheKey)) {
				return jedis.hget(cacheKey, field);
			}
			getOne(param, jedis, keyArgs);
			return jedis.hget(cacheKey, field);
		});
	}
	
	/**
	 * 使用数据主键的base64编码格式化redis存储键
	 * @param key 数据主键值
	 * @return 已格式化的缓存键
	 * @throws KutaRuntimeException KSF运行时异常
	 * */
	protected String formatCacheKeyByTKey(TKey key) throws KutaRuntimeException {
		String result = null;
		if(key.getClass().equals(Long.class) || long.class.equals(key.getClass())) {
			result = formatCacheKey(KutaUtil.longToBase64(key.longValue()));
		}
		result = formatCacheKey(KutaUtil.intToBase64(key.intValue()));
		return result;
	}
	
	/**
	 * 使用数据主键查询数据
	 * @param session 数据库连接
	 * @param jedis redis连接
	 * @param field 查询键
	 * @param key 数据主键值
	 * @return 查询结果
	 * @throws Exception 内部异常
	 * */
	public String queryByKey(SqlSession session,JedisClient jedis,String field, TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		String val = jedis.hget(cacheKey, field);
		if(KutaUtil.isEmptyString(val)) {
			T t = get(session, key);
			if(KutaUtil.isValueNull(t)) {
				return null;
			}
			cache(t, jedis, cacheKey);
			return queryByKey(session,jedis,field,key);
		}
		return val;
	}
	/**
	 * 使用数据主键查询数据
	 * @param factory 数据session工厂
	 * @param field 查询键
	 * @param key 数据主键值
	 * @return 查询结果
	 * @throws Exception 内部异常
	 * */
	public String queryByKey(DataSessionFactory factory,String field, TKey key) throws Exception {
		String cacheKey = formatCacheKeyByTKey(key);
		String val = factory.getJedis().hget(cacheKey, field);
		if(KutaUtil.isEmptyString(val)) {
			T t = get(factory.getSqlSession(), key);
			if(KutaUtil.isValueNull(t)) {
				return null;
			}
			cache(t, factory.getJedis(), cacheKey);
			return queryByKey(factory,field,key);
		}
		return val;
	}
	public String queryByKeyArgs(DataSessionFactory factory,String field,TKey key, Object... args) throws Exception {
		String cacheKey = formatCacheKey(args);
		String val = factory.getJedis().hget(cacheKey, field);
		if(KutaUtil.isEmptyString(val)) {
			T t = get(factory.getSqlSession(), key);
			if(KutaUtil.isValueNull(t)) {
				return null;
			}
			cache(t, factory.getJedis(), cacheKey);
			return queryByKeyArgs(factory, field, key, args);
		}
		return val;
	}
	@Deprecated
	public String queryByKeyArgs(SqlSession session, JedisClient jedis,String field,TKey key, Object... args) throws Exception {
		String cacheKey = formatCacheKey(args);
		String val = jedis.hget(cacheKey, field);
		if(KutaUtil.isEmptyString(val)) {
			T t = get(session, key);
			if(KutaUtil.isValueNull(t)) {
				return null;
			}
			cache(t, jedis, cacheKey);
			return queryByKeyArgs(session,jedis,field,key,args);
		}
		return val;
	}
	/**
	 * 使用数据主键查询数据(内部创建数据库和redis连接)
	 * @param field 查询键
	 * @param key 数据主键值
	 * @return 查询结果
	 * @throws Exception 内部异常
	 * */
	@SuppressWarnings("deprecation")
	@Deprecated
	public String queryByKey(String field, TKey key) throws Exception {
		return KutaRedisUtil.exec(jedis->{
			return KutaSQLUtil.exec(session->{
				return queryByKey(session,jedis,field,key);
			});
		});
	}
	
	
	/**
	 * 缓存中的数据增加指定的值(值设置为负数即为减法操作)
	 * @param jedis redis连接
	 * @param t 数据实体
	 * @param args 缓存键格式化参数
	 * @throws Exception 内部异常
	 * */
	public void cacheIncr(JedisClient jedis, T t, Object... args) throws Exception {
		if (KutaUtil.isValueNull(t)) {
			throw new KutaIllegalArgumentException(
					String.format(KutaError.ARGUMENT_NOT_EMPTY.getDesc(), "实体对象"));
		}

		Field[] fields = KutaBeanUtil.getAllFields(t);

		String cacheKey = CACHE_KEY;
		if (!KutaUtil.isValueNull(args)) {
			cacheKey = formatCacheKey(args);
		}
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldObj = field.get(t);
			if (fieldObj == null) {
				continue;
			}
//			if(KutaSQLUtil.isPrimaryKey(field) || KutaSQLUtil.isIncrIgoneColumn(field)) {
//				continue;
//			}
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			Class<?> fieldType = field.getType();
			if(fieldType.equals(Date.class)) {
				String date = KutaTimeUtil.formatWithMill((Date)field.get(t));
				jedis.hset(cacheKey, field.getName(), date);
			}
			else if (fieldType.equals(Integer.class) || fieldType.equals(Long.class)
					|| fieldType.equals(Short.class) || fieldType.equals(Byte.class)
					|| fieldType.equals(int.class) || fieldType.equals(long.class)
					|| fieldType.equals(short.class) || fieldType.equals(byte.class)) {
				logger.debug("累加操作: hincrBy key:{},field:{},value:{}", cacheKey, field.getName(),field.get(t));
				jedis.hincrBy(cacheKey, field.getName(), Long.parseLong(field.get(t).toString()));
			}
			else if (fieldType.equals(Float.class) || fieldType.equals(Double.class)
					|| fieldType.equals(BigDecimal.class)
					|| fieldType.equals(float.class) || fieldType.equals(double.class)) {
				logger.debug("累加操作:hincrByFloat key:{},field:{},value:{}", cacheKey, field.getName(),field.get(t));
				jedis.hincrByFloat(cacheKey, field.getName(), Double.parseDouble(field.get(t).toString()));
			}
		}
	}
	
	private final SimpleDateFormat fomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 缓存中的数据增加指定的值(值设置为负数即为减法操作)
	 * @param jedis redis连接
	 * @param t 数据实体
	 * @param key 数据主键
	 * @throws Exception 内部异常
	 * */
	public void cacheIncr(JedisClient jedis, T t, TKey key) throws Exception {
		if (KutaUtil.isValueNull(t)) {
			throw new KutaIllegalArgumentException(
					String.format(KutaError.ARGUMENT_NOT_EMPTY.getDesc(), "实体对象"));
		}

		Field[] fields = KutaBeanUtil.getAllFields(t);

		String cacheKey = CACHE_KEY;
		if (!KutaUtil.isValueNull(key)) {
			if(fieldClazz.equals(Long.class) || fieldClazz.equals(long.class)) {
				cacheKey = formatCacheKey(KutaUtil.longToBase64(key.longValue()));
			} else {
				cacheKey = formatCacheKey(KutaUtil.intToBase64(key.intValue()));
			}
		}
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldObj = field.get(t);
			if (KutaUtil.isValueNull(fieldObj)) {
				continue;
			}
//			if(KutaSQLUtil.isPrimaryKey(field) || KutaSQLUtil.isIncrIgoneColumn(field)) {
//				continue;
//			}
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			
			Class<?> fieldType = field.getType();
			if(fieldType.equals(Date.class)) {
				jedis.hset(cacheKey, field.getName(), fomatter.format((Date)field.get(t)));
			}
			else if (fieldType.equals(Integer.class) || fieldType.equals(Long.class)
					|| fieldType.equals(Short.class) || fieldType.equals(Byte.class)
					|| fieldType.equals(int.class) || fieldType.equals(long.class)
					|| fieldType.equals(short.class) || fieldType.equals(byte.class)) {
				jedis.hincrBy(cacheKey, field.getName(), Long.parseLong(field.get(t).toString()));
			}
			else if (fieldType.equals(Float.class) || fieldType.equals(Double.class)
					|| fieldType.equals(BigDecimal.class)
					|| fieldType.equals(float.class) || fieldType.equals(double.class)) {
				jedis.hincrByFloat(cacheKey, field.getName(), Double.parseDouble(field.get(t).toString()));
			}
		}
	}
	/**
	 * 缓存中的数据增加指定的值(值设置为负数即为减法操作)
	 * <p>内部建立redis连接</p>
	 * @param t 数据实体
	 * @param key 数据主键
	 * @throws Exception 内部异常
	 * */
	public void cacheIncr(T t, TKey key) throws Exception {
		KutaRedisUtil.exec(jedis->{
			cacheIncr(jedis, t, key);
		});
	}
}
