package com.kuta.base.util;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Transaction;

public class KutaRedisUtil {
	
	/**
	 * <pre>
	 * 获取一个redis连接，并且包装此连接以实现自动释放
	 * 您可以传入一个函数式消费器，在消费器里完成您的缓存应用。
	 * case:
	 * KSFRedisUtil.exec(jedis->{
	 *     jedis.set("key","val");
	 * });
	 * </pre>
	 * @param consumer 可抛出异常的函数式消费器
	 * @throws Exception 当消费器内部发生异常时将抛出
	 * */
	public static void exec(ThrowingConsumer<JedisClient,Exception> consumer) throws Exception {
		JedisClient jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			consumer.accept(jedis);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
	
	
	
	/**
	 * <pre>
	 * 获取一个redis连接，并且包装此连接以实现自动释放
	 * 您可以传入一个函数式执行器，在执行器里完成您的缓存应用。
	 * case:
	 * int result = KSFRedisUtil.exec(jedis->{
	 *     jedis.set("key","val");
	 *     return 0;
	 * });
	 * </pre>
	 * @param <T> 函数式执行器返回结果的泛型
	 * @param func 可抛出异常的函数式执行器
	 * @return 执行器中返回的结果
	 * @throws Exception 当消费器内部发生异常时将抛出
	 * */
	public static <T> T exec(ThrowingFunction<JedisClient, T, Exception> func) throws Exception {
		JedisClient jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return func.apply(jedis);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
	
	public static <T> T func(Function<JedisClient, T> func) {
		JedisClient jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return func.apply(jedis);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
	
	/**
	 * <pre>
	 * 执行一个redis事务
	 * 您可以传入一个函数式消费器，在消费器里完成您的缓存应用。
	 * case:
	 * KSFRedisUtil.trans(t->{
	 *     t.set("key","val");
	 * });
	 * </pre>
	 * @param consumer 可抛出异常的函数式消费器
	 * @throws Exception 当消费器内部发生异常时将抛出
	 * */
	public static void trans(ThrowingConsumer<JedisClient,Exception> consumer)  throws Exception{
		JedisClient jedis = JedisPoolUtil.getJedis();
		jedis.multi();
		try {
			consumer.accept(jedis);
			jedis.exec();
		}
		catch (Exception e) {
			// TODO: handle exception
			jedis.discard();
			throw e;
			
			
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
	/**
	 * <pre>
	   * 执行一个redis事务
	 * case:
	 * int result = KSFRedisUtil.trans(t->{
	 *     t.set("key","val");
	 *     return 0;
	 * });
	 * </pre>
	 * @param <T> 函数式执行器返回结果的泛型
	 * @param func 可抛出异常的函数式执行器
	 * @return 执行器中返回的结果
	 * @throws Exception 当消费器内部发生异常时将抛出
	 * */
	public static <T> T trans(ThrowingFunction<JedisClient,T,Exception> func)  throws Exception{
		JedisClient jedis = JedisPoolUtil.getJedis();
		jedis.multi();
		try {
			T result = func.apply(jedis);
			jedis.exec();
			return result;
		}
		catch (Exception e) {
			// TODO: handle exception
			jedis.discard();
			throw e;
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
}
