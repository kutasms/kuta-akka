package com.kuta.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Java serialization tool
 * */
public class JavaSerializer {
	/**
	 * Convert Java objects to serialized binary objects
	 * @param src An object that implements the serializable interface
	 * @return Serialized byte array
	 */
	public static byte[] serialize(Serializable src) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
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
	 * Deserialization, converting binary data to Java objects
	 * @param src Byte array to be deserialized
	 * @return Deserialized Java object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] src) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
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
