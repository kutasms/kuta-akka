package com.kuta.akka.base.entity;

public class DataRedoRequest<T> {
	private T entity;

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
}
