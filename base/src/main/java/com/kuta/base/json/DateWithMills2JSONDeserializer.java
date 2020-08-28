package com.kuta.base.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * 带毫秒的日期JSON转换解析器 
 **/
public class DateWithMills2JSONDeserializer implements ObjectDeserializer {

	/**
	 * 单例解析器对象
	 * */
	private static DateWithMills2JSONDeserializer instance;
	
	/**
	 * 获取单例的实体对象
	 * @return 单例解析器对象
	 * */
	public static DateWithMills2JSONDeserializer getInstance() {
		if(instance == null) {
			instance = new DateWithMills2JSONDeserializer();	
		}
		return instance;
	}
	
	/**
	 * 日期格式化工具
	 * */
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	
	/**
	 * 解析日期
	 * @param parser 转换器
	 * @param type 数据类型
	 * @param fieldName 字段名称
	 * @return 日期
	 * */
	@Override
	public Date deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		// TODO Auto-generated method stub
		String str = parser.getLexer().stringVal();
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 未知用途的方法，暂时保持返回0即可
	 * */
	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}

}
