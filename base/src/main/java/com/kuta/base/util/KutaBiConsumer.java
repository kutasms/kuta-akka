package com.kuta.base.util;

/**
 * A functional consumer that can throw exceptions
 * @param <T> Incoming object generics
 * @param <E> Throw exception generics
 * */
@FunctionalInterface
public interface KutaBiConsumer<T,U, E extends Exception> {
	/**
	 * Call the consumer and return the result
	 * @param <T> Incoming object generics
	 * @param <E> Throw exception generics
	 * @param t Incoming object
	 * @throws E Exception thrown
	 * */
	void accept(T t, U u) throws E;
}
