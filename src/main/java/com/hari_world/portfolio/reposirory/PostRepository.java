package com.hari_world.portfolio.reposirory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hari_world.portfolio.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
	List<Post> findLatestFivePosts(PageRequest of);

	Optional<Post> findBySlugAndPublishedTrue(String slug);

	Optional<Post> findBySlug(String slug);

	@Query("SELECT Count(*) FROM Post p WHERE p.published = true")
	Integer countPublishedPosts();

	@Query("SELECT Count(*) FROM Post p WHERE p.published = false")
	Integer countDraftedPosts();

	@Modifying
	@Query("DELETE FROM Post p WHERE p.slug = :slug")
	void deleteBySlug(String slug);

}