package com.postcenter.infrastructure.persistence.mongodb.repositories;

import org.jongo.MongoCollection;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;

public class UserMongoRepository extends GenericMongoRepository implements IUserRepository {


	protected MongoCollection userCollection;
	protected MongoCollection authtenticationCollection;

	public UserMongoRepository() {
		super();
	}

	public UserMongoRepository(String hostUri, String dbName) {
		super(hostUri, dbName);
	}

	@Override
	protected void loadCollections() {
		userCollection = jongo.getCollection(User.class.getSimpleName());
		authtenticationCollection = jongo.getCollection(Authentication.class.getSimpleName());
	}

	@Override
	public void store(User user) {
		userCollection.save(user);
	}
	
	@Override
	public void remove(User user) {
		userCollection.remove(user.get_id());
	}

	@Override
	public void removeAll() {
		userCollection.remove();
	}

	@Override
	public User findUserById(String id) {
		if (id == null) 
			return null;
		
		return userCollection.findOne(this.createFilter("_id", id)).as(User.class);
	}

	@Override
	public User findUserByName(String name) {
		if (name == null) 
			return null;
		
		return userCollection.findOne(this.createFilter("name", name)).as(User.class);
	}

	
	

}
