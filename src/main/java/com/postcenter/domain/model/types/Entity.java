package com.postcenter.domain.model.types;

public abstract class Entity {
	private String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
	public abstract boolean isValid();
	
}
