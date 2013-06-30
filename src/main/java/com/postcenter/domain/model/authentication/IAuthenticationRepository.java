package com.postcenter.domain.model.authentication;

public interface IAuthenticationRepository {
	void store(Authentication authentication);
	Authentication findAuthtentication(String password, String email);
	void removeAll();
}
