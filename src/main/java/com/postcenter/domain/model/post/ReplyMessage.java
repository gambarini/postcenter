package com.postcenter.domain.model.post;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.postcenter.domain.model.types.Entity;
import com.postcenter.domain.model.user.User;

public class ReplyMessage extends Entity {

	@JsonIgnore
	private User user;
	private String userId;

	private String text;
	private Date date;

	private String postId;

	private ReplyMessage() {

	}

	private ReplyMessage(User user, String text, Date date) {

		this.userId = user.get_id();
		this.user = user;
		this.text = text;
		this.date = date;
	}

	public User getUser() {
		return user;
	}
	

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public static ReplyMessage createReplyMessage(User user, String text, Date date) {
		return new ReplyMessage(user, text, date);
	}

}
