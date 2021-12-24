package com.kuta.data;

import org.junit.Test;

import com.kuta.base.database.DataSessionFactory;

/**
 * It is used to detect whether redis transactions are executed 
 * normally in stand-alone mode and cluster mode
 * */
public class RedisTest {
	@Test
	public void test() {
		DataSessionFactory f = null;
		try {
			f = DataSessionFactory.create();
			f.getJedis();
			f.getSqlSession();
		} finally {
			f.release();
		}
	}
	
	
}
