package com.postcenter.domain.model.authentication;

import java.util.Calendar;
import java.util.Date;

import com.postcenter.domain.model.types.Entity;

public class Authentication extends Entity {

	private String password;
	private String token;
	protected Date lastAuthentication;
	private String userId;
	public static final String COOKIE_EMAIL = "email";
	public static final String COOKIE_TOKEN = "token";

	protected Authentication() {

	}

	protected Authentication(String userId, String password) {
		this();
		this.password = password;
		this.userId = userId;
	}

	public static Authentication createAuthentication(String userId, String password) {
		return new Authentication(userId, password);
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

	public boolean isAuthenticated(String token) {
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

	public void setUser(String userId) {
		this.userId = userId;
	}

	@Override
	public boolean isValid() {

		return !(this.password.isEmpty() || this.userId.isEmpty());
	}

}
