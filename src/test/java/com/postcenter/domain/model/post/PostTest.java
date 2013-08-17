package com.postcenter.domain.model.post;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;
import com.postcenter.infrastructure.persistence.mongodb.repositories.PostMongoRepository;
import com.postcenter.infrastructure.persistence.mongodb.repositories.UserMongoRepository;

public class PostTest {

	public static final String HOST_URI = "localhost";
	public static final String DB_NAME = "testpostcenter";
	public static IPostRepository postRepo = null;
	public static IUserRepository userRepo = null;

	@BeforeClass
	public static void startup() {
		postRepo = new PostMongoRepository(HOST_URI, DB_NAME);
		userRepo = new UserMongoRepository(HOST_URI, DB_NAME);
	}

	@After
	public void teardownTest() {
		postRepo.removeAll();
		userRepo.removeAll();
	}

	@Test
	public void testCreateFindPost() {

		User user1 = new User("Alfred", "alfred@email.com;");
		User user2 = new User("Antotonio", "ttoniho@email.com;");
		userRepo.store(user1);
		userRepo.store(user2);

		PostMessage message = Post.createPostMessage("Pergunta!");
		ReplyMessage messageReply1 = Post.createReplyMessage(user2, "Resposta!");
		ReplyMessage messageReply2 = Post.createReplyMessage(user1, "Duvida!");
		ReplyMessage messageReply3 = Post.createReplyMessage(user2, "Explicacao");

		Post newPost = Post.createPost("Tenho uma pergunta", user1.get_id(), message);
		postRepo.store(newPost);
		
		newPost.reply(messageReply1);
		newPost.reply(messageReply2);
		newPost.reply(messageReply3);
		postRepo.store(newPost);

		Post foundPost = postRepo.findPostById(newPost.get_id());

		Assert.assertNotNull(foundPost);
		Assert.assertNotNull(foundPost.get_id());
		Assert.assertEquals(user1.get_id(), foundPost.getUserId());
		Assert.assertEquals(3, foundPost.getReplys().size());

	}
	
	@Test
	public void testRemovePost() {
		User user1 = new User("Alfred", "alfred@email.com;");
		userRepo.store(user1);
		
		PostMessage message = Post.createPostMessage("Pergunta!");
		Post newPost = Post.createPost("Tenho uma pergunta", user1.get_id(), message);
		postRepo.store(newPost);
		
		Post foundPost = postRepo.findPostById(newPost.get_id());
		
		Assert.assertNotNull(foundPost);
		
		postRepo.remove(foundPost);
		
		Post deletedPost = postRepo.findPostById(foundPost.get_id());
		
		Assert.assertNull(deletedPost);
	}

	@Test
	public void testFindTopPosts() {
		User user = new User("Alfred", "alfred@email.com;");
		userRepo.store(user);
		
		PostMessage message1 = Post.createPostMessage("Pergunta!");
		Post newPost1 = Post.createPost("Tenho uma pergunta", user.get_id(), message1);
		postRepo.store(newPost1);
		
		PostMessage message2 = Post.createPostMessage("Pergunta!");
		Post newPost2 = Post.createPost("Tenho uma pergunta", user.get_id(), message2);
		postRepo.store(newPost2);
		
		PostMessage message3 = Post.createPostMessage("Pergunta!");
		Post newPost3 = Post.createPost("Tenho uma pergunta", user.get_id(), message3);
		postRepo.store(newPost3);
		
		PostMessage message4 = Post.createPostMessage("Pergunta!");
		Post newPost4 = Post.createPost("Tenho uma pergunta", user.get_id(), message4);
		postRepo.store(newPost4);
		
		PostMessage message5 = Post.createPostMessage("Pergunta!");
		Post newPost5 = Post.createPost("Tenho uma pergunta", user.get_id(), message5);
		postRepo.store(newPost5);
		
		PostMessage message6 = Post.createPostMessage("Pergunta!");
		Post newPost6 = Post.createPost("Tenho uma pergunta", user.get_id(), message6);
		postRepo.store(newPost6);
		
		Collection<Post> topPosts = postRepo.findTopPosts(5);
		
		Iterator<Post> it = topPosts.iterator();
		
		Post firstPost = it.next();
		Post secondPost = it.next();
		
		Date firstDate = firstPost.getMessage().getDate();
		Date secondDate = secondPost.getMessage().getDate();
		
		boolean isNewer = firstDate.after(secondDate) ? true : false; 
		
		Assert.assertEquals(5, topPosts.size());
		Assert.assertTrue(isNewer);
		Assert.assertNotNull(firstPost.getUserId());
	}

	@Test
	public void testFindPostNotExist() {

		Post post = postRepo.findPostById("1");

		Assert.assertNull(post);

	}
	
	@Test
	public void testIsValid() {
		User user = new User("User", "user@user.com");
		userRepo.store(user);
		
		Post validPost = Post.createPost("Valid Post", user.get_id(), Post.createPostMessage("Mensagem"));
		Post invalidPost1 = Post.createPost("", user.get_id(), Post.createPostMessage(""));
		Post invalidPost2 = Post.createPost("No User", null, Post.createPostMessage("Where is the User?"));
		
		Assert.assertEquals(true, validPost.isValid());
		Assert.assertEquals(false, invalidPost1.isValid());
		Assert.assertEquals(false, invalidPost2.isValid());
		
	}

	@Test
	public void testValidateRemoval() {
		User userValid = new User("User", "user@user.com");
		User userNotValid = new User("User", "user@user.com");
		userRepo.store(userValid);
		userRepo.store(userNotValid);
		
		Post post = Post.createPost("Valid Post", userValid.get_id(), Post.createPostMessage("Mensagem"));
		
		Assert.assertEquals(true, post.validateRemoval(userValid));
		Assert.assertEquals(false, post.validateRemoval(userNotValid));
	}

}
