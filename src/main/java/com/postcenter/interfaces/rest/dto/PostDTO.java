package com.postcenter.interfaces.rest.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PostDTO {
	
	private String id;
	private String title;
	private String message;
	private Date createDate;
	private String userName;
	private Collection<ReplyDTO> replys = new ArrayList<ReplyDTO>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Collection<ReplyDTO> getReplys() {
		return replys;
	}
	public void setReplys(Collection<ReplyDTO> replys) {
		this.replys = replys;
	}
	
	
	
}
