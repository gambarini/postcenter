package com.postcenter.domain.model.post;

import java.util.Date;

import com.postcenter.domain.model.types.Entity;
import com.postcenter.domain.model.user.User;

public class ReplyMessage extends Entity {
	
	private String userId;
	private String text;
	private Date date;
	private String postId;

	private ReplyMessage() {

	}

	private ReplyMessage(String text, Date date, String userId) {

		this.userId = userId;
		this.text = text;
		this.date = date;
	}
	
	public void setUser(String userId) {
		this.userId = userId;
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
		return new ReplyMessage(text, date, user.get_id());
	}

	@Override
	public boolean isValid() {
		
		return false;
	}

}
