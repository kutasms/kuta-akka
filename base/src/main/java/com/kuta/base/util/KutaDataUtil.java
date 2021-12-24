package com.kuta.base.util;

import java.util.function.BiFunction;

import org.apache.ibatis.session.SqlSession;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.database.KutaSQLUtil;

/**
 * Data connection generation and connection consumption construction tool
 * */
public class KutaDataUtil {
	
	
	/**
	 * Perform connection generation and consumption
	 * @param consumer Consumer object
	 * @throws Exception Thrown when an exception occurs
	 */
	public static void execute(KutaBiConsumer<SqlSession,JedisClient,Exception> consumer) throws Exception {
		KutaSQLUtil.exec(session->{
			KutaRedisUtil.exec(jedis->{
				consumer.accept(session, jedis);
			});
		});	
	}
	/**
	 * Perform connection generation and consumption
	 * @param func Execute the object and return data after execution
	 * @return The object returned by the func actuator
	 * @throws Exception Thrown when an exception occurs
	 */
	public static <T> T func(BiFunction<SqlSession,JedisClient, T> func) throws Exception {
		return KutaSQLUtil.func(session->{
			return KutaRedisUtil.func(jedis->{
				return func.apply(session, jedis);
			});
		});

	}
}
