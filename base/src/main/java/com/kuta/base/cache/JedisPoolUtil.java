package com.kuta.base.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuta.base.exception.KutaError;
import com.kuta.common.config.utils.PropertyUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池工具
 * */
public class JedisPoolUtil {
	
	private static JedisCluster jedis;
	
	/**
	 * redis连接池
	 * */
    private static JedisPool pool = null;
    /**
     * 属性集
     * */
    private static Properties pro;
    /**
     * redis密码
     * */
    private static String pwd = null;
    
    private static final String REDIS_MAX_TOTAL = "redis.maxTotal";
    private static final String REDIS_MAX_IDLE = "redis.maxIdle";
    private static final String REDIS_MIN_IDLE = "redis.minIdle";
    private static final String REDIS_URL = "redis.url";
    private static final String REDIS_PWD = "redis.pwd";
    private static final boolean useCluster;
    private static final Logger logger = LoggerFactory.getLogger(JedisPoolUtil.class);
    private static final String REDIS_PROPERTIES_FILE = "redis.properties";
    static {
        //加载配置文件
        InputStream in = JedisPoolUtil.class.getClassLoader().getResourceAsStream(REDIS_PROPERTIES_FILE);
        pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();	
            System.out.println(KutaError.FILE_LOAD_ERROR.getDesc());
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(Integer.parseInt( pro.get(REDIS_MAX_TOTAL).toString()));
        //最大空闲连接数
        poolConfig.setMaxIdle(Integer.parseInt(pro.get(REDIS_MAX_IDLE).toString()));
        poolConfig.setMaxWaitMillis(2000);
        poolConfig.setBlockWhenExhausted(true);
        //最小空闲连接数
        poolConfig.setMinIdle(Integer.parseInt( pro.get(REDIS_MIN_IDLE).toString()));
        pool = new JedisPool(poolConfig, pro.get(REDIS_URL).toString(),Integer.parseInt( pro.get("redis.port")
                .toString()));
        pwd = pro.get(REDIS_PWD).toString();
        useCluster = Boolean.parseBoolean(pro.get("redis.useCluster").toString());
        if(useCluster) {
        	logger.info("已启用REDIS集群");
        	jedis = new JedisCluster(getClusterHostAndPorts(), 2000, 2000, 5, pwd.toString(), poolConfig);
        }
    }
    
    public static boolean useCluster() {
    	return useCluster;
    }
    
    /**
     * 获取一个redis连接
     * @return redis连接包装
     * */
    public static JedisClient getJedis(){
    	JedisClient client = new JedisClient();
    	if(useCluster) {
    		client.setCluster(jedis);
    		return client;
    	}
    	
        Jedis jedis = pool.getResource();
        jedis.auth(pwd.toString());
        client.setJedis(jedis);
        return client;
    }
    
    
    
    
    public static JedisCluster getCluster() {
    	if(useCluster) {
    		
    		return jedis;
    	}
    	return null;
    }
    
    /**
     * 释放redis连接
     * @param redis连接包装
     * */
    public static void release(Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }
    
    public static String getPassword() {
    	return pwd;
    }
    
    
    private static Set<HostAndPort> getClusterHostAndPorts() {
		String config = PropertyUtil.getProperty("redis", "redis.cluster.members");
		Set<HostAndPort> members = new HashSet<HostAndPort>();
		JSONArray json = JSONObject.parseArray(config);
		json.forEach(member->{
			JSONObject item = (JSONObject) JSONObject.toJSON(member);
			members.add(new HostAndPort(item.getString("ip"), item.getInteger("port")));
		});
		logger.info("集群成员:{}", JSONObject.toJSONString(members,true));
		return members;
    }
}
