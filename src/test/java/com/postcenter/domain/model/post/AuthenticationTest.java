package com.postcenter.domain.model.post;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;
import com.postcenter.domain.model.authentication.IUserRepository;
import com.postcenter.domain.model.authentication.User;
import com.postcenter.infrastructure.persistence.mongodb.repositories.AuthenticationMongoRepository;
import com.postcenter.infrastructure.persistence.mongodb.repositories.UserMongoRepository;

public class AuthenticationTest {

	public static final String HOST_URI = "localhost";
	public static final String DB_NAME = "testpostcenter";
	public static IAuthenticationRepository authRepo = null;
	public static IUserRepository userRepo = null;

	@BeforeClass
	public static void startup() {
		authRepo = new AuthenticationMongoRepository(HOST_URI, DB_NAME);
		userRepo = new UserMongoRepository(HOST_URI, DB_NAME);
	}

	@After
	public void teardownTest() {
		authRepo.removeAll();
		userRepo.removeAll();
	}

	@Test
	public void testAuthenticaticateUser() {
		
		User user = new User("Nameless One", "nameless@mail.com");
		userRepo.store(user);
		
		Authentication authentication = Authentication.createAuthentication(user, "password");
		authRepo.store(authentication);
		
		Authentication authFound = authRepo.findAuthtentication("password", "nameless@mail.com");
		Authentication authNotFound1 = authRepo.findAuthtentication("pass", "nameless@mail.com");
		Authentication authNotFound2 = authRepo.findAuthtentication("password", "name@mail.com");
		
		Assert.assertEquals(user.getName(), authFound.getUser().getName());
		Assert.assertNull(authNotFound1);
		Assert.assertNull(authNotFound2);
		
	}
}
