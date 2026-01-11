package com.hari_world.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	private String[] publicUrls = { "/", "/posts", "/post/**", "/contact", "/about", "/login", "/forgot-password",
			"/reset-password/**", "/subscribe", "/unsubscribe" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.ignoringRequestMatchers("/admin/**"))
				.authorizeHttpRequests(req -> req.requestMatchers(publicUrls).permitAll().anyRequest().authenticated())
				.userDetailsService(userDetailsService)
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/admin/dashboard", true).permitAll())
				.exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
				.rememberMe(remember -> remember.key("secure-remember-me-key").tokenValiditySeconds(7 * 24 * 60 * 60))
				.sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(false));
		return security.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}