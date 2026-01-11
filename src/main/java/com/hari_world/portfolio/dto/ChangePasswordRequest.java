package com.hari_world.portfolio.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

	private String username;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

}