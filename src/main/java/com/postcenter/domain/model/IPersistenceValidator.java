package com.postcenter.domain.model;

public interface IPersistenceValidator<T> {
	
	abstract boolean isPersistenceValid(T entity);
}
