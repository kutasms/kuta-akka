package com.kuta.data.mysql.biz;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.database.DataSessionFactory;
import com.kuta.base.database.KutaConfigAbstractBiz;
import com.kuta.base.database.KutaSQLUtil;
import com.kuta.base.util.KutaRedisUtil;
import com.kuta.base.util.KutaUtil;
import com.kuta.data.mysql.dao.SystemConfigMapper;
import com.kuta.data.mysql.pojo.SystemConfig;
import com.kuta.data.mysql.pojo.SystemConfigExample;

public class SystemConfigBiz extends KutaConfigAbstractBiz<SystemConfig> {	

	private final int expire = 30 * 60;
	
	public SystemConfigBiz() {
		super("system-config");
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected String getJson(SqlSession session) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("不支持JSON格式");
	}

	@Override
	protected Map<String, String> getMap(SqlSession session) {
		// TODO Auto-generated method stub
		List<SystemConfig> list = session.getMapper(SystemConfigMapper.class).selectByExample(null);
		if (KutaUtil.isEmptyColl(list)) {
			return null;
		} else {
			Map<String, String> map = new HashMap<String, String>();
			list.forEach(x -> {
				map.put(x.getKey(), x.getValue());
			});
			return map;
		}
	}

	public Map<String, String> get() throws Exception {
		return KutaRedisUtil.exec(jedis -> {
			if (jedis.exists(CACHE_KEY)) {
				return jedis.hgetAll(CACHE_KEY);
			} else {
				try {
					cacheAllToHash(jedis, expire);
					return get();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		});
	}

	public SystemConfig get(String key, SqlSession session) {
		SystemConfigMapper mapper = session.getMapper(SystemConfigMapper.class);
		SystemConfigExample example = new SystemConfigExample();
		example.createCriteria().andKeyEqualTo(key);
		List<SystemConfig> list = mapper.selectByExample(example);
		if (KutaUtil.isEmptyColl(list)) {
			return null;
		}
		return list.get(0);
	}

	public String query(String key, JedisClient jedis) {
		String result = jedis.hget(CACHE_KEY, key);
		if (KutaUtil.isEmptyString(result)) {
			try {
				cacheAllToHash(jedis, expire);
				return query(key, jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	@Deprecated
	public String query(String key, SqlSession session, JedisClient jedis) {
		String result = jedis.hget(CACHE_KEY, key);
		if (KutaUtil.isEmptyString(result)) {
			try {
				if(fullValid(session, jedis, key)) {
					return query(key, session, jedis); 
				}
				else {
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	public String query(String key, DataSessionFactory f) {
		String result = f.getJedis().hget(CACHE_KEY, key);
		if (KutaUtil.isEmptyString(result)) {
			try {
				if(fullValid(f, key)) {
					return query(key, f); 
				}
				else {
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	@Deprecated
	public BigInteger queryBigInteger(String key, SqlSession session, JedisClient jedis) {
		String result = query(key, session, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return new BigInteger(result);
	}
	
	public BigInteger queryBigInteger(String key, DataSessionFactory f) {
		String result = query(key, f);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return new BigInteger(result);
	}

	public BigInteger queryBigInteger(String key, JedisClient jedis) {
		String result = query(key, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return new BigInteger(result);
	}
	@Deprecated
	public Integer queryInt(String key, SqlSession session, JedisClient jedis) {
		String result = query(key, session, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Integer.parseInt(result);
	}
	
	public Integer queryInt(String key, DataSessionFactory f) {
		String result = query(key, f);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Integer.parseInt(result);
	}

	public Integer queryInt(String key, JedisClient jedis) {
		String result = query(key, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Integer.parseInt(result);
	}

	public Long queryLong(String key, SqlSession session, JedisClient jedis) {
		String result = query(key, session, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Long.parseLong(result);
	}
	public Long queryLong(String key, DataSessionFactory f) {
		String result = query(key, f);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Long.parseLong(result);
	}

	public Long queryLong(String key, JedisClient jedis) {
		String result = query(key, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Long.parseLong(result);
	}
	public Double queryDouble(String key, SqlSession session, JedisClient jedis) {
		String result = query(key, session, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}

		return Double.parseDouble(result);
	}
	public Double queryDouble(String key, DataSessionFactory f) {
		String result = query(key, f);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}

		return Double.parseDouble(result);
	}
	public Double queryDouble(String key, JedisClient jedis) {
		String result = query(key, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}

		return Double.parseDouble(result);
	}

	public Boolean queryBoolean(String key, SqlSession session, JedisClient jedis) {
		String result = query(key, session, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Boolean.parseBoolean(result);
	}
	public Boolean queryBoolean(String key, DataSessionFactory f) {
		String result = query(key, f);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Boolean.parseBoolean(result);
	}
	public Boolean queryBoolean(String key, JedisClient jedis) {
		String result = query(key, jedis);
		if (KutaUtil.isEmptyString(result)) {
			throw new IllegalArgumentException("未查询到相关的配置");
		}
		return Boolean.parseBoolean(result);
	}

	public Map<String, String> get(JedisClient jedis) throws Exception {
		if (jedis.exists(CACHE_KEY)) {
			return jedis.hgetAll(CACHE_KEY);
		} else {
			try {
				cacheAllToHash(jedis, expire);
				return get(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}


	public Map<String, String> get(SqlSession session, JedisClient jedis) throws Exception {
		if (jedis.exists(CACHE_KEY)) {
			return jedis.hgetAll(CACHE_KEY);
		} else {
			try {
				cacheAllToHash(session, jedis, expire);
				return get(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	public int update(List<SystemConfig> configs,SqlSession session, JedisClient jedis) {
		int batchUpdate = session.getMapper(SystemConfigMapper.class).batchUpdate(configs);
		configs.forEach(config->{
			jedis.hset(this.CACHE_KEY, config.getKey(), config.getValue());
		});
		return batchUpdate;
	}
	
	public int update(List<SystemConfig> configs) {
		return KutaSQLUtil.func(session -> {
			return KutaRedisUtil.func(jedis -> {
				return update(configs,session, jedis);
			});
		});
	}
	
	public int update(SystemConfig config) throws Exception {
		return KutaSQLUtil.func(session -> {
			return KutaRedisUtil.func(jedis -> {
				int result = session.getMapper(SystemConfigMapper.class).updateByPrimaryKey(config);
				jedis.hset(this.CACHE_KEY, config.getKey(), config.getValue());
				return result;
			});
		});
	}

	public boolean checkType(String value,String type) {
		switch (type) {
		case "json":
			try {
				JSONObject.parse(value);
				return true;
			}
			catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		case "int":
			try {
				Integer.parseInt(value);
				return true;
			}
			catch (Exception e) {
				return false;
				// TODO: handle exception
			}
		case "long":
			try {
				Long.parseLong(value);
				return true;
			}
			catch (Exception e) {
				return false;
				// TODO: handle exception
			}
		case "double":
			try {
				Double.parseDouble(value);
				return true;
			} catch (Exception e) {
				return false;
				// TODO: handle exception
			}
		case "boolean":
			try {
				Boolean.parseBoolean(value);
				return true;
			} catch (Exception e) {
				return false;
				// TODO: handle exception
			}
		case "biginteger":
			try {
				new BigInteger(value);
				return true;
			} catch (Exception e) {
				return false;
				// TODO: handle exception
			}
		default:
			return true;
		}
	}
	
	public int update(SqlSession session, SystemConfig config) throws Exception {
		return KutaRedisUtil.exec(jedis -> {
			SystemConfigExample example = new SystemConfigExample();
			example.createCriteria().andKeyEqualTo(config.getKey());
			int result = session.getMapper(SystemConfigMapper.class).updateByExampleSelective(config, example);
			if (result > 0) {
				jedis.hset(this.CACHE_KEY, config.getKey(), config.getValue());
			}
			return result;
		});
	}

	@Override
	protected int update(SqlSession session, Map<String, String> map) {
		// TODO Auto-generated method stub

		map.forEach((k, v) -> {
			SystemConfig config = new SystemConfig();
			config.setKey(k);
			config.setValue(v);
			session.getMapper(SystemConfigMapper.class).updateByPrimaryKeySelective(config);
		});
		return map.size();
	}

}
