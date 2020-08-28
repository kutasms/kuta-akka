package com.kuta.base.entity;

import com.kuta.base.collection.KSFHashSet;

/**
 * REDIS扫描结果
 * */
public class KSFScanResult {
	/**
	 * 游标值
	 * */
	private String cursor;
	/**
	 * set集合
	 * */
	private KSFHashSet<String> set;
	
	/**
	 * 获取游标值
	 * @return 游标值
	 * */
	public String getCursor() {
		return cursor;
	}
	/**
	 * 设置游标值
	 * @param cursor 游标值
	 * */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
	/**
	 * 获取集合
	 * @return 集合
	 * */
	public KSFHashSet<String> getSet() {
		return set;
	}
	/**
	 * 设置集合
	 * @param 集合
	 * */
	public void setSet(KSFHashSet<String> set) {
		this.set = set;
	}
}
