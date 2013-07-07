package com.postcenter.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.postcenter.domain.model.authentication.IUserRepository;
import com.postcenter.domain.model.authentication.User;
import com.postcenter.domain.model.post.IPostRepository;
import com.postcenter.domain.model.post.Post;
import com.postcenter.domain.model.post.ReplyMessage;

@Path("/user")
public class UserService {
	
	@Inject
	private IUserRepository userRepository;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User user){
		
		if (!user.isValid()) return Response.status(Status.BAD_REQUEST).build();
		
		userRepository.store(user);
		
		return Response.status(Status.CREATED).entity(user).build();
	}
	
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name") String name){
		
		User user = userRepository.findUserByName(name);
		
		return Response.status(Status.OK).entity(user).build();
	}

}
