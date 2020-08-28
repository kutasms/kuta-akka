package com.kuta.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.function.Consumer;

/**
 * KSF流读取器
 * */
public class KutaStreamReader {
	/**
	 * 读取行数据
	 * @param stream 流数据
	 * @param consumer 函数式消费对象
	 * */
	public static void readLine(InputStream stream, Consumer<String> consumer) {
		InputStreamReader reader = null;
		BufferedReader bf = null;
		try {
			reader = new InputStreamReader(stream);
			 bf = new BufferedReader(new InputStreamReader(stream));
			 String s = null;
			 try {
				while(!(s = bf.readLine()).equals("exit")) {
					 consumer.accept(s);
				}
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
