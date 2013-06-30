package com.postcenter.domain.model.post;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcenter.domain.model.types.Entity;
import com.postcenter.domain.model.user.User;

public class Post extends Entity {

	@JsonIgnore
	private User user;
	private String userId;

	private String title;
	private PostMessage message;

	@JsonIgnore
	private List<ReplyMessage> replys = new ArrayList<ReplyMessage>();

	private Post() {

	}

	private Post(String title, User user, PostMessage message) {
		this();

		this.title = title;
		this.message = message;
		
		if (user != null) {
			this.userId = user.get_id();
			this.user = user;
		}
	}

	public static Post createPost(String title, User user, PostMessage message) {
		return new Post(title, user, message);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PostMessage getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public boolean isValid() {
		return !(this.title.isEmpty() || this.message.getText().isEmpty() || this.userId == null);
	}

}
