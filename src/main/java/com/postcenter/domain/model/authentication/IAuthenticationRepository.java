package com.postcenter.domain.model.authentication;

public interface IAuthenticationRepository {
	public abstract void store(Authentication authentication);

	public abstract Authentication findAuthentication(String password, String email);

	public abstract Authentication findAuthenticationByToken(String email, String token);

	public abstract void removeAll();
}
