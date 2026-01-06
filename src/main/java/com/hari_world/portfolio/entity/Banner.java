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
@Table(name = "BANNERS_TAB")
public class Banner {

	@Id
	@SequenceGenerator(name = "banner_seq_gen", sequenceName = "banner_seq", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(generator = "banner_seq_gen", strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String title;
	@Column(columnDefinition = "LONGTEXT")
	private String content;	// HTML content

	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}