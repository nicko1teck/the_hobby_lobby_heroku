package com.o1teck.model.dto;

import java.util.Date;

import com.o1teck.model.entity.Message;

public class SimpleMessage {
	
	private Long id;
	private String from;
	private String text;
	private Date sent;
	private Long fromUserID;
	private Boolean isReply;
	
	
	public SimpleMessage() {
	}
	
public SimpleMessage(Message m, Boolean isReply) {
		
		this.from = m.getFromUser().getFirstname() + " " + m.getFromUser().getSurname();
		this.text = m.getText();
		this.sent = m.getSent();
		this.fromUserID = m.getFromUser().getId();
		this.isReply = isReply;
		this.id = m.getId();
	}


	public SimpleMessage(String text){
		this.text = text;
		this.sent = new Date();
	}


	public SimpleMessage(String from, String text, Date sent, Long fromUserID, Boolean isReply) {
		super();
		this.from = from;
		this.text = text;
		this.sent = sent;
		this.fromUserID = fromUserID;
		this.isReply = isReply;
	}

	
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	
	public void setFromUserID(Long fromUserID) {
		this.fromUserID = fromUserID;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Date getSent() {
		return sent;
	}


	public void setSent(Date sent) {
		this.sent = sent;
	}


	public Long getFromUserID() {
		return fromUserID;
	}


	public void setFromUserId(Long fromUserID) {
		this.fromUserID = fromUserID;
	}


	public Boolean getIsReply() {
		return isReply;
	}


	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}
	
	
	@Override
	public String toString() {
		return "SimpleMessage [from=" + from + ", text=" + text + ", sent=" + sent + ", fromUserId=" + fromUserID
				+ ", isReply=" + isReply + "]";
	}
}
