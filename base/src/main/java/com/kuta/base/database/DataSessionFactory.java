package com.kuta.base.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisPoolUtil;

public class DataSessionFactory implements Closeable {
	private final Logger logger = LoggerFactory.getLogger(DataSessionFactory.class);
	private SqlSession session = null;
	private JedisClient jedis = null;
	private boolean usedRedisTx = false;
	private boolean usedSqlBatchMode = false;
	private boolean rollbacked = false;
	private boolean reprocessOnErrorOccurred = false;
	private DataSessionFactory() {
	
	}
	public JedisClient getJedis() {
		return getJedis(false);
	}
	
	public boolean isRollbacked() {
		return rollbacked;
	}
	
	public void enableJedisTrans() {
		if(!this.usedRedisTx) {
			this.usedRedisTx = true;
			jedis.multi();
		}
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
		try {
			session.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return session;
	}
	public SqlSession getSqlSession(ExecutorType execType, TransactionIsolationLevel level) {
		if(session == null) {
			session = MybatisUtil.getSession(execType,level);
		}
		return session;
	}
	
	/**
	 * <p>Rollback database and cached data</p>
	 * This method is very important. When an error occurs, 
	 * you may need to roll back the data. Please note that 
	 * the latest database snapshot will be obtained after 
	 * the rollback.
	 * */
	public void rollback() {
		if(!this.rollbacked) {
			logger.info("回滚数据");
			this.rollbacked = true;
			if(this.session != null) {
				session.rollback();
			}
			if(usedRedisTx && this.jedis!=null) {
				this.jedis.discard();
			}
		}
	}
	public void release() {
		logger.debug("sqlbatchmode:{}",usedSqlBatchMode);
		try {
			if(!rollbacked && session!=null) {
				session.commit();
			}
			if(!rollbacked && usedRedisTx) {
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
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		release();
	}
	public boolean isReprocessOnErrorOccurred() {
		return reprocessOnErrorOccurred;
	}
	public void setReprocessOnErrorOccurred(boolean reprocessOnErrorOccurred) {
		this.reprocessOnErrorOccurred = reprocessOnErrorOccurred;
	}
}
