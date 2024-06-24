package com.o1teck.model.dto;

public class ChatRequest {
	
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ChatRequest(int page) {
		super();
		this.page = page;
	}

	public ChatRequest() {
		super();
	}
}
