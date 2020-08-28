package com.kuta.base.cache;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.kuta.base.collection.KSFHashSet;
import com.kuta.base.entity.KSFScanResult;
import com.kuta.base.serialization.ProtostuffUtil;
import com.kuta.base.util.KSFUtil;

import akka.actor.Scheduler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisDataException;
import scala.concurrent.ExecutionContext;

public class JedisUtil {
	private static final int DEFAULT_SETEX_TIMEOUT = 60 * 60;// setex的默认时间

	/**
	 * 通过管线模糊查询指定key,并加载所有数据
	 * @param key 模糊查询的key
	 * @return 包含所有数据的结果
	 * @deprecated
	 * */
	public static Map<String, Map<String, String>> getAllValsByPipeline(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Set<String> keys = jedis.keys(key);
			Pipeline pipe = jedis.pipelined();
			for (String k : keys) {
				Response<Map<String, String>> response = pipe.hgetAll(k);
				map.put(k, response.get());
			}
			pipe.sync();
			return map;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 根据键集合通过管线加载所有数据
	 * @param keys 键集合
	 * @return 包含所有数据的结果
	 * */
	public static Map<String, Map<String, String>> getAllValsByPipeline(Set<String> keys) {
		if (KSFUtil.isValueNull(keys)) {
			return null;
		}
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Pipeline pipe = jedis.pipelined();
			Map<String, Response<Map<String, String>>> result = new HashMap<>();
			for (String k : keys) {
				result.put(k, pipe.hgetAll(k));
				System.out.println(String.format("已加载%s数据,当前写入%s", result.size(),k));
			}
			pipe.sync();
			for(Map.Entry<String, Response<Map<String, String>>> entry : result.entrySet()) {
				map.put(entry.getKey(), entry.getValue().get());
			}
			return map;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}
	/**
	 * 根据键集合通过管线加载所有数据
	 * <p>使用外部创建的jedis连接</p>
	 * @param jedis redis连接
	 * @param keys 键集合
	 * @return 包含所有数据的结果
	 * */
	public static Map<String, Map<String, String>> getAllValsByPipeline(Jedis jedis, Set<String> keys) {
		if (KSFUtil.isValueNull(keys, jedis)) {
			return null;
		}
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, Response<Map<String, String>>> result = new HashMap<>();
		Pipeline pipe = jedis.pipelined();
		for (String k : keys) {
			result.put(k, pipe.hgetAll(k));
		}
		pipe.sync();
		for(Map.Entry<String, Response<Map<String, String>>> entry : result.entrySet()) {
			map.put(entry.getKey(), entry.getValue().get());
		}
		
		return map;
	}
	
	/**
	 * 根据键集合通过管线加载所有数据
	 * <p>使用外部创建的jedis连接管线</p>
	 * @param pipeline redis连接管线
	 * @param keys 键集合
	 * @return 包含所有数据的结果
	 * */
	public static Map<String, Map<String, String>> getAllValsByPipeline(Pipeline pipeline, Set<String> keys) {
		if (KSFUtil.isValueNull(keys, pipeline)) {
			return null;
		}
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, Response<Map<String, String>>> result = new HashMap<>();
		
		for (String k : keys) {
			result.put(k, pipeline.hgetAll(k));
//			System.out.println(String.format("已加载%s数据,当前写入%s", result.size(),k));
		}
		pipeline.sync();
		for(Map.Entry<String, Response<Map<String, String>>> entry : result.entrySet()) {
			map.put(entry.getKey(), entry.getValue().get());
		}
		
		return map;
	}

	/**
	 * 模糊扫描redis键 
	 * @param jedis redis的java对象 
	 * @param pattern 模糊查找的key 
	 * @param scanCount 每次模糊查找的数量，推荐不大于10万
	 * @param sleep 休眠
	 * @param consumerSize 当查找到的数据量大于consumerSize值时调用consumer表达式 
	 * @param consumer 处理函数，可以是lamda表达式，如 x->{}
	 */
	public static void scanKeys(Jedis jedis, String pattern, Integer scanCount, Integer consumerSize,
			long sleep,Consumer<Set<String>> consumer) {
		KSFHashSet<String> keySet = new KSFHashSet<>();
		ScanParams params = new ScanParams();
		params.count(scanCount);
		params.match(pattern);
		String cursor = "0";
		ScanResult<String> result = jedis.scan(cursor, params);
		cursor = result.getCursor();
		keySet.addAll(result.getResult());
		while (keySet.size() >= consumerSize.intValue()) {
			consumer.accept(keySet.pop(consumerSize));
		}
		while (!cursor.equals("0")) {
			result = jedis.scan(cursor, params);
			cursor = result.getCursor();
			keySet.addAll(result.getResult());
			while (keySet.size() >= consumerSize.intValue()) {
				consumer.accept(keySet.pop(consumerSize));
			}
			if(sleep > 0) {
				try {
					TimeUnit.MILLISECONDS.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (keySet.size() > 0) {
			consumer.accept(keySet.pop(keySet.size()));
		}
	}
	
	/**
	 * 模糊扫描redis键 
	 * @param jedis redis的java对象 
	 * @param pattern 模糊查找的key 
	 * @param scanCount 每次模糊查找的数量，推荐不大于10万
	 */
	public static KSFHashSet<String> scanKeys(Jedis jedis, String pattern,Integer scanCount) {
		
		String cursor = "0";
		ScanParams params = new ScanParams();
		params.count(scanCount);
		params.match(pattern);
		KSFHashSet<String> set = new KSFHashSet<>();
		ScanResult<String> result = jedis.scan(cursor,params);
		
		cursor = result.getCursor();
		set.addAll(result.getResult());
		while (!cursor.equals("0")) {
			result = jedis.scan(cursor, params);
			cursor = result.getCursor();
			set.addAll(result.getResult());
			System.out.println(String.format("已加载%s条", set.size()));
		}
		return set;
	}
	
	/**
	 * 模糊扫描redis键 
	 * @param jedis redis的java对象 
	 * @param pattern 模糊查找的key 
	 * @param cursor 游标
	 * @param scanCount 每次模糊查找的数量，推荐不大于10万
	 * @param sleep 休眠
	 * @param consumerSize 当查找到的数据量大于consumerSize值时调用consumer表达式 
	 * @param consumer 处理函数，可以是lamda表达式，如 x->{}
	 */
	public static KSFScanResult scanKeys(Jedis jedis, String pattern,String cursor,Integer scanCount) {
		ScanParams params = new ScanParams();
		params.count(scanCount);
		params.match(pattern);
		KSFHashSet<String> set = new KSFHashSet<>();
		ScanResult<String> result = jedis.scan(cursor,params);
		cursor = result.getCursor();
		set.addAll(result.getResult());
		KSFScanResult scanResult = new KSFScanResult();
		scanResult.setCursor(cursor);
		scanResult.setSet(set);
		return scanResult;
	}
	/**
	 * 模糊扫描redis键 
	 * @param jedis redis的java对象 
	 * @param pattern 模糊查找的key 
	 * @param scanCount 每次模糊查找的数量，推荐不大于10万
	 * @param interval 每次扫描的时间间隔
	 * @param scheduler 调度器
	 * @param executor 统一使用system().dispatcher()
	 * @param consumerSize 当查找到的数据量大于consumerSize值时调用consumer表达式 
	 * @param consumer 处理函数，可以是lamda表达式，如 x->{}
	 */
	public static void scanKeys(Jedis jedis, String pattern, Integer scanCount, 
			Integer interval,
			Scheduler scheduler,
			ExecutionContext executor,
			Integer consumerSize,
			Consumer<Set<String>> consumer) {
		KSFHashSet<String> keySet = new KSFHashSet<>();
		ScanParams params = new ScanParams();
		params.count(scanCount);
		params.match(pattern);
		String cursor = "0";
		List<ScanResult<String>> results = new ArrayList<>();
		results.add(0, jedis.scan(cursor, params));
		List<String> cursors = new ArrayList<>();
		cursors.add(0, results.get(0).getCursor());
		keySet.addAll(results.get(0).getResult());
		scheduler.scheduleAtFixedRate(Duration.ZERO, Duration.ofMillis(50), new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (keySet.size() >= consumerSize.intValue()) {
					consumer.accept(keySet.pop(consumerSize));
				}
				if (!cursors.get(0).equals("0")) {
					results.set(0, jedis.scan(cursors.get(0), params));
					cursors.set(0, results.get(0).getCursor());
					keySet.addAll(results.get(0).getResult());
					while (keySet.size() >= consumerSize.intValue()) {
						consumer.accept(keySet.pop(consumerSize));
					}
				}
				if (keySet.size() > 0) {
					consumer.accept(keySet.pop(keySet.size()));
				}
			}
		}, executor);
	}
	
	
	/**
	 * 添加一个字符串值,成功返回1,失败返回0
	 *
	 * @param key 键
	 * @param value 值
	 * @return 成功返回1,失败返回0
	 */
	public static int set(String key, String value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			if (jedis.set(key, value).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个字符串值,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
	 *
	 * @param key 键
	 * @param value 值
	 * @return 成功返回1,失败返回0
	 */
	public static int setEx(String key, String value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			if (jedis.setex(key, DEFAULT_SETEX_TIMEOUT, value).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个字符串值,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
	 *
	 * @param key 键
	 * @param value 值
	 * @param timeout 超时
	 * @return 成功返回1,失败返回0
	 */
	public static int setEx(String key, String value, int timeout) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			if (jedis.setex(key, timeout, value).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加一个指定类型的对象,成功返回1,失败返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 值
	 * @return 成功返回1,失败返回0
	 */
	public static <T> int set(String key, T value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serialize(value);
			if (jedis.set(key.getBytes(), data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个指定类型的对象,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 值
	 * @return 成功返回1,失败返回0
	 */
	public static <T> int setEx(String key, T value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serialize(value);
			if (jedis.setex(key.getBytes(), DEFAULT_SETEX_TIMEOUT, data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个指定类型的对象,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 值
	 * @param timeout 超时
	 * @return 成功返回1,失败返回0
	 */
	public static <T> int setEx(String key, T value, int timeout) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serialize(value);
			if (jedis.setex(key.getBytes(), timeout, data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个数值+1,成功返回+后的结果,失败返回null
	 *
	 * @param key 键
	 * @return 成功返回+后的结果,失败返回null
	 * @throws JedisDataException 出现数据异常时抛出
	 */
	public static Long incr(String key) throws JedisDataException {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.incr(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个数值+n,成功返回+后的结果,失败返回null
	 *
	 * @param key 键
	 * @param n 添加n
	 * @return 成功返回+后的结果,失败返回null
	 * @throws JedisDataException 出现数据异常时抛出
	 */
	public static Long incrBy(String key, long n) throws JedisDataException {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.incrBy(key, n);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个数值-1,成功返回-后的结果,失败返回null
	 *
	 * @param key 键
	 * @return 成功返回-后的结果,失败返回null
	 * @throws JedisDataException 出现数据异常时抛出
	 */
	public static Long decr(String key) throws JedisDataException {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.decr(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个数值-n,成功返回-后的结果,失败返回null
	 *
	 * @param key 键
	 * @param n 减去n
	 * @return 成功返回-后的结果,失败返回null
	 * @throws JedisDataException 出现数据异常时抛出
	 */
	public static Long decrBy(String key, long n) throws JedisDataException {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.decrBy(key, n);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个字符串值到list中,成功返回1,失败返回0
	 *
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败返回0
	 */
	public static int setList(String key, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.rpush(key, value);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存n个字符串值到list中,全部list的key默认缓存时间为1小时,成功返回1,失败返回0
	 *
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败返回0
	 */
	public static int setExList(String key, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.rpush(key, value);
			jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}

		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个字符串值到list中,全部list的key缓存时间为timeOut,单位为秒,成功返回1,失败返回0
	 *
	 * @param key 键
	 * @param timeOut 超时时间
	 * @param value 展开的string数组
	 * @return 成功返回1,失败返回0
	 */
	public static int setExList(String key, int timeOut, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.rpush(key, value);
			jedis.expire(key, timeOut);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}

		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个<T>类型对象值到list中,成功返回1,失败返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 展开的实体对象数组
	 * @return 成功返回1,失败返回0
	 */
	@SafeVarargs
	public static <T> int setList(String key, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			Long result = jedis.rpush(key.getBytes(), list.toArray(new byte[list.size()][]));

			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存n个<T>类型对象值到list中,全部list的key默认缓存时间为1小时,成功返回1,失败返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 展开的实体对象数组
	 * @return 成功返回1,失败返回0
	 */
	@SafeVarargs
	public static <T> int setExList(String key, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			Long result = jedis.rpush(key.getBytes(), list.toArray(new byte[list.size()][]));
			jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存一个<T>类型对象值到list中,全部list的key缓存时间为timeOut,单位秒,成功返回1,失败返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param timeOut 超时时间
	 * @param value 展开的实体对象数组
	 * @return 成功返回1,失败返回0
	 */
	@SafeVarargs
	public static <T> int setExList(String key, int timeOut, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			int res = 0;
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			Long result = jedis.rpush(key.getBytes(), list.toArray(new byte[list.size()][]));
			jedis.expire(key, timeOut);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个List集合作为一个整体存入一个key-value,成功返回1,失败返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value list集合
	 * @return 成功返回1,失败返回0
	 * @throws IOException 发生IO异常时抛出
	 * @throws RuntimeException 当序列化出现异常时抛出
	 */
	public static <T> int setList(String key, List<T> value) throws RuntimeException, IOException {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serializeList(value);
			if (jedis.set(key.getBytes(), data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个List集合作为一个整体存入一个key-value,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value list集合
	 * @return 成功返回1,失败返回0
	 * @throws IOException 发生IO异常时抛出
	 * @throws RuntimeException 当序列化出现异常时抛出
	 */

	public static <T> int setExList(String key, List<T> value) throws RuntimeException, IOException {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serializeList(value);
			if (jedis.setex(key.getBytes(), DEFAULT_SETEX_TIMEOUT, data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 将一个List集合作为一个整体存入一个key-value,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value list集合
	 * @param timeout 超时时间
	 * @return 成功返回1,失败返回0
	 * @throws IOException 发生IO异常时抛出
	 * @throws RuntimeException 当序列化出现异常时抛出
	 */
	public static <T> int setExList(String key, List<T> value, int timeout) throws RuntimeException, IOException {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = ProtostuffUtil.serializeList(value);
			if (jedis.setex(key.getBytes(), timeout, data).equalsIgnoreCase("ok")) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个字符串到set,如果key存在就在就最追加,如果key不存在就创建,成功返回1,失败或者没有受影响返回0
	 *
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	public static int setSet(String key, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.sadd(key, value);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个字符串set,如果key存在就在就最追加,整个set的key默认一小时后过期,如果key存在就在可以种继续添加,如果key不存在就创建,成功返回1,失败或者没有受影响返回0
	 *
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	public static int setExSet(String key, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.sadd(key, value);
			jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个字符串set,如果key存在就在就最追加,整个set的key有效时间为timeOut时间,单位秒,如果key存在就在可以种继续添加,如果key不存在就创建,,成功返回1,失败或者没有受影响返回0
	 *
	 * @param key 键
	 * @param timeOut 超时时间
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	public static int setExSet(String key, int timeOut, String... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Long result = jedis.sadd(key, value);
			jedis.expire(key, timeOut);
			if (result != null && result != 0) {
				return 1;
			} else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加n个<T>类型到set集合,如果key存在就在就最追加,成功返回1,失败或者没有受影响返回0
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	@SafeVarargs
	public static <T> int setSet(String key, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			
			Long result = jedis.sadd(key.getBytes(), list.toArray(new byte[list.size()][]));
			if (result != null && result != 0) {
				return 1;
			}
			else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存n个<T>类型到set集合,如果key存在就在就最追加,整个set的key默认有效时间为1小时,成功返回1,失败或者没有受影响返回0
	 *
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	@SafeVarargs
	public static <T> int setExSet(String key, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			
			Long result = jedis.sadd(key.getBytes(), list.toArray(new byte[list.size()][]));
			jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
			if (result != null && result != 0) {
				return 1;
			}
			else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 缓存n个<T>类型到set集合,如果key存在就在就最追加,整个set的key有效时间为timeOut,单位秒,成功返回1,失败或者没有受影响返回0
	 *
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param timeOut 超时时间
	 * @param value 展开的string数组
	 * @return 成功返回1,失败或者没有受影响返回0
	 */
	@SafeVarargs
	public static <T> int setExSet(String key, int timeOut, T... value) {
		if (KSFUtil.isValueNull(key, value)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> list = new ArrayList<>();
			for (T t : value) {
				byte[] data = ProtostuffUtil.serialize(t);
				list.add(data);
			}
			
			Long result = jedis.sadd(key.getBytes(), list.toArray(new byte[list.size()][]));
			jedis.expire(key, timeOut);
			if (result != null && result != 0) {
				return 1;
			}
			else {
				return 0;
			}
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 设置map中的值,如果map不存在则创建
	 * 
	 * @param key 键
	 * @param field map-key
	 * @param value map-value
	 * @return 设置结果
	 */
	public static long mapSet(String key, String field, String value) {
		if (KSFUtil.isValueNull(key, field, value)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hset(key, field, value);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 设置map中的值,如果map不存在則创建
	 * @param <K> map键泛型
	 * @param <V> map值泛型
	 * @param key 键
	 * @param field map-key
	 * @param value map-value
	 * @return 设置结果
	 */
	public static <K, V> Long mapSet(String key, K field, V value) {
		if (KSFUtil.isValueNull(key, field, value)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hset(key.getBytes(), ProtostuffUtil.serialize(field),
					ProtostuffUtil.serialize(value));
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 設置map中的值,如果map不存在則創建
	 * 
	 * @param key 键
	 * @param field map-key
	 * @param value map-value
	 * @return 设置结果
	 */
	public static Long mapSet(String key, byte[] field, byte[] value) {
		if (KSFUtil.isValueNull(key, field, value)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hset(key.getBytes(), field, value);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取map中的值
	 * 
	 * @param key 键
	 * @param field map-key
	 * @return 查询的结果
	 */
	public static String mapValGet(String key, String field) {
		if (KSFUtil.isValueNull(key, field)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hget(key, field);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取map中指定fields的多個值
	 * @param key 键
	 * @param fields 展开的String类型map-key数组
	 * @return 查询的结果
	 */
	public static List<String> mapValsGet(String key, String... fields) {
		if (KSFUtil.isValueNull(key, fields)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hmget(key, fields);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 刪除Map中指定fields的Value
	 * @param key 键
	 * @param fields 展开的String类型map-key数组
	 * @return 删除的结果
	 */
	public static long mapValDel(String key, String... fields) {
		if (KSFUtil.isValueNull(key, fields)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hdel(key, fields);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 刪除Map中指定fields的Value
	 * @param key 键
	 * @param field String类型map-key
	 * @return 删除的结果
	 */
	public static boolean mapExists(String key, String field) {
		if (KSFUtil.isValueNull(key, field)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hexists(key, field);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取map中key集合
	 * @param key 键
	 * @return key集合
	 */
	public static Set<String> mapKeySet(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hkeys(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取map中value集合
	 * @param key 键
	 * @return value集合
	 */
	public static List<String> mapValueSet(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hvals(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 批量设置map键值对,如果map不存在则创建,如果存在則批量插入
	 * @param key 键
	 * @param map map-key,map-value的集合
	 */
	public static void mapBatchSet(String key, Map<String, String> map) {
		if (KSFUtil.isValueNull(key, map)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			jedis.hmset(key, map);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}
	/**
	 * 批量设置map键值对,如果map不存在则创建,如果存在則批量插入
	 * @param key 键
	 * @param map map-key,map-value的集合
	 */
	public static void mapBatchSet(byte[] key, Map<byte[], byte[]> map) {
		if (KSFUtil.isValueNull(key, map)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			jedis.hmset(key, map);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取整个map對象
	 * @param key 键
	 * @return 获取的数据
	 */
	public static Map<String, String> mapGetAll(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hgetAll(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取map的长度
	 * @param key 键
	 * @return map的长度
	 */
	public static long mapLen(String key) {
		if (KSFUtil.isValueNull(key)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.hlen(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 添加一个Map<K, V>集合,如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1, 如果哈希表中域字段已经存在且旧值已被新值覆盖返回0
	 * @param <K> map键泛型
	 * @param <V> map值泛型
	 * @param key 键
	 * @param value map-key,map-value的集合
	 * @return 添加结果
	 */
	public static <K, V> Long setMap(String key, Map<K, V> value) {
		if (value == null || key == null || "".equals(key)) {
			return 0L;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Map<byte[], byte[]> org = ProtostuffUtil.serializeMap(value);
			return jedis.hset(key.getBytes(), org);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获取一个字符串值
	 *
	 * @param key 键
	 * @return String类型的值
	 */
	public static String get(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.get(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个指定类型的对象
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param clazz 值对应的java类型
	 * @return 实体对象
	 */
	public static <T> T get(String key, Class<T> clazz) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();

			byte[] data = jedis.get(key.getBytes());
			T result = ProtostuffUtil.deserialize(data, clazz);
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个字符串集合,区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 *
	 * @param key 键
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return 查询结果
	 */
	public static List<String> getList(String key, long start, long end) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<String> result = jedis.lrange(key, start, end);
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个<T>类型的对象集合,区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param start 起始位置
	 * @param end 结束位置
	 * @param clazz 实体Java类型
	 * @return 查询结果
	 */
	public static <T> List<T> getList(String key, long start, long end, Class<T> clazz) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			List<byte[]> lrange = jedis.lrange(key.getBytes(), start, end);
			List<T> result = null;
			if (lrange != null) {
				for (byte[] data : lrange) {
					if (result == null) {
						result = new ArrayList<>();
					}
					result.add(ProtostuffUtil.deserialize(data, clazz));
				}
			}
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得list中存了多少个值
	 * @param key 键
	 * @return 值的数量
	 */
	public static long getListCount(String key) {
		if (KSFUtil.isValueNull(key)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.llen(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个List<T>的集合
	 * @param <T> 实体泛型
	 * @param key 键
	 * @return 查询结果
	 * @throws IOException 发生IO异常时抛出
	 */
	public static <T> List<T> getList(String key, Class<T> clazz) throws IOException {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			byte[] data = jedis.get(key.getBytes());
			List<T> result = ProtostuffUtil.deserializeList(data,clazz);
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个字符串set集合
	 *
	 * @param key 键
	 * @return 查询结果
	 */
	public static Set<String> getSet(String key) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Set<String> result = jedis.smembers(key);
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个字符串set集合
	 * @param <T> 实体泛型
	 * @param key 键
	 * @param clazz 实体Java类型
	 * @return 查询结果
	 */
	public static <T> Set<T> getSet(String key, Class<T> clazz) {
		if (KSFUtil.isValueNull(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Set<byte[]> smembers = jedis.smembers(key.getBytes());
			Set<T> result = null;
			if (smembers != null) {
				for (byte[] data : smembers) {
					if (result == null) {
						result = new HashSet<>();
					}
					result.add(ProtostuffUtil.deserialize(data, clazz));
				}
			}
			return result;
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得集合中存在多少个值
	 * 
	 * @param key 键
	 * @return 获取Set中包含多少个值
	 */
	public static long getSetCount(String key) {
		if (KSFUtil.isValueNull(key)) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.scard(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 获得一个Map<k,v>的集合
	 * @param <K> map键泛型
	 * @param <V> map值泛型
	 * @param key 键
	 * @param v map值对应的Java类型
	 * @param k map键对应的Java类型
	 * @return 查询结果
	 */
	public static <K, V> Map<K, V> getMap(String key, Class<K> k, Class<V> v) {
		if (key == null || "".equals(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			Map<byte[], byte[]> map = jedis.hgetAll(key.getBytes());
			return ProtostuffUtil.deserializerMap(map, k, v);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 判斷key是否存在
	 * 
	 * @param key 键
	 * @return 是否存在
	 */
	public static boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.exists(key);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 判斷key是否存在
	 * 
	 * @param keys 展开的键数组
	 * @return 是否存在
	 */
	public static Long exists(String... keys) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			return jedis.exists(keys);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}

	/**
	 * 删除n个值
	 * @param keys 展开的键数组
	 */
	public static void del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			jedis.del(keys);
		} finally {
			JedisPoolUtil.release(jedis);
		}
	}
}
