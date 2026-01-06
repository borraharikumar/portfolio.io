package com.hari_world.portfolio.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2231677517568620255L;
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
