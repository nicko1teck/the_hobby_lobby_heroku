package com.o1teck.exceptions;

public class ImageTooSmallException extends Exception {
	
	private String message = "Is this the message you're looking for?";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ImageTooSmallException(String message) {
		super();
		this.message = message;
	}

	public ImageTooSmallException(){
		
	}
}
