package com.postcenter.domain.model.post;

import java.util.Date;

public class PostMessage {

	private String text;
	private Date date;
	
	private PostMessage() {
		this.date = new Date();
	}

	public PostMessage(String text) {
		this();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

}
