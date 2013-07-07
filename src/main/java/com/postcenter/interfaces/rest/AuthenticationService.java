package com.postcenter.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;

@Path("/authentication")
public class AuthenticationService {

	@Inject
	private IAuthenticationRepository authenticationRepository;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentication(@FormParam("email") @DefaultValue("") String email, @FormParam("password") @DefaultValue("") String password) {

		Authentication auth = authenticationRepository.findAuthtentication(password, email);

		if (auth == null)
			return Response.status(Status.FORBIDDEN).build();

		return Response.status(Status.OK).entity(auth.getUser()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAuthentication(Authentication auth) {

		if (auth == null || !auth.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		
		authenticationRepository.store(auth);

		return Response.status(Status.CREATED).entity(auth.getUser()).build();
	}

}
