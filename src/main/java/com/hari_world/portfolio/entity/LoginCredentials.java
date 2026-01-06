package com.hari_world.portfolio.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "LOGIN_CREDENTIALS_TAB")
public class LoginCredentials {

	@Id
	@SequenceGenerator(name = "login_creds_seq_gen", sequenceName = "login_creds_seq", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "login_creds_seq_gen", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false)
	private Boolean isEnabled;

	// 🔗 Owning side
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", referencedColumnName = "id", unique = true)
	private Profile profile;

}
// MD2A11CX9SCK63986