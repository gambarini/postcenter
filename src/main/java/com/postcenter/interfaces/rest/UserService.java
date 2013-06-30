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

import com.postcenter.domain.model.post.IPostRepository;
import com.postcenter.domain.model.post.Post;
import com.postcenter.domain.model.post.ReplyMessage;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;

@Path("/user")
public class UserService {

	@Inject
	private IPostRepository postRepository;
	
	@Inject
	private IUserRepository userRepository;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user){
		
		if (!user.isValid()) return Response.status(Status.BAD_REQUEST).build();
		
		userRepository.store(user);
		
		return Response.status(Status.CREATED).entity(user).build();
	}
	
	@POST
	@Path("{id}/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPost(@PathParam("id") String userId, Post post) {

		if (post == null)
			return Response.status(Status.BAD_REQUEST).build();

		User user = userRepository.findUserById(userId);

		if (user == null)
			return Response.status(Status.FORBIDDEN).build();

		if (!post.isValid())
			return Response.status(Status.BAD_REQUEST).build();

		postRepository.store(post);

		return Response.status(Status.CREATED).entity(post).build();
	}
	
	@POST
	@Path("{userId}/post/{postId}/reply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response replyPost(@PathParam("userId") String userId, @PathParam("postId") String postId, ReplyMessage reply) {

		User user = userRepository.findUserById(userId);

		if (user == null)
			return Response.status(Status.FORBIDDEN).build();

		Post post = postRepository.findPostById(postId);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		if (reply == null)
			return Response.status(Status.BAD_REQUEST).build();

		post.reply(reply);

		postRepository.store(post);

		return Response.status(Status.CREATED).entity(reply).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name") String name){
		
		User user = userRepository.findUserByName(name);
		
		return Response.status(Status.OK).entity(user).build();
	}

}
