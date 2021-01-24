package com.kuta.base.collection;

import java.util.TreeMap;

public class KutaSortableMap<K, V> extends TreeMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6489986983434381140L;

	
	public void moveDown(K k) {
		V v = this.get(k);
		K higherKey = this.higherKey(k);
		if(higherKey == null) {
			return;
		}
		V higherVal = this.get(higherKey);
		this.replace(k, higherVal);
		this.replace(higherKey, v);
	}

	public void moveUp(K k) {
		if(this.size() < 2) {
			return;
		}
		V v = this.get(k);
		K lowerKey = this.lowerKey(k);
		if(lowerKey == null) {
			return;
		}
		V lowerVal = this.get(lowerKey);
		this.replace(k, lowerVal);
		this.replace(lowerKey, v);
	}
}
