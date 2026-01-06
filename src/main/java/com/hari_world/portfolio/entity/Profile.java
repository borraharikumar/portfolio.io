package com.hari_world.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PROFILE_TAB")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String tagline;

	@Column(columnDefinition = "LONGTEXT")
	private String bio;

	@Column(nullable = false, unique = true)
	private String mobile;

	@Column(nullable = false, unique = true)
	private String email;

	private String linkedin;
	private String instagram;
	private String twitter;

	private String profilePicUrl;

}