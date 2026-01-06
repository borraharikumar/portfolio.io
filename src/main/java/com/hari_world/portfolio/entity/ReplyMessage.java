package com.hari_world.portfolio.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "REPLY_MESSAGES_TAB")
public class ReplyMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_message_id", nullable = false)
	private ContactMessage contactMessage;

	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String content;

	@CreationTimestamp
	private LocalDateTime repliedAt;

}