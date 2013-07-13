package com.postcenter.domain.model.post;

import java.util.Calendar;
import java.util.Date;

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

	@Test
	public void testIsLastAuththenticationExpired() {

		class AuthenticationMock extends Authentication {

			private int expireMinutes = 0;

			public AuthenticationMock(User user, String password, int expireMinutes) {
				super(user, password);
				this.expireMinutes = expireMinutes;
			}

			public void setLastAuthentication(Date date) {
				this.lastAuthentication = date;
			}

			@Override
			protected int expireMinutes() {
				return this.expireMinutes;
			}
		}

		Calendar lastAuth = Calendar.getInstance();
		lastAuth.add(Calendar.MINUTE, -3);

		User user = new User("Nameless One", "nameless@mail.com");
		userRepo.store(user);
		
		AuthenticationMock authenticationExpired = new AuthenticationMock(user, "password", 2);
		authenticationExpired.setLastAuthentication(lastAuth.getTime());

		Assert.assertEquals(true, authenticationExpired.isLastAuthenticationExpired());

		AuthenticationMock authenticationOk = new AuthenticationMock(user, "password", 10);
		authenticationOk.authenticate();

		Assert.assertEquals(false, authenticationOk.isLastAuthenticationExpired());
	}

	@Test
	public void testIsAuthenticated() {
		User user = new User("Nameless One", "nameless@mail.com");
		userRepo.store(user);

		Authentication authentication = Authentication.createAuthentication(user, "password");

		String token = authentication.authenticate();

		authRepo.store(authentication);

		authentication = authRepo.findAuthtentication("password", "nameless@mail.com");

		Assert.assertEquals(true, authentication.isAuthtenticated(token));
	}
}
