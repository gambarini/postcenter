package com.postcenter.domain.model.post;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcenter.domain.model.IPersistenceValidator;
import com.postcenter.domain.model.types.Entity;
import com.postcenter.domain.model.user.User;

public class Post extends Entity<Post> {

	private String userId;

	private String title;
	private PostMessage message;

	@JsonIgnore
	private List<ReplyMessage> replys = new ArrayList<ReplyMessage>();

	private Post() {

	}

	private Post(String title, String userId, PostMessage message) {
		this();

		this.title = title;
		this.message = message;
		this.userId = userId;
	}

	public static Post createPost(String title, String userId, PostMessage message) {
		return new Post(title, userId, message);
	}

	public void reply(ReplyMessage message) {
		message.setPostId(this.get_id());
		this.replys.add(message);
	}

	public List<ReplyMessage> getReplys() {
		return replys;
	}

	public String getUserId() {
		return userId;
	}

	public void setUser(String userId) {
		this.userId = userId;
	}

	public PostMessage getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public boolean isValid(IPersistenceValidator<Post> validator) {
		return !(this.title.isEmpty() || this.message.getText().isEmpty() || this.userId == null);
	}

	public boolean validateRemoval(User user) {
		return !(user == null || getUserId() != user.get_id());
	}

	public static PostMessage createPostMessage(String text) {
		return new PostMessage(text);
	}

	public static ReplyMessage createReplyMessage(User user, String text) {
		return new ReplyMessage(text, user.get_id());
	}


}




