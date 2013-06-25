package com.postcenter.domain.model.user;

import com.postcenter.domain.model.types.Entity;

public class User extends Entity {

	private String name;
	private String email;

	private User() {
		
	}

	public User(String name, String email) {
		this();
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
