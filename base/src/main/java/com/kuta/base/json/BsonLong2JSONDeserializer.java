package com.kuta.base.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.kuta.base.util.KutaUtil;

/**
 * Bson数据long类型解析器
 * */
public class BsonLong2JSONDeserializer implements ObjectDeserializer {

	/**
	 * 解析器
	 * */
	public static BsonLong2JSONDeserializer instance;
	
	/**
	 * 获取解析器
	 * @return 解析器
	 * */
	public static BsonLong2JSONDeserializer getInstance() {
		if(instance == null) {
			instance = new BsonLong2JSONDeserializer();	
		}
		return instance;
	}
	
	
	/**
	 * 解析操作
	 * @param parser 转换器
	 * @param type 数据类型
	 * @param fieldName 字段名称
	 * @return 获取的long类型数据值
	 * */
	@Override
	public Long deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		// TODO Auto-generated method stub
		return parser.getLexer().longValue();
	}

	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}

}
