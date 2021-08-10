package com.kuta.base.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.function.Function;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.kuta.base.annotation.PrimaryKey;
import com.kuta.base.util.ThrowingConsumer;
import com.kuta.base.util.ThrowingFunction;

/**
 * 数据库连接池应用工具类，自动回收连接
 * */
public class KutaSQLUtil {
	
	
	/**
	 * 判断当前字段是否为主键字段
	 * @param field 通过反射获取的Field对象
	 * @return true:是主键,false:非主键
	 * */
	public static boolean isPrimaryKey(Field field) {
		Annotation[] anns = field.getAnnotations();
		if(anns!=null && anns.length > 0) {
			for(int i=0;i<anns.length;i++) {
				if(anns[i].annotationType().getName().equals(PrimaryKey.class.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isIncrIgoneColumn(Field field) {
		Annotation[] anns = field.getAnnotations();
		if(anns!=null && anns.length > 0) {
			for(int i=0;i<anns.length;i++) {
				if(anns[i].annotationType().getName().equals(PrimaryKey.class.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 执行数据库相关操作
	 * @param consumer 函数式数据执行单元
	 * @throws Exception 内部异常
	 * */
	public static void exec(ThrowingConsumer<SqlSession,Exception> consumer) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSession();
			consumer.accept(session); 
			session.commit();
		}
		catch (Exception e) {
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	
	/**
	 * 执行数据库操作，更改事务执行级别
	 * @param execType 执行类型
	 * @param level 事务级别
	 * @param consumer 函数式数据执行单元
	 * @throws Exception 内部异常
	 * */
	public static void execTrans(ExecutorType execType, TransactionIsolationLevel level,ThrowingConsumer<SqlSession,Exception> consumer) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSession(execType,level);
			consumer.accept(session); 
			session.commit();
		}
		catch (Exception e) {
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	
	/**
	 * 执行数据库相关操作 (已废弃，请使用func方法)
	 * @param func 带返回结果的函数式数据执行单元
	 * @return 返回执行结果
	 * @throws Exception 内部异常
	 * */
	@Deprecated
	public static <T> T exec(ThrowingFunction<SqlSession,T,Exception> func) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSession();
			T t = func.apply(session);
			session.commit();
			return t;
		}
		catch (Exception e) {
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	public static <T> T func(Function<SqlSession,T> func) {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSession();
			T t = func.apply(session);
			session.commit();
			return t;
		}
		catch (Exception e) {
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	/**
	 * 批量方式执行数据库操作
	 * @param func 带返回结果的函数式数据执行单元
	 * @return 返回执行结果
	 * @throws Exception 内部异常
	 * */
	public static <T> T execBatch(ThrowingFunction<SqlSession,T,Exception> func) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getBatchSession();
			T t = func.apply(session);
			session.commit();
			return t;
		}
		catch (Exception e) {
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	/**
	 * 批量方式执行数据库操作
	 * @param consumer 函数式数据执行单元
	 * @throws Exception 内部异常
	 * */
	public static void execBatch(ThrowingConsumer<SqlSession,Exception> consumer) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getBatchSession();
			consumer.accept(session);
			session.commit();
		}
		catch (Exception e) {
			// TODO: handle exception
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	/**
	 * 批量方式执行数据库操作
	 * <p style='color:#ff0000;'>此方法将不会在执行单元执行完毕后commit,需要调用者自己commit</p>
	 * @param consumer 函数式数据执行单元
	 * @throws Exception 内部异常
	 * */
	public static void execBatchNotCommit(ThrowingConsumer<SqlSession,Exception> consumer) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getBatchSession();
			consumer.accept(session);
		}
		catch (Exception e) {
			// TODO: handle exception
			if(session!=null) {
				session.rollback();
			}
			throw e;
		}
		finally {
			MybatisUtil.release(session);
		}
	}
	/**
	 * 执行指定的sql语句
	 * @param sql 需要执行的sql语句
	 * @param consumer 函数式数据执行单元
	 * @return 执行结果
	 * @throws Exception 内部异常
	 * */
	public static <T> T execSql(String sql,ThrowingFunction<ResultSet,T,Exception> consumer) throws Exception {
		SqlSession session = null;
		ResultSet result;
		try {
			session = MybatisUtil.getSession();
			result = session.getConnection().prepareStatement(sql).executeQuery();
			T t = consumer.apply(result);
			return t;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if(session!=null) {
				session.rollback();
			}
			throw e1;
		} finally {
			MybatisUtil.release(session);
		}
	}
	public static <T> T execUpdateSql(String sql,ThrowingFunction<Integer,T,Exception> consumer) throws Exception {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSession();
			int result = session.getConnection().prepareStatement(sql).executeUpdate(sql);
			T t = consumer.apply(result);
			return t;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if(session!=null) {
				session.rollback();
			}
			throw e1;
		} finally {
			MybatisUtil.release(session);
		}
	}
}
