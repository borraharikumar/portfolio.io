package com.hari_world.portfolio.exception;

public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2107153607475149092L;
	
	public PostNotFoundException(String message) {
		super(message);
	}

}
