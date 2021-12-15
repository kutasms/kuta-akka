package com.kuta.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.kuta.base.database.DataSessionFactory;
import com.kuta.data.mysql.biz.SystemConfigBiz;
import com.kuta.data.mysql.pojo.SystemConfig;

public class MysqlTest {
	
	
	public class TransactionThread extends Thread{
		@Override
		public void run() {
			DataSessionFactory f = null;
			try {
				f = DataSessionFactory.create();
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				try {
					
					Random random = new Random();
					Thread.sleep(random.nextInt(5000) + 200);
					SystemConfig org = configBiz.get("unit_test_num", f.getSqlSession());
					System.out.println(String.format("【线程%s】 查询结果:%s,版本号:%s", Thread.currentThread().getId(), org.getValue(), org.getOpVersion()));

					Thread.sleep(random.nextInt(500) + 200);
					org.setValue(String.valueOf(Integer.parseInt(org.getValue()) + 2));
					System.out.println(String.format("【线程%s】 将数据修改为:%s", Thread.currentThread().getId(), org.getValue()));
					SystemConfig newConfig = new SystemConfig();
					newConfig.setKey(org.getKey());
					newConfig.setValue(org.getValue());
					newConfig.setOpVersion(org.getOpVersion());
					int result = configBiz.updateWithOptimisticLock(f.getSqlSession(), newConfig);
					System.out.println("【线程" + Thread.currentThread().getId() + "】 更新结果为:" + (result > 0 ? "成功":"失败"));
					
					if(result == 0) {
						f.rollback();
						System.out.println("【线程" + Thread.currentThread().getId() + "】 已回滚");
						SystemConfig config = configBiz.get("unit_test_num", f.getSqlSession());
						System.out.println(String.format("【线程%s】 修改后查询结果:%s,版本:%s", Thread.currentThread().getId(), config.getValue(), config.getOpVersion()));
					} else {
						SystemConfig config = configBiz.get("unit_test_num", f.getSqlSession());
						System.out.println(String.format("【线程%s】 修改后查询结果:%s,版本:%s", Thread.currentThread().getId(), config.getValue(), config.getOpVersion()));
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				f.release();
				System.out.println(String.format("【线程%s】已清理F", Thread.currentThread().getId()));
			}
			
		}
		
	}
	private final SystemConfigBiz configBiz = new SystemConfigBiz();
	
	/**
	 * run test.sql file to database before test.
	 * */
	@Before
	public void init() throws Exception{
		TestSqlIniter.runWithFile("ksf-mysql.sql");
	}
	
	/**
	 * run a test for data searching and data saving in a same transaction. 
	 * @throws Exception 
	 * */
	@Test
	public void testDataSearchAndSaveInSameTransaction() throws Exception {
		List<Thread> list = new ArrayList<Thread>();
		for(int i=0;i<10;i++) {
			TransactionThread t = new TransactionThread();
			t.start();
			list.add(t);
		}
		list.forEach(item->{try {
			item.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
}
