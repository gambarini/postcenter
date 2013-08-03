package com.postcenter.infrastructure.persistence.mongodb.repositories;

import org.jongo.MongoCollection;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;
import com.postcenter.domain.model.user.User;

public class AuthenticationMongoRepository extends GenericMongoRepository implements IAuthenticationRepository{


	protected MongoCollection userCollection;
	protected MongoCollection authenticationCollection;

	public AuthenticationMongoRepository() {
		super();
	}

	public AuthenticationMongoRepository(String hostUri, String dbName) {
		super(hostUri, dbName);
	}

	@Override
	protected void loadCollections() {
		userCollection = jongo.getCollection(User.class.getSimpleName());
		authenticationCollection = jongo.getCollection(Authentication.class.getSimpleName());
	}

	@Override
	public void store(Authentication authentication) {
		authenticationCollection.save(authentication);		
	}

	@Override
	public Authentication findAuthentication(String password, String email){
		
		User user = fetchUserByEmail(email);
		
		Authentication authentication = null;
		if (user != null) 
			authentication =  this.authenticationCollection.findOne(createFilter("userId", user.get_id() , "password", password)).as(Authentication.class);
			
		
		if (authentication == null) return null;
		else authentication.setUser(user.get_id());
		
		return authentication;
	}

	protected User fetchUserByEmail(String email) {
		User user = userCollection.findOne(createFilter("email", email)).as(User.class);
		return user;
	}

	@Override
	public Authentication findAuthenticationByToken(String email, String token) {
		
		User user = fetchUserByEmail(email);
		
		Authentication authentication = null;
		if (user != null) 
			authentication =  this.authenticationCollection.findOne(createFilter("userId", user.get_id() , "token", token)).as(Authentication.class);
			
		
		if (authentication == null) return null;
		else authentication.setUser(user.get_id());
		
		return authentication;
		
	}
	
	@Override
	public void removeAll() {
		authenticationCollection.remove();
	}

}
