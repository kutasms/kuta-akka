package com.kuta.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kuta.base.database.DataSessionFactory;
import com.kuta.data.mysql.biz.RoleBiz;
import com.kuta.data.mysql.biz.UserBiz;
import com.kuta.data.mysql.pojo.Role;
import com.kuta.data.mysql.pojo.extend.UserExt;

public class DataTransactionTest {
	
	DataSessionFactory f = null;
	UserBiz userBiz = new UserBiz("User_%s");
	RoleBiz roleBiz = new RoleBiz();
	@Before
	public void before() {
		f = DataSessionFactory.create();
	}
	
	@After
	public void after() {
		f.release();
	}
	
	/**
	 * Test whether the data is normal when the redis 
	 * update is normal but the database update fails.
	 * */
	@Test
	public void testRedisUpdateCompletedAndDBError() throws Exception {
		userBiz.removeCache(f.getJedis(), 7);
		UserExt user = userBiz.getOneByKey(f, 7);
		assertNotNull(user);
		
		try {
			user.setPhone("138000000000");
			userBiz.dbCacheWithKey(f, user);
		}
		catch (PersistenceException e) {
			// TODO: handle exception
			f.rollback();
			user = userBiz.getOneByKey(f, 7);
			assertEquals(user.getPhone(), "13886485355");
		}
	}
	
	/**
	 * Update multiple data at the same time, 
	 * and test whether other data is rolled 
	 * back normally when one of them fails
	 * @throws Exception 
	 * */
	@Test
	public void testMultiUpdateErrorOccurred() throws Exception {
		roleBiz.removeCache(f.getJedis(), 4);
		userBiz.removeCache(f.getJedis(), 7);
		Role role = null;
		UserExt user = userBiz.getOneByKey(f, 7);
		assertNotNull(user);
		try {
			role = roleBiz.getOneByKey(f,4);
			role.setDesc("Marketing Manager");
			user.setPhone("138000000000");
			roleBiz.dbCacheWithKey(f, role);
			userBiz.dbCacheWithKey(f, user);
		}
		catch (Exception e) {
			// TODO: handle exception
			f.rollback();
			role = roleBiz.getOneByKey(f,4);
			assertNotEquals(role.getDesc(), "Marketing Manager");
		}
	}
}
