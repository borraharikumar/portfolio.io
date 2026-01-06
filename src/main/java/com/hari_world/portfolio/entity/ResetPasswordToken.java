package com.hari_world.portfolio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RESET_PASSWORD_TOKEN_TAB")
public class ResetPasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "login_id", nullable = false)
	private LoginCredentials loginCredentials;

	@Column(nullable = false)
	private LocalDateTime expiryTime;

	@Column(nullable = false)
	private Boolean isUsed;

}