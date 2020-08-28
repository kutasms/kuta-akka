package com.kuta.base.database;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 实现Druid数据源工厂
 * */
public class DruidDataSourceFactory implements DataSourceFactory {

	/**
	 * 属性集合
	 * */
	private Properties properties;

	/**
	 * 设置属性对象
	 * @param properties 属性对象
	 * */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * 构造函数
	 * */
	public DruidDataSourceFactory() {
	}

	/**
	 * 获取数据源
	 * */
	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		DruidDataSource dds = new DruidDataSource();

		try {
			dds.setUrl(properties.getProperty("url"));
			dds.setUsername(properties.getProperty("username"));
			dds.setPassword(properties.getProperty("password"));
			dds.setFilters(properties.getProperty("filters"));
			dds.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
			dds.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
			dds.setMaxWait(Integer.parseInt(properties.getProperty("maxWait")));
			dds.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
			dds.setTimeBetweenEvictionRunsMillis(
					Integer.parseInt(properties.getProperty("timeBetweenEvictionRunsMillis")));
			dds.setMinEvictableIdleTimeMillis(
					Long.parseLong(properties.getProperty("minEvictableIdleTimeMillis")));
			dds.setValidationQuery(properties.getProperty("validationQuery"));
			dds.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("testWhileIdle")));
			dds.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
			dds.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("testOnReturn")));
			dds.setPoolPreparedStatements(Boolean.parseBoolean(properties.getProperty("poolPreparedStatements")));
			dds.setMaxPoolPreparedStatementPerConnectionSize(
					Integer.parseInt(properties.getProperty("maxPoolPreparedStatementPerConnectionSize")));
			dds.setRemoveAbandoned(Boolean.parseBoolean(properties.getProperty("removeAbandoned")));
			dds.setRemoveAbandonedTimeout(Integer.parseInt(properties.getProperty("removeAbandonedTimeout")));
			dds.setLogAbandoned(Boolean.parseBoolean(properties.getProperty("logAbandoned")));
			dds.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dds;
	}
}
