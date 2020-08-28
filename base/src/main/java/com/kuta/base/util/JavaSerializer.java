package com.kuta.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * java序列化工具
 * */
public class JavaSerializer {
	/**
	 * JavaObj转换为序列化二进制对象
	 * @param src 实现了Serializable 接口的对象
	 * @return 序列化后的byte数组
	 */
	public static byte[] serialize(Serializable src) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				baos.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 反序列化，二进制数据转换为javaObj
	 * @param src 待反序列化的byte数组
	 * @return 反序列化后的java对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] src) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(src);
			ois = new ObjectInputStream(bais);
			return (T)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				bais.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}
