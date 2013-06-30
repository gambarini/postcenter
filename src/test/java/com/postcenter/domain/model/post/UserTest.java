package com.postcenter.domain.model.post;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;
import com.postcenter.infrastructure.persistence.mongodb.repositories.UserMongoRepository;

public class UserTest {

	public static final String HOST_URI = "localhost";
	public static final String DB_NAME = "testpostcenter";
	public static IUserRepository userRepo = null;
	
	@BeforeClass
	public static void startup() {
		userRepo = new UserMongoRepository(HOST_URI, DB_NAME);
	}	
	
	@After
	public void teardownTest() {
		userRepo.removeAll();
	}
	
	@Test
	public void testCreateUser() {
		
		User user = new User("Nameless One", "Nameless@mail.com");
		
		userRepo.store(user);
		
		Assert.assertNotNull(user.get_id());
		
	}
	
	@Test
	public void testfindUser() {
		
		User user = new User("nameless One", "Nameless@email.com");
		
		userRepo.store(user);
		
		User foundUser = userRepo.findUserById(user.get_id());
		
		Assert.assertEquals(user.get_id(), foundUser.get_id());
		Assert.assertEquals(user.getName(), foundUser.getName());
		
	}
	
	@Test
	public void testFindUserByName() {
		User user = new User("nameless One", "Nameless@email.com");
		
		userRepo.store(user);
		
		User foundUser = userRepo.findUserByName(user.getName());
		
		Assert.assertEquals(user.get_id(), foundUser.get_id());
		Assert.assertEquals(user.getName(), foundUser.getName());
	}
	
	@Test
	public void testIsValid() {
		
		User validUser = new User("nameless One", "Nameless@email.com");
		User invalidUser = new User("", "");
		
		Assert.assertEquals(true, validUser.isValid());
		Assert.assertEquals(false, invalidUser.isValid());
	}

}
