package com.hari_world.portfolio.service;

import java.util.List;

import com.hari_world.portfolio.dto.ChangePasswordRequest;
import com.hari_world.portfolio.entity.LoginCredentials;

public interface ILoginCredentialsService {
	
	public LoginCredentials saveCredentials(LoginCredentials credentials);
	public Boolean changePassword(ChangePasswordRequest changePasswordRequest);
	public Boolean sendResetPasswordLink(String username);
	public Boolean resetPassword(String token, String password);
	public Boolean validResetPasswordToken(String token);
	public Integer getAdminsCount();
	public List<LoginCredentials> getAdminsList();
	public void disableAdmin(Integer id);
	public void enableAdmin(Integer id);
	public void deleteAdmin(Integer id);
	public LoginCredentials getCredentials(Integer id);
	public void updateCredentials(LoginCredentials credentials);

}
