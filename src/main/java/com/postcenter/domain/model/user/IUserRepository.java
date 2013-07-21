package com.postcenter.domain.model.user;



public interface IUserRepository {

	public abstract void store(User user);
	public abstract User findUserById(String id);
	public abstract void remove(User user);
	public abstract void removeAll();
	public abstract User findUserByName(String name);
	public abstract User findUserByEmail(String email);

}