package com.kuta.data.mysql.biz;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kuta.base.database.DataSessionFactory;
import com.kuta.base.util.KutaConsoleUtil;

public class SystemConfigBizTest {

	DataSessionFactory f = null;
	@Before
	public void before() {
		f = DataSessionFactory.create();
	}
	@After
	public void after() {
		f.release();
	}
	
	@Test
	public void testGet() throws Exception {
		SystemConfigBiz biz = new SystemConfigBiz();
		Map<String, String> map = biz.get(f.getSqlSession(), f.getJedis());
		KutaConsoleUtil.printObj(map, true);
	}

}
