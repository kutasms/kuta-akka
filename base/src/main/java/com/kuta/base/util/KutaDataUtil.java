package com.kuta.base.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.apache.ibatis.session.SqlSession;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.database.KutaSQLUtil;

public class KutaDataUtil {
	public static void execute(KutaBiConsumer<SqlSession,JedisClient,Exception> consumer) throws Exception {
		KutaSQLUtil.exec(session->{
			KutaRedisUtil.exec(jedis->{
				consumer.accept(session, jedis);
			});
		});
	}
	public static <T> T func(BiFunction<SqlSession,JedisClient, T> func) throws Exception {
		return KutaSQLUtil.func(session->{
			return KutaRedisUtil.func(jedis->{
				return func.apply(session, jedis);
			});
		});

	}
}
