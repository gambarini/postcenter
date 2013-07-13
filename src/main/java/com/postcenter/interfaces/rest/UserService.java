package com.postcenter.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.postcenter.domain.model.authentication.IUserRepository;
import com.postcenter.domain.model.authentication.User;

@Path("/user")
public class UserService {
	
	@Inject
	private IUserRepository userRepository;
	
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name") String name){
		
		User user = userRepository.findUserByName(name);
		
		return Response.status(Status.OK).entity(user).build();
	}

}
