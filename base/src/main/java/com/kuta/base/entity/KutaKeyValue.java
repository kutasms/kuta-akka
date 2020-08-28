package com.kuta.base.entity;

/**
 * KSF键值对
 * */
public class KutaKeyValue<K,V> {
	/**
	 * 键
	 * */
	private K key;
	/**
	 * 值
	 * */
	private V value;
	/**
	 * 获取键
	 * @return 键
	 * */
	public K getKey() {
		return key;
	}
	/**
	 * 设置键
	 * @param 键
	 * */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * 获取值
	 * @return 值
	 * */
	public V getValue() {
		return value;
	}
	/**
	 * 设置值
	 * @param 值
	 * */
	public void setValue(V value) {
		this.value = value;
	}
	/**
	 * 创建键值对
	 * @param k 键
	 * @param v 值
	 * @return 键值对对象
	 * */
	public static <K,V> KutaKeyValue<K, V> create(K k, V v){
		KutaKeyValue<K,V> entity = new KutaKeyValue<>();
		entity.key = k;
		entity.value = v;
		return entity;
	}
}
