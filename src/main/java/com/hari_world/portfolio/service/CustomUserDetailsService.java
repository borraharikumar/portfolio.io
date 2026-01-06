package com.hari_world.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.entity.LoginCredentials;
import com.hari_world.portfolio.exception.UserNotFoundException;
import com.hari_world.portfolio.reposirory.LoginCredentialsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginCredentialsRepository credentialsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginCredentials credentials = credentialsRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found with '" + username + "'"));
		return User.builder()
					.username(credentials.getUsername())
					.password(credentials.getPassword())
					.roles(credentials.getRole())
					.disabled(!credentials.getIsEnabled())
					.build();
	}

}
