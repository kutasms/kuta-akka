package com.kuta.base.database;

import java.io.InputStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

/**
 * mybatis工具类
 * */
public class MybatisUtil {
	
	/**
	 * 配置文件
	 * */
	private static final String resPath = "mybatis-config.xml";
	/**
	 * 输入流
	 * */
	private static InputStream inputStream;
	/**
	 * 数据库连接抽象工厂
	 * */
	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			inputStream = MybatisUtil.class.getClassLoader().getResourceAsStream(resPath);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inputStream = null;
			sqlSessionFactory = null;
		}
		finally {
		}
	}
	
	/**
	 * 获取自动commit的数据库连接
	 * @return 数据库连接
	 * */
	public static SqlSession getSession() {
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 获取批量执行的数据库连接
	 * <p>此数据库连接不会自动commit</p>
	 * @return 数据库连接
	 * */
	public static SqlSession getBatchSession() {
		return sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	}
	/**
	 * 获取事务级别可控的数据库连接
	 * @param execType执行类型
	 * @param level 事务级别
	 * @return 数据库连接
	 * */
	public static SqlSession getSession(ExecutorType execType, TransactionIsolationLevel level) {
		return sqlSessionFactory.openSession(execType, level);
	}
	/**
	 * 释放数据库连接
	 * */
	public static void release(SqlSession session) {
		if(session!=null) {
			session.clearCache();
			session.close();
		}
	}
}
