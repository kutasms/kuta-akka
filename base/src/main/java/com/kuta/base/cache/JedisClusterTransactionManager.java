package com.kuta.base.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kuta.base.util.KutaUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.util.JedisClusterCRC16;

public class JedisClusterTransactionManager {
	
	private static ThreadLocal<Object> txThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<JedisCluster> clusterThreadLocal= new ThreadLocal<>();
    
    //开启事务
    public static void multi(JedisCluster jedisCluster){
        clusterThreadLocal.set(jedisCluster);
    }

    /**
     * 	保存string数据类型
     * @param key
     * @param value
     */
    public static Response<String> set(String key,String value) {
        Transaction tx = getTxByKey(key);
        return tx.set(key, value);
    }
    
    public static Object eval(String script, String key, List<String> args) {
    	Transaction tx = getTxByKey(key);
    	return tx.eval(script, Collections.singletonList(key), args);
    }
    
    public static Response<Long> incr(String key) {
    	Transaction tx = getTxByKey(key);
    	return tx.incr(key);
    }
    /**
     * 	批量保存string数据类型
     * @param key
     * @param value
     */
    public static void mset(String... keysvalues) {
        if(keysvalues!=null && keysvalues.length>0) {
            for(int i=0;i<keysvalues.length;i+=2) {
                String key = keysvalues[i];
                String value = keysvalues[i+1];
                Transaction tx = getTxByKey(key);
                tx.set(key, value);
            }
        }
    }

    /**
     * 保存hash数据类型
     * @param key
     * @param value
     */
    public static void hset(String key, String field,String value) {
        Transaction tx = getTxByKey(key);
        tx.hset(key, field, value);
    }

    /**
     * 批量保存hash数据类型
     * @param key
     * @param value
     */
    public static void hmset(String key,Map<String,String> hash) {
        Transaction tx = getTxByKey(key);
        tx.hmset(key, hash);
    }

    /**
     * 保存list数据类型
     * @param key
     * @param value
     */
    public static void lpush(String key,String... values) {
        Transaction tx = getTxByKey(key);
        tx.lpush(key, values);
    }

    /**
     * 保存set数据类型
     * @param key
     * @param value
     */
    public static void sadd(String key,String... member) {
        Transaction tx = getTxByKey(key);
        tx.sadd(key, member);
    }

    /**
     * 保存sorted set数据类型
     * @param key
     * @param value
     */
    public static void zadd(String key,Map<String,Double> scoreMembers) {
        Transaction tx = getTxByKey(key);
        tx.zadd(key, scoreMembers);
    }

    /**
     * 通用删除
     * @param key
     * @param value
     */
    public static void del(String... keys) {
        if(keys!=null && keys.length>0) {
            for(String key:keys) {
                Transaction tx = getTxByKey(key);
                tx.del(key);
            }
        }
    }


    /**
     * 删除hash
     * @param key
     * @param value
     */
    public static void hdel(String key,String... field) {
        Transaction tx = getTxByKey(key);
        tx.hdel(key, field);
    }

    /**
     * 删除set
     * @param key
     * @param value
     */
    public static void srem(String key,String... member) {
        Transaction tx = getTxByKey(key);
        tx.srem(key, member);
    }

    /**
     * 删除sorted set
     * @param key
     * @param value
     */
    public static void zrem(String key,String... member) {
        Transaction tx = getTxByKey(key);
        tx.zrem(key, member);
    }

    private static final Logger logger = LoggerFactory.getLogger(JedisClusterTransactionManager.class);
    /**
     * 提交
     */
    public static void exec() {
        @SuppressWarnings("unchecked")
		Map<String,TransactionWrapper> map = (Map<String,TransactionWrapper> )txThreadLocal.get();
        if(KutaUtil.isEmptyMap(map)) {
        	logger.debug("exec-map empty.");
        	return;
        }
        for(Entry<String,TransactionWrapper> entry:map.entrySet()) {
//        	logger.info("exec:{}",entry.getKey());
            entry.getValue().getTransaction().exec();
            entry.getValue().getJedis().close();
        }
    }

    /**
     * 回滚
     */
    public static void discard() {
        @SuppressWarnings("unchecked")
		Map<String,TransactionWrapper> map = (Map<String,TransactionWrapper> )txThreadLocal.get();
        
        if(KutaUtil.isEmptyMap(map)) {
//        	logger.info("exec-map empty.");
        	return;
        }
        for(Entry<String,TransactionWrapper> entry:map.entrySet()) {
            entry.getValue().getTransaction().discard();
            entry.getValue().getJedis().close();
        }
    }

    /**
     * 根据key，得到事务对象
     * @param cluster
     * @param key
     * @return
     */
    public static Transaction getTxByKey(String key) {
        
    	JedisCluster cluster = clusterThreadLocal.get();
        @SuppressWarnings("unchecked")
		Map<String, TransactionWrapper> res = (Map<String, TransactionWrapper>)txThreadLocal.get();
//        logger.info("获取事务对象,key:{},res:{}",key,res == null);
        if(res == null) {
            res = new HashMap<>();
        }
//        Map<String, JedisPool> map = cluster.getClusterNodes();
//        logger.info("获取的map:{}",JSONObject.toJSONString(map.keySet(),true));
//        
        int slot = JedisClusterCRC16.getSlot(key);
        
        Jedis jedis = cluster.getConnectionFromSlot(slot);
        Transaction tx = jedis.multi();
        TransactionWrapper wrapper = new TransactionWrapper();
        wrapper.setJedis(jedis);
        wrapper.setTransaction(tx);
        res.put(String.format("%s_%s",key,UUID.randomUUID().hashCode()), wrapper);
        txThreadLocal.set(res);
        return tx;
    }
}
