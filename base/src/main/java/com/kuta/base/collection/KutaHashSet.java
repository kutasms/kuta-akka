package com.kuta.base.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 对HashSet的进一步包装，如通过pop方法从哈希集合中取出一个对象，并且从集合中删除
 * */
public class KutaHashSet<T> extends HashSet<T> {

	private static final long serialVersionUID = -6904231798482837118L;
	
	/**
	 * 获取指定个数的{@code Set<T>}副本,并且从本Set中删除
	 * */
	public Set<T> pop(int count){
		if(count>0 && this.size() > 0) {
			Set<T> set = new HashSet<>();
			Iterator<T> iterator = this.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				if(i == count) {
					break;
				}
				T t = iterator.next();
				set.add(t);
				i++;
			}
			this.removeAll(set);
			return set;
		}
		return null;
	}
	
}
