package com.kuta.base.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.util.JedisClusterCRC16;

public class JedisClient {
	
	private Jedis jedis;
	private JedisCluster cluster;
	private boolean txOpened = false;
	private Transaction transaction = null;
	
	public JedisClient() {
		
	}
	
	public void multi() {
		txOpened = true;
		if(JedisPoolUtil.useCluster()) {
			JedisClusterTransactionManager.multi(cluster);
		}
		else {
			transaction = jedis.multi();
		}
	}
	
	public void discard() {
		if(JedisPoolUtil.useCluster()) {
			JedisClusterTransactionManager.discard();
		}
		else {
			transaction.discard();
		}
	}
	
	public void exec() {
		if(JedisPoolUtil.useCluster()) {
			JedisClusterTransactionManager.exec();
		}
		else {
			transaction.exec();
		}
	}
	
	
	public JedisCluster getCluster() {
		return cluster;
	}
	public void setCluster(JedisCluster cluster) {
		this.cluster = cluster;
	}
	public Jedis getJedis() {
		return jedis;
	}
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
	public Set<String> keys(String pattern) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.keys(pattern);
		} else {
			return jedis.keys(pattern);
		}
	}
	
	
	public ScanResult<String> scan(final String cursor, final ScanParams params) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.scan(cursor, params);
		}
		return jedis.scan(cursor, params);
	}
	
	public String set(String key, String value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				JedisClusterTransactionManager.set(key, value);
				return null;
			}
			return cluster.set(key, value);
		}
		if(txOpened) {
			transaction.set(key, value);
			return null;
		}
		return jedis.set(key,value);
	}
	
	public Long incr(String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Response<Long> rsp = JedisClusterTransactionManager.incr(key);
				return rsp.get();
			}
			return cluster.incr(key);
		}
		if(txOpened) {
			Response<Long> rsp = transaction.incr(key);
			return rsp.get();
		}
		return jedis.incr(key);
	}
	
	public String set(byte[] key, byte[] value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.set(key,value);
				return null;
			}
			return cluster.set(key, value);
		}
		if(txOpened) {
			transaction.set(key,value);
			return null;
		}
		return jedis.set(key,value);
	}
	
	public String setex(final String key, final int seconds, final String value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.setex(key,seconds,value);
				return null;
			}
			return cluster.setex(key, seconds, value);
		}
		if(txOpened) {
			transaction.setex(key, seconds, value);
			return null;
		}
		return jedis.setex(key, seconds, value);
	}
	public String setex(final byte[] key, final int seconds, final byte[] value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.setex(key,seconds,value);
				return null;
			}
			return cluster.setex(key, seconds, value);
		}
		if(txOpened) {
			transaction.setex(key, seconds, value);
			return null;
		}
		return jedis.setex(key, seconds, value);
	}
	public Long incrBy(String key, long increment) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.incrBy(key, increment);
				return null;
			}
			return cluster.incrBy(key, increment);
		}
		if(txOpened) {
			transaction.incrBy(key, increment);
			return null;
		}
		return jedis.incrBy(key, increment);
	}
	public Long decr(String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.decr(key);
				return null;
			}
			return cluster.decr(key);
		}
		if(txOpened) {
			transaction.decr(key);
			return null;
		}
		return jedis.decr(key);
	}
	public Double hincrByFloat(final byte[] key, final byte[] field, final double value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hincrByFloat(key, field, value);
				return null;
			}
			return cluster.hincrByFloat(key, field, value);
		}
		if(txOpened) {
			transaction.hincrByFloat(key, field, value);
			return null;
		}
		return jedis.hincrByFloat(key, field, value);
	}
	public Double hincrByFloat(final String key, final String field, final double value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hincrByFloat(key.getBytes(), field.getBytes(), value);
				return null;
			}
			return cluster.hincrByFloat(key.getBytes(), field.getBytes(), value);
		}
		if(txOpened) {
			transaction.hincrByFloat(key, field, value);
			return null;
		}
		return jedis.hincrByFloat(key, field, value);
	}
	private static final Logger logger = LoggerFactory.getLogger(JedisClient.class);
	
	public Long hincrBy(final String key, final String field, final long value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
//				logger.info("hincrBy,key:{},field:{},value:{}", key, field,value);
				tran.hincrBy(key, field, value);
				logger.debug("hincrBy");
				return null;
			}
//			logger.info("hincrBy,key:{},field:{},value:{}", key, field,value);
			return cluster.hincrBy(key, field, value);
		}
		if(txOpened) {
			transaction.hincrBy(key, field, value);
			return null;
		}
		return jedis.hincrBy(key, field, value);
	}
	public Long hincrBy(final byte[] key, final byte[] field, final long value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hincrBy(key, field, value);
				return null;
			}
			return cluster.hincrBy(key, field, value);
		}
		if(txOpened) {
			transaction.hincrBy(key, field, value);
			return null;
		}
		return jedis.hincrBy(key, field, value);
	}
	public Long pexpire(final byte[] key, final long milliseconds) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.pexpire(key, milliseconds);
				return null;
			}
			return cluster.pexpire(key, milliseconds);
		}
		if(txOpened) {
			transaction.pexpire(key, milliseconds);
			return null;
		}
		return jedis.pexpire(key, milliseconds);
	}
	public Long pexpire(final String key, final long milliseconds) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.pexpire(key, milliseconds);
				return null;
			}
			return cluster.pexpire(key, milliseconds);
		}
		if(txOpened) {
			transaction.pexpire(key, milliseconds);
			return null;
		}
		return jedis.pexpire(key, milliseconds);
	}
	
	public Long decrBy(String key, long n) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.decrBy(key,n);
				return null;
			}
			return cluster.decrBy(key,n);
		}
		if(txOpened) {
			transaction.decrBy(key,n);
			return null;
		}
		return jedis.decrBy(key,n);
	}
	public Long rpush(String key, String... value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.rpush(key,value);
				return null;
			}
			return cluster.rpush(key,value);
		}
		if(txOpened) {
			transaction.rpush(key,value);
			return null;
		}
		return jedis.rpush(key,value);
	}
	public Long expire(String key, int seconds) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.expire(key, seconds);
				return null;
			}
			return cluster.expire(key, seconds);
		}
		if(txOpened) {
			transaction.expire(key, seconds);
			return null;
		}
		return jedis.expire(key, seconds);
	}
	
	public Long rpush(final byte[] key, final byte[]... args) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.rpush(key, args);
				return null;
			}
			return cluster.rpush(key, args);
		}
		if(txOpened) {
			transaction.rpush(key, args);
			return null;
		}
		return jedis.rpush(key, args);
	}
	
	public Long sadd(final byte[] key, final byte[]... member) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.sadd(key, member);
				return null;
			}
			return cluster.sadd(key, member);
		}
		if(txOpened) {
			transaction.sadd(key, member);
			return null;
		}
		return jedis.sadd(key, member);
	}
	public Long sadd(final String key, final String... member) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.sadd(key, member);
				return null;
			}
			return cluster.sadd(key, member);
		}
		if(txOpened) {
			transaction.sadd(key, member);
			return null;
		}
		return jedis.sadd(key, member);
	}
	public Long hset(final String key, final Map<String, String> hash) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hset(key, hash);
				return null;
			}
			return cluster.hset(key, hash);
		}
		if(txOpened) {
			transaction.hset(key, hash);
			return null;
		}
		
		return jedis.hset(key, hash);
	}
	
//	public Long expire(String key, int seconds) {
//		if(JedisPoolUtil.useCluster()) {
//			if(txOpened) {
//				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
//				tran.expire(key, seconds);
//				return null;
//			}
//			return cluster.expire(key, seconds);
//		}
//		if(txOpened) {
//			transaction.expire(key, seconds);
//			return null;
//		}
//		return jedis.expire(key, seconds);
//	}
	
	public Long hset(final byte[] key, final Map<byte[], byte[]> hash) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hset(key, hash);
				return null;
			}
			return cluster.hset(key, hash);
		}
		if(txOpened) {
			transaction.hset(key, hash);
			return null;
		}
		return jedis.hset(key, hash);
	}
	public Long hset(final byte[] key, final byte[] field, final byte[] value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hset(key, field, value);
				return null;
			}
			return cluster.hset(key, field, value);
		}
		if(txOpened) {
			transaction.hset(key, field, value);
			return null;
		}
		return jedis.hset(key, field, value);
	}
	public Long hset(final String key, final String field, final String value) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hset(key, field, value);
				return null;
			}
			return cluster.hset(key, field, value);
		}
		if(txOpened) {
			transaction.hset(key, field, value);
			return null;
		}
		return jedis.hset(key, field, value);
	}
	public List<String> hmget(final String key, final String... fields) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hmget(key, fields);
				return null;
			}
			return cluster.hmget(key, fields);
		}
		if(txOpened) {
			transaction.hmget(key, fields);
			return null;
		}
		return jedis.hmget(key, fields);
	}
	public List<byte[]> hmget(final byte[] key, final byte[] fields) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hmget(key, fields);
				return null;
			}
			return cluster.hmget(key, fields);
		}
		if(txOpened) {
			transaction.hmget(key, fields);
			return null;
		}
		return jedis.hmget(key, fields);
	}
	
	public byte[] hget(final byte[] key, final byte[] field) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hget(key, field);
				return null;
			}
			return cluster.hget(key, field);
		}
		if(txOpened) {
			transaction.hget(key, field);
			return null;
		}
		return jedis.hget(key, field);
	}
	public String hget(final String key, final String field) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hget(key, field);
				return null;
			}
			return cluster.hget(key, field);
		}
		if(txOpened) {
			transaction.hget(key, field);
			return null;
		}
		return jedis.hget(key, field);
	}
	public Long hdel(final String key, final String... field) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hdel(key, field);
				return null;
			}
			return cluster.hdel(key, field);
		}
		if(txOpened) {
			transaction.hdel(key, field);
			return null;
		}
		return jedis.hdel(key, field);
	}
	public Long hdel(final byte[] key, final byte[] field) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hdel(key, field);
				return null;
			}
			return cluster.hdel(key, field);
		}
		if(txOpened) {
			transaction.hdel(key, field);
			return null;
		}
		return jedis.hdel(key, field);
	}
	public Boolean hexists(final byte[] key, final byte[] field) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hexists(key, field);
		}
		return jedis.hexists(key, field);
	}
	public Boolean hexists(final String key, final String field) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hexists(key, field);
		}
		return jedis.hexists(key, field);
	}
	public Collection<byte[]> hvals(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hvals(key);
				return null;
			}
			return cluster.hvals(key);
		}
		if(txOpened) {
			transaction.hvals(key);
			return null;
		}
		return jedis.hvals(key);
	}
	public List<String> hvals(final String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hvals(key);
				return null;
			}
			return cluster.hvals(key);
		}
		if(txOpened) {
			transaction.hvals(key);
			return null;
		}
		return jedis.hvals(key);
	}
	
	public Set<String> hkeys(final String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hkeys(key);
				return null;
			}
			return cluster.hkeys(key);
		}
		if(txOpened) {
			transaction.hkeys(key);
			return null;
		}
		return jedis.hkeys(key);
	}
	public Set<byte[]> hkeys(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hkeys(key);
				return null;
			}
			return cluster.hkeys(key);
		}
		if(txOpened) {
			transaction.hkeys(key);
			return null;
		}
		return jedis.hkeys(key);
	}
	public String hmset(final byte[] key, final Map<byte[], byte[]> hash) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hmset(key, hash);
				return null;
			}
			return cluster.hmset(key, hash);
		}
		if(txOpened) {
			transaction.hmset(key, hash);
			return null;
		}
		return jedis.hmset(key, hash);
	}
	public String hmset(final String key, final Map<String, String> hash) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.hmset(key, hash);
				return null;
			}
			return cluster.hmset(key, hash);
		}
		if(txOpened) {
			transaction.hmset(key, hash);
			return null;
		}
		return jedis.hmset(key, hash);
	}
	public Map<String, String> hgetAll(final String key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hgetAll(key);
		}
		
		return jedis.hgetAll(key);
	}
	public Map<byte[], byte[]> hgetAll(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hgetAll(key);
		}
		return jedis.hgetAll(key);
	}
	public Long hlen(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hlen(key);
		}
		return jedis.hlen(key);
	}
	public Long hlen(final String key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.hlen(key);
		}
		return jedis.hlen(key);
	}
	public String get(final String key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.get(key);
		}
		return jedis.get(key);
	}
	public byte[] get(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.get(key);
		}
		return jedis.get(key);
	}
	public List<byte[]> lrange(final byte[] key, final long start, final long stop) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.lrange(key, start, stop);
		}
		return jedis.lrange(key, start, stop);
	}
	public List<String> lrange(final String key, final long start, final long stop) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.lrange(key, start, stop);
		}
		return jedis.lrange(key, start, stop);
	}
	public Set<byte[]> smembers(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.smembers(key);
				return null;
			}
			return cluster.smembers(key);
		}
		if(txOpened) {
			transaction.smembers(key);
			return null;
		}
		return jedis.smembers(key);
	}
	public Set<String> smembers(final String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.smembers(key);
				return null;
			}
			return cluster.smembers(key);
		}
		if(txOpened) {
			transaction.smembers(key);
			return null;
		}
		return jedis.smembers(key);
	}
	public Long llen(final String key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.llen(key);
		}
		return jedis.llen(key);
	}
	public Long llen(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.llen(key);
		}
		return jedis.llen(key);
	}
	public Boolean exists(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.exists(key);
		}
		return jedis.exists(key);
	}
	public Boolean exists(final String key) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.exists(key);
		}
		return jedis.exists(key);
	}
	public Long exists(final String... keys) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.exists(keys);
		}
		return jedis.exists(keys);
	}
	public Long exists(final byte[]... keys) {
		if(JedisPoolUtil.useCluster()) {
			return cluster.exists(keys);
		}
		return jedis.exists(keys);
	}
	public Long scard(final String key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.scard(key);
				return null;
			}
			return cluster.scard(key);
		}
		if(txOpened) {
			transaction.scard(key);
			return null;
		}
		return jedis.scard(key);
	}
	public Long scard(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.scard(key);
				return null;
			}
			return cluster.scard(key);
		}
		if(txOpened) {
			transaction.scard(key);
			return null;
		}
		return jedis.scard(key);
	}
	public Long del(final byte[] key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(key));
				tran.del(key);
				return null;
			}
			return cluster.del(key);
		}
		if(txOpened) {
			transaction.del(key);
			return null;
		}
		return jedis.del(key);
	}
	public Long del(final String... key) {
		if(JedisPoolUtil.useCluster()) {
			if(txOpened) {
				for(String k : key) {
					Transaction tran = JedisClusterTransactionManager.getTxByKey(new String(k));
					tran.del(k);
				}
				return null;
			}
			long count = 0;
			for(String k : key) {
				Jedis jedis = null;
				try {
					int slot = JedisClusterCRC16.getSlot(k);
					jedis = cluster.getConnectionFromSlot(slot);
					count += jedis.del(k);
				}
				finally {
					if(jedis != null) {
						jedis.close();
					}
				}
			}
			return count;
		}
		if(txOpened) {
			transaction.del(key);
			return null;
		}
		return jedis.del(key);
	}
}
