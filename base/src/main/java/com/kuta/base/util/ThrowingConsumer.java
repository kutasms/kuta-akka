package com.kuta.base.util;

/**
 * 可抛出异常的函数式消费器
 * @param <T> 传入对象泛型
 * @param <E> 消费器内部抛出异常泛型
 * */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
	/**
	 * 调用消费器兵返回结果
	 * @param <T> 传入对象泛型
	 * @param <E> 消费器内部抛出异常泛型
	 * @param t 传入对象
	 * @throws E 消费器内部抛出的异常
	 * */
	void accept(T t) throws E;
}