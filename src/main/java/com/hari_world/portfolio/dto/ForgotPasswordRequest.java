package com.hari_world.portfolio.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
	
	private String username;
	private String password;

}