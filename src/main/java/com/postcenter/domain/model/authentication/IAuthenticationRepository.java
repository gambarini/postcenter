package com.postcenter.domain.model.authentication;

public interface IAuthenticationRepository {
	void store(Authentication authentication);
	Authentication findAuthentication(String password, String email);
	Authentication findAuthenticationByToken(String email, String token);
	void removeAll();
}
