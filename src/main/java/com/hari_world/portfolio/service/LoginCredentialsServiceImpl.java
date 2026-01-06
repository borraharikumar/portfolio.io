package com.hari_world.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.dto.ChangePasswordRequest;
import com.hari_world.portfolio.entity.LoginCredentials;
import com.hari_world.portfolio.entity.Profile;
import com.hari_world.portfolio.entity.ResetPasswordToken;
import com.hari_world.portfolio.exception.UserNotFoundException;
import com.hari_world.portfolio.reposirory.LoginCredentialsRepository;
import com.hari_world.portfolio.reposirory.ResetPasswordTokenRepository;
import com.hari_world.portfolio.util.EmailUtil;

import jakarta.transaction.Transactional;

@Service
public class LoginCredentialsServiceImpl implements ILoginCredentialsService {

	@Autowired
	private LoginCredentialsRepository credentialsRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ResetPasswordTokenRepository passwordTokenRepository;
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public LoginCredentials saveCredentials(LoginCredentials credentials) {
		String rawPassword = credentials.getPassword();
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		emailUtil.sendAdminAdditionConfirmationMail(credentials.getProfile().getName(), credentials.getUsername(),
				rawPassword, credentials.getRole(), credentials.getProfile().getEmail());
		credentials = credentialsRepository.save(credentials);
		return credentials;
	}

	private LoginCredentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with username '" + username + "'"));
	}

	@Override
	public LoginCredentials getCredentials(Integer id) {
		return credentialsRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with ID '" + id + "'"));
	}

	@Override
	@Transactional
	public Boolean changePassword(ChangePasswordRequest cpRequest) {
		String currentPassword = getCredentials(cpRequest.getUsername()).getPassword();
		if (passwordEncoder.matches(cpRequest.getOldPassword(), currentPassword)) {
			Integer count = credentialsRepository.changePassword(cpRequest.getUsername(),
					passwordEncoder.encode(cpRequest.getNewPassword()));
			if (count != 0) {
				sendPasswordChangeSuccessMail(getCredentials(cpRequest.getUsername()).getProfile());
				return true;
			}
		}
		return false;
	}

	private void sendPasswordChangeSuccessMail(Profile profile) {
		emailUtil.sendPasswordChangeSuccessMail(profile);
	}

	@Override
	public Boolean sendResetPasswordLink(String username) {
		Optional<LoginCredentials> obj = credentialsRepository.findByUsername(username);
		if (obj.isPresent()) {
			LoginCredentials credentials = obj.get();
			Profile profile = credentials.getProfile();
			String email = profile.getEmail();
			String name = profile.getName();
			String token = UUID.randomUUID().toString();

			ResetPasswordToken passwordToken = new ResetPasswordToken();
			passwordToken.setToken(token);
			passwordToken.setLoginCredentials(credentials);
			passwordToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));
			passwordToken.setIsUsed(false);
			passwordTokenRepository.save(passwordToken);

			emailUtil.sendResetPasswordLink(email, name, token);
			return true;
		}
		return false;
	}

	private Boolean isValidResetPasswordToken(ResetPasswordToken passwordToken) {
		return !passwordToken.getIsUsed() && passwordToken.getExpiryTime().isAfter(LocalDateTime.now());
	}

	@Override
	public Boolean validResetPasswordToken(String token) {
		Optional<ResetPasswordToken> obj = passwordTokenRepository.findByToken(token);
		if (obj.isPresent())
			return isValidResetPasswordToken(obj.get());
		return false;
	}

	@Override
	public Boolean resetPassword(String token, String password) {
		Optional<ResetPasswordToken> obj = passwordTokenRepository.findByToken(token);
		if (obj.isPresent()) {
			ResetPasswordToken passwordToken = obj.get();
			if (isValidResetPasswordToken(passwordToken)) {
				passwordToken.setIsUsed(true);
				passwordToken.getLoginCredentials().setPassword(passwordEncoder.encode(password));
				passwordTokenRepository.save(passwordToken);
				sendPasswordChangeSuccessMail(passwordToken.getLoginCredentials().getProfile());
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer getAdminsCount() {
		return credentialsRepository.countByRole("ADMIN");
	}

	@Override
	public List<LoginCredentials> getAdminsList() {
		return credentialsRepository.findByRole("ADMIN");
	}

	@Override
	public void disableAdmin(Integer id) {
		LoginCredentials credentials = getCredentials(id);
		credentials.setIsEnabled(false);
		credentialsRepository.save(credentials);
	}

	@Override
	public void enableAdmin(Integer id) {
		LoginCredentials credentials = getCredentials(id);
		credentials.setIsEnabled(true);
		credentialsRepository.save(credentials);
	}

	@Override
	public void deleteAdmin(Integer id) {
		credentialsRepository.deleteById(id);
	}

	@Override
	public void updateCredentials(LoginCredentials credentials) {
		credentialsRepository.save(credentials);
	}

}