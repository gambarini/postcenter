package com.postcenter.domain.model.authentication;


public interface IUserRepository {

	public abstract void store(User user);
	User findUserById(String id);
	void removeAll();
	public abstract User findUserByName(String name);

}