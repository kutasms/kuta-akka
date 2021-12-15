package com.kuta.data;

import java.io.File;
import java.net.URL;

import com.kuta.base.database.KutaSQLUtil;
import com.kuta.base.io.FileUtil;

public class TestSqlIniter {
	public static void runWithFile(String fileName) throws Exception {
		
		KutaSQLUtil.execSql("SHOW TABLES LIKE 'BS_User'", rsp->{
			if(!rsp.next()) {
				System.out.println("initize database infomation.");
				URL url = TestSqlIniter.class.getClassLoader().getResource(fileName);
				String content = FileUtil.readFile(new File(url.getPath()));
				KutaSQLUtil.execSql(content, result->{
					String output = rsp.getString(0);
					System.out.println(output);
					return null;
				});
			}
			return null;
		});
	}
}
