package com.postcenter.domain.model.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcenter.domain.model.types.Entity;


public class Authentication extends Entity{
	
	private String password;
	private String userId;

	@JsonIgnore
	private User user;

	private Authentication() {
		
	}

	private Authentication(User user, String password) {
		this();
		this.password = password;
		this.user = user;
		this.userId = user.get_id();
	}

	public static Authentication createAuthentication(User user, String password) {
		return new Authentication(user, password);
	}

	public String getUserId() {
		return userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.userId = user.get_id();
		this.user = user;
	}

	@Override
	public boolean isValid() {
		
		return !(this.password.isEmpty() || this.userId.isEmpty());
	}
	
	
	
}
