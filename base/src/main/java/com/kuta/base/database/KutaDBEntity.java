package com.kuta.base.database;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据实体基类
 * */
public class KutaDBEntity extends Number implements Serializable {

	/**
	 * 序列化ID
	 * */
	private static final long serialVersionUID = 7915863703084702806L;
	
	/**
	 * 缓存field
	 * */
	private Number cacheField;

	/**
	 * 获取缓存键
	 * @return 缓存field
	 * */
	public Number getCacheField() {
		return cacheField;
	}

	/**
	 * 设置缓存field
	 * @param field 缓存field
	 * */
	public void setCacheField(Number field) {
		this.cacheField = field;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return cacheField.intValue();
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return cacheField.longValue();
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return cacheField.floatValue();
	}

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return cacheField.doubleValue();
	}
	
	/**
	 *	将对象转换为json格式
	 * */
	public Object toJSON() {
		return JSONObject.toJSON(this);
	}
}
