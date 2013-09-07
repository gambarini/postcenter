package com.postcenter.domain.model.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.postcenter.infrastructure.persistence.mongodb.repositories.UserMongoRepository;
import com.postcenter.infrastructure.persistence.mongodb.validator.UserPersistenceValidator;

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
	public void testFindUserByEmail(){
		User user = new User("nameless One", "Nameless@email.com");
		
		userRepo.store(user);
		
		User foundUser = userRepo.findUserByEmail(user.getEmail());
		
		Assert.assertEquals(user.get_id(), foundUser.get_id());
		Assert.assertEquals(user.getName(), foundUser.getName());

	}
	
	@Test
	public void testIsValid() {
		
		User validUser = new User("nameless One", "Nameless@email.com");
		User invalidUser = new User("", "");
		
		Assert.assertEquals(true, validUser.isValid(new UserPersistenceValidator(userRepo)));
		Assert.assertEquals(false, invalidUser.isValid(new UserPersistenceValidator(userRepo)));
	}
	
	@Test
	public void testIsValidSameEmail() {
		
		User user = new User("nameless One", "Nameless@email.com");
		userRepo.store(user);
		
		User invalidUser = new User("nameless Two", "Nameless@email.com");
		
		Assert.assertEquals(false, invalidUser.isValid(new UserPersistenceValidator(userRepo)));
	}

}
