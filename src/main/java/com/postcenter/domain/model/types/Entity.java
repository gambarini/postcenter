package com.postcenter.domain.model.types;

import com.postcenter.domain.model.IPersistenceValidator;

public abstract class Entity<T> {
	private String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public abstract boolean isValid(IPersistenceValidator<T> validator);


	
}
