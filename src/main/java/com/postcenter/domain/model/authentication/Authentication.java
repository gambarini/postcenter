package com.postcenter.domain.model.authentication;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcenter.domain.model.types.Entity;

public class Authentication extends Entity {

	private String password;
	private String token;
	protected Date lastAuthentication;
	private String userId;

	@JsonIgnore
	private User user;

	protected Authentication() {

	}

	protected Authentication(User user, String password) {
		this();
		this.password = password;
		this.user = user;
		this.userId = user.get_id();
	}

	public static Authentication createAuthentication(User user, String password) {
		return new Authentication(user, password);
	}

	public String generateToken() {
		// TODO crypto token generator service
		this.token = String.valueOf((new Date()).getTime());
		return this.token;
	}

	public String authenticate() {
		if (this.userId == null)
			return "";

		updateLastAuthentication();
		return this.generateToken();
	}

	protected void updateLastAuthentication() {
		this.lastAuthentication = new Date();
	}

	public boolean isAuthtenticated(String token) {
		if (this.isLastAuthenticationExpired())
			return false;

		if (!this.token.equals(token))
			return false;

		this.updateLastAuthentication();
		return true;
	}

	public boolean isLastAuthenticationExpired() {
		if (this.lastAuthentication == null)
			return true;

		Calendar lastAuth = Calendar.getInstance();
		lastAuth.setTime(this.lastAuthentication);

		lastAuth.add(Calendar.MINUTE, expireMinutes());

		Date now = new Date();

		return (now.compareTo(lastAuth.getTime()) > 0);
	}

	protected int expireMinutes() {
		// TODO get minutes from a config file
		return 5;
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

		return !(this.password.isEmpty() || this.user.isValid());
	}

}
