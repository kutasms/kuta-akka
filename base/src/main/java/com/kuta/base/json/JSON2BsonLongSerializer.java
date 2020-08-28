package com.kuta.base.json;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * json和bson的long类型序列化器
 * */
public class JSON2BsonLongSerializer implements ObjectSerializer {

	/**
	 * 单例序列化对象
	 * */
	private static JSON2BsonLongSerializer instance;
	
	/**
	 * 获取单例序列化对象
	 * @return 单例序列化对象
	 * */
	public static JSON2BsonLongSerializer getInstance() {
		if(instance == null) {
			instance = new JSON2BsonLongSerializer();
		}
		return instance;
	}
	
	/**
	 * 将long类型数据以bson表现形式写入
	 * @param serializer json序列化器
	 * @param object 获取的数据对象
	 * @param fieldName 字段名称
	 * @param fieldType 字段类型
	 * @param features 特性集合
	 * */
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		// TODO Auto-generated method stub
		SerializeWriter out = serializer.out;
		if(object == null) {
			out.writeNull();
			return;
		}
		//String s = String.format("%s%s", object.toString(), "L");
		String string = String.format("{\"$numberLong\": \"%s\"}", object);
		out.write(string);
	}

}
