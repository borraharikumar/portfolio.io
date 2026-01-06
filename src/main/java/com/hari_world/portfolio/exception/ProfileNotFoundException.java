package com.hari_world.portfolio.exception;

public class ProfileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5698701585215795347L;

	public ProfileNotFoundException(String message) {
		super(message);
	}

}