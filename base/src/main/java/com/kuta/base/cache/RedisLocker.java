package com.kuta.base.cache;

import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Builder;
import redis.clients.jedis.Response;

/**
 * The Distributed Locker with Redis.
 * */
@Builder
public class RedisLocker {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Builder.Default
    private long timeout=30000;
    @Builder.Default
    private boolean enableTimeout = false;
    
    /**
     * get distrbuted lock by redis
     * @param key cache key
     * @return
     */
    public boolean lock(JedisClient jedis,String key,String value, int expire){
    	Long start = System.currentTimeMillis();
        for(;;)	{
            //SET命令返回OK ，则证明获取锁成功
            Response<String> lock = jedis.getDistributedLocked(key, value, expire);
            
            if("OK".equals(lock.get())){
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            if(!enableTimeout) {
            	return false;
            }
            long l = System.currentTimeMillis() - start;
            if (l>=timeout) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * unlock the Distributed Lock from redis
     * @param id
     * @return
     */
    public boolean unlock(JedisClient jedis,String key, String value){
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";
        Object result = jedis.eval(script, key, 
                                Collections.singletonList(value));
        if("1".equals(result.toString())){
            return true;
        }
        return false;
    }
}
