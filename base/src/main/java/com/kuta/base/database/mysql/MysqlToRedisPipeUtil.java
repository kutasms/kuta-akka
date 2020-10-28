package com.kuta.base.database.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.kuta.common.config.utils.PropertyUtil;

import redis.clients.jedis.HostAndPort;

/**
 * 以linux管线将mysql数据导入redis的工具类
 * */
public class MysqlToRedisPipeUtil {
	
	/**
	 * 执行导入操作
	 * @param sqlFileName sql文件地址
	 * @param desc 说明信息
	 * @return 执行结果
	 * */
	public static boolean execute(String sqlFileName, String desc, HostAndPort hostAndPort) {
		URL url = MysqlToRedisPipeUtil.class.getClassLoader().getResource(sqlFileName);
		String sqlPath = url.getPath();

		List<String> command = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		command.add("sh");
		command.add("-c");
		arguments.add("mysql");
		arguments.add("-uroot");
		String password = PropertyUtil.getProperty("jdbc", "jdbc.password");
		if(password.contains("&")) {
			password = password.replace("&", "\\&");
		}
		arguments.add("-p" + password);
		arguments.add("-P" + PropertyUtil.getProperty("jdbc", "jdbc.port"));
		arguments.add("-Dsimcity");
		arguments.add("-h" + PropertyUtil.getProperty("jdbc", "jdbc.host"));
		arguments.add("--default-character-set=utf8");
		// commandList.add("-h");
		// commandList.add(PropertyUtils.getProperty("jdbc", "jdbc.host"));
		arguments.add("--skip-column-names");
		arguments.add("--raw");
		arguments.add("<");
		arguments.add(sqlPath);
		arguments.add("|");
		arguments.add("redis-cli");
		arguments.add("-h");
		arguments.add(hostAndPort.getHost());
		arguments.add("-p");
		arguments.add(String.valueOf(hostAndPort.getPort()));
		arguments.add("-a");
		arguments.add(PropertyUtil.getProperty("redis", "redis.pwd"));
		arguments.add("-c");
		arguments.add("--pipe");
		command.add(StringUtils.join(arguments, " "));
		System.out.println(Arrays.deepToString(arguments.toArray()));
		ProcessBuilder pb = new ProcessBuilder(command);

		File file = new File(url.getPath());
		pb.directory(file.getParentFile());
		final AtomicInteger success = new AtomicInteger(0);
		
		try {
			Process p = pb.start();
			// 获取进程的标准输入流
			final InputStream is1 = p.getInputStream();
			// 获取进城的错误流
			final InputStream is2 = p.getErrorStream();

			new Thread() {
				public void run() {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
					try {
						String line = null;
						while ((line = br1.readLine()) != null) {
							if (line != null) {

								System.out.println("INFO:" + line);
								if(line.contains("errors: 0")) {
									System.out.println("包含errors: 0");
									success.incrementAndGet();
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is1.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			new Thread() {
				public void run() {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is2));
					try {
						String line = null;
						while ((line = br1.readLine()) != null) {
							if (line != null) {
								System.out.println("ERROR:" + line);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			try {
				p.waitFor();
				if(success.get() > 0) {
					System.out.println("REDIS导入" + desc + "操作成功");
					return true;
				}
				return false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
