package com.kuta.base.database;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisPoolUtil;

public class DataSessionFactory {
	private final Logger logger = LoggerFactory.getLogger(DataSessionFactory.class);
	private SqlSession session;
	private JedisClient jedis;
	private boolean usedRedisTx = false;
	private boolean usedSqlBatchMode = false;
	private DataSessionFactory() {
		
	}
	public JedisClient getJedis() {
		return getJedis(false);
	}
	public JedisClient getJedis(boolean usedRedisTx) {
		if(jedis == null) {
			jedis = JedisPoolUtil.getJedis();
			this.usedRedisTx = usedRedisTx;
			if(usedRedisTx) {
				jedis.multi();
			}
		}
		return jedis;
	}
	public SqlSession getSqlSession() {
		return getSqlSession(false);
	}
	public SqlSession getSqlSession(boolean useSqlBatchMode) {
		if(session == null) {
			this.usedSqlBatchMode = useSqlBatchMode;
			if(useSqlBatchMode) {
				session = MybatisUtil.getBatchSession();
			} else {
				session = MybatisUtil.getSession();
			}
		}
		return session;
	}
	public SqlSession getSqlSession(ExecutorType execType, TransactionIsolationLevel level) {
		if(session == null) {
			session = MybatisUtil.getSession(execType,level);
		}
		return session;
	}
	
	public void release() {
		logger.debug("sqlbatchmode:{}",usedSqlBatchMode);
		try {
			if(session!=null) {
				session.commit();
			}
			if(usedRedisTx) {
				jedis.exec();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			if(session!=null) {
				session.rollback();
			}
			if(usedRedisTx) {
				jedis.discard();
			}
		} finally {
			if(session!=null) {
				logger.info("销毁SQL数据对象");
				MybatisUtil.release(session);
			}
			if(jedis!=null) {
				logger.info("销毁Jedis数据对象");
				JedisPoolUtil.release(jedis.getJedis());
			}
		}
	}
	
	public static DataSessionFactory create() {
		return new DataSessionFactory();
	}
}
