package com.postcenter.interfaces.rest.service;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;

@Path("/authentication")
public class AuthenticationService {

	@Inject
	private IAuthenticationRepository authenticationRepository;

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response authentication(@FormParam("email") @DefaultValue("") String email, @FormParam("password") @DefaultValue("") String password) {

		Authentication auth = authenticationRepository.findAuthentication(password, email);

		if (auth == null)
			return Response.status(Status.FORBIDDEN).build();

		String token = auth.authenticate();

		authenticationRepository.store(auth);

		return Response.status(Status.CREATED).cookie(new NewCookie(Authentication.COOKIE_EMAIL, email), new NewCookie(Authentication.COOKIE_TOKEN, token)).entity(token).build();
	}

	@DELETE
	public Response authenticationDelete(@CookieParam("email") @DefaultValue("") String email, @CookieParam("token") @DefaultValue("") String token) {
		
		Authentication authentication = authenticationRepository.findAuthenticationByToken(email, token);
		
		if (authentication == null) return Response.status(Status.NOT_FOUND).build();
		
		authentication.expireToken();
		
		authenticationRepository.store(authentication);
		
		return Response.status(Status.NO_CONTENT).cookie(new NewCookie(Authentication.COOKIE_EMAIL, null), new NewCookie(Authentication.COOKIE_TOKEN, null)).build();
	}

}
