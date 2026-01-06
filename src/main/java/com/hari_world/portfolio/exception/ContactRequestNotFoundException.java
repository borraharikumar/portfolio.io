package com.hari_world.portfolio.exception;

public class ContactRequestNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7643648577715449327L;
	
	public ContactRequestNotFoundException(String message) {
		super(message);
	}

}
