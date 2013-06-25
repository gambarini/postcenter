package com.postcenter.domain.model.user;


public interface IUserRepository {

	public abstract void store(User user);
	User findUserById(String id);
	void removeAll();

}