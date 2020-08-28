package com.kuta.base.util;

/**
 * 可抛出异常的函数式执行器
 * @param <T> 传入对象泛型
 * @param <R> 返回结果泛型
 * @param <E> 执行器内部抛出异常泛型
 * */
@FunctionalInterface
public interface ThrowingFunction<T,R, E extends Exception> {
	/**
	 * 调用执行器兵返回结果
	 * @param <T> 传入对象泛型
	 * @param <R> 返回结果泛型
	 * @param <E> 执行器内部抛出异常泛型
	 * @param t 传入对象
	 * @return 返回的结果
	 * @throws E 执行器内部抛出的异常
	 * */
	R apply(T t) throws E;
}
