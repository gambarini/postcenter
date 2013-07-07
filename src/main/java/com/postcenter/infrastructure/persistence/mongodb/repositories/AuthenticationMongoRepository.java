package com.postcenter.infrastructure.persistence.mongodb.repositories;

import org.jongo.MongoCollection;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;
import com.postcenter.domain.model.authentication.User;

public class AuthenticationMongoRepository extends GenericMongoRepository implements IAuthenticationRepository{

	protected MongoCollection userCollection;
	protected MongoCollection authtenticationCollection;

	public AuthenticationMongoRepository() {
		super();
	}

	public AuthenticationMongoRepository(String hostUri, String dbName) {
		super(hostUri, dbName);
	}

	@Override
	protected void loadCollections() {
		userCollection = jongo.getCollection(User.class.getSimpleName());
		authtenticationCollection = jongo.getCollection(Authentication.class.getSimpleName());
	}

	@Override
	public void store(Authentication authentication) {
		userCollection.save(authentication.getUser());
		authentication.setUser(authentication.getUser()); //just update the user id in the authentication object
		authtenticationCollection.save(authentication);		
	}

	@Override
	public Authentication findAuthtentication(String password, String email){
		
		Authentication authentication =  this.authtenticationCollection.findOne(createFilter("password", password)).as(Authentication.class);
		
		User user = null;
		if (authentication != null) 
			user = userCollection.findOne(createFilter("_id", authentication.getUserId(), "email", email)).as(User.class);
		
		if (user == null) return null;
		else authentication.setUser(user);
		
		return authentication;
	}

	@Override
	public void removeAll() {
		authtenticationCollection.remove();
	}

}
