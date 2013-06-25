package com.postcenter.infrastructure.persistence.mongodb.repositories;

import org.jongo.MongoCollection;

import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;

public class UserMongoRepository extends GenericMongoRepository implements IUserRepository {

	protected MongoCollection userCollection;

	public UserMongoRepository() {
		super();
	}

	public UserMongoRepository(String hostUri, String dbName) {
		super(hostUri, dbName);
	}

	@Override
	protected void loadCollections() {
		userCollection = jongo.getCollection(User.class.getSimpleName());
	}

	@Override
	public void store(User user) {
		userCollection.save(user);
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

}
