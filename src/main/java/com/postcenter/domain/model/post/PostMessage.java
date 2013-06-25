package com.postcenter.domain.model.post;

import java.util.Date;

public class PostMessage {

	private String text;
	private Date date;
	
	private PostMessage() {
		this.date = new Date();
	}

	private PostMessage(String text) {
		this();
		this.text = text;
	}

	public static PostMessage createPostMessage(String text) {
		return new PostMessage(text);
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

}
