package com.postcenter.interfaces.rest;

import java.util.List;

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
import com.postcenter.domain.model.post.PostMessage;
import com.postcenter.domain.model.post.ReplyMessage;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;
import com.postcenter.interfaces.rest.dto.PostDTO;
import com.postcenter.interfaces.rest.dto.ReplyDTO;
import com.postcenter.interfaces.rest.facade.PostFacade;
import com.postcenter.interfaces.rest.interceptors.Authenticate;

@Path("/")
public class PostService {

	@Inject
	private IPostRepository postRepository;
	
	@Inject
	private IUserRepository userRepository;


	@GET
	@Path("/post")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopPosts(@DefaultValue("5") @QueryParam("top") int topQuantity) {
		
		List<PostDTO> postsDTO = PostFacade.toPostDTO(postRepository.findTopPosts(topQuantity), userRepository);
		
		return Response.status(Status.OK).entity(postsDTO).build();
	}

	@GET
	@Path("/post/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPost(@PathParam("id") String id) {

		Post post = postRepository.findPostById(id);
		PostDTO postDTO = PostFacade.toPostDTO(post, userRepository);
		
		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.status(Status.OK).entity(postDTO).build();
	}


	@DELETE
	@Path("/post/{id}")
	@Authenticate
	public Response deletePost(@PathParam("id") String id) {

		Post post = postRepository.findPostById(id);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		postRepository.remove(post);

		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/post/count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response totalPosts() {

		long totalPosts = postRepository.findPostsTotal();

		return Response.status(Status.OK).entity(totalPosts).build();

	}
	
	@POST
	@Path("/user/{id}/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticate
	public Response addPost(@PathParam("id") String userId, PostDTO postDTO) {

		if (postDTO == null)
			return Response.status(Status.BAD_REQUEST).build();

		User user = userRepository.findUserById(userId);

		if (user == null)
			return Response.status(Status.FORBIDDEN).build();

		PostMessage postMessage = Post.createPostMessage(postDTO.getMessage());
		Post post = Post.createPost(postDTO.getTitle(), userId, postMessage);

		if (!post.isValid())
			return Response.status(Status.BAD_REQUEST).build();

		postRepository.store(post);

		return Response.status(Status.CREATED).entity(post).build();
	}
	
	@POST
	@Path("/user/{userId}/post/{postId}/reply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticate
	public Response replyPost(@PathParam("userId") String userId, @PathParam("postId") String postId, ReplyDTO replyDTO) {

		User user = userRepository.findUserById(userId);

		if (user == null)
			return Response.status(Status.FORBIDDEN).build();

		Post post = postRepository.findPostById(postId);

		if (post == null)
			return Response.status(Status.NOT_FOUND).build();

		if (replyDTO == null)
			return Response.status(Status.BAD_REQUEST).build();
		ReplyMessage replyMessage = Post.createReplyMessage(user, replyDTO.getMessage());
		post.reply(replyMessage);

		postRepository.store(post);

		return Response.status(Status.CREATED).entity(replyMessage).build();
	}


}
