package com.hari_world.portfolio.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "POSTS_TAB")
public class Post {

	@Id
	@SequenceGenerator(name = "post_seq_gen", sequenceName = "post_seq", initialValue = 10000, allocationSize = 1)
	@GeneratedValue(generator = "post_seq_gen", strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String title;
	@Column(nullable = false, unique = true)
	private String slug;
	@Column(nullable = false)
	private String thumnailUrl;
	@Column(nullable = false)
	private String summary;
	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String content;
	@Column(nullable = false)
	private Boolean published;

	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}