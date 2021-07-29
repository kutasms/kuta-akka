package com.kuta.base.collection;

public class KutaTuple<T1,T2> {
	private T1 t1;
	private T2 t2;
	
	
	/**
	 * @param t1
	 * @param t2
	 */
	public KutaTuple(T1 t1, T2 t2) {
		super();
		this.t1 = t1;
		this.t2 = t2;
	}
	public T1 getT1() {
		return t1;
	}
	public void setT1(T1 t1) {
		this.t1 = t1;
	}
	public T2 getT2() {
		return t2;
	}
	public void setT2(T2 t2) {
		this.t2 = t2;
	}
}
