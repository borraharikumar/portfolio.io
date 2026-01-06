package com.hari_world.portfolio.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CONTACT_MESSAGES_TAB")
public class ContactMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String message;

	@Column(nullable = false)
	private Boolean readStatus = false;

	@CreationTimestamp
	private LocalDateTime createdAt;

}