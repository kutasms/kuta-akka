package com.kuta.base.collection;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.kuta.base.util.KutaTimeUtil;

/**
 * 对HashMap的进一步包装，包括直接获取各种数据类型的值
 * */
public class KutaHashMap<K,V> extends HashMap<K, V> {

	private static final long serialVersionUID = -7378992047316048595L;
	
	/**
	 * 获取Long类型的值
	 * */
	public Long getLong(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return Long.valueOf(val.toString());
	}
	/**
	 * 获取Integer类型的值
	 * */
	public Integer getInteger(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return Integer.valueOf(val.toString());
	}
	/**
	 * 获取Boolean类型的值
	 * */
	public Boolean getBoolean(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return Boolean.parseBoolean(val.toString());
	}
	/**
	 * 获取Float类型的值
	 * */
	public Float getFloat(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return Float.parseFloat(val.toString());
	}
	/**
	 * 获取Double类型的值
	 * */
	public Double getDouble(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return Double.parseDouble(val.toString());
	}
	/**
	 * 获取BigDecimal类型的值
	 * */
	public BigDecimal getDecimal(K key) {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return new BigDecimal(val.toString());
	}
	/**
	 * 获取不带毫秒Date类型的值
	 * */
	public Date getDate(K key) throws ParseException {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return KutaTimeUtil.parse(val.toString());
	}
	/**
	 * 获取带毫秒Date类型的值
	 * */
	public Date getDateWithMill(K key) throws ParseException {
		Object val = this.get(key);
		if(val == null) {
			return null;
		}
		return KutaTimeUtil.parseWithMill(val.toString());
	}
	
}
