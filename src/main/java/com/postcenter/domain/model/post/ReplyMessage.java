package com.postcenter.domain.model.post;

import java.util.Date;

import com.postcenter.domain.model.IPersistenceValidator;
import com.postcenter.domain.model.types.Entity;

public class ReplyMessage extends Entity<ReplyMessage> {
	
	private String userId;
	private String text;
	private Date date;
	private String postId;

	private ReplyMessage() {
		this.date = new Date();
	}

	public ReplyMessage(String text, String userId) {
		this();
		this.userId = userId;
		this.text = text;
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

	@Override
	public boolean isValid(IPersistenceValidator<ReplyMessage> validator) {
		
		return false;
	}

}
