package com.postcenter.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

@Path("/post")
public class PostService {

	@Inject
	private IPostRepository postRepository;
	
	@Inject
	private IUserRepository userRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopPosts(@DefaultValue("5") @QueryParam("top") int topQuantity) {
		return Response.status(Status.OK).entity(postRepository.findTopPosts(topQuantity)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPost(@PathParam("id") String id) {

		Post post = postRepository.findPostById(id);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.status(Status.OK).entity(post).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPost(Post post) {

		if (post == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = userRepository.findUserById(post.getUserId());
		
		if (user == null){
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (!post.isValid()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		postRepository.store(post);
		
		return Response.status(Status.CREATED).entity(post).build();
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response replyPost(@PathParam("id") String id, ReplyMessage reply) {

		Post post = postRepository.findPostById(id);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		if (reply == null)
			return Response.status(Status.BAD_REQUEST).build();

		post.reply(reply);

		postRepository.store(post);

		return Response.status(Status.CREATED).entity(reply).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePost(@PathParam("id") String id) {

		Post post = postRepository.findPostById(id);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		postRepository.remove(post);
		
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response totalPosts(){
		
		long totalPosts = postRepository.findPostsTotal();

		return Response.status(Status.OK).entity(totalPosts).build();
		
	}

}
