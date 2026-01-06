package com.hari_world.portfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hari_world.portfolio.entity.Post;
import com.hari_world.portfolio.exception.PostNotFoundException;
import com.hari_world.portfolio.reposirory.PostRepository;
import com.hari_world.portfolio.reposirory.SubscriberRepository;
import com.hari_world.portfolio.util.EmailUtil;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Post savePost(Post post) {
		post = postRepository.save(post);
		if(post.getPublished()) notifySubscribers(post);
		return post;
	}

	@Override
	public Post getPost(String slug) {
		return postRepository.findBySlugAndPublishedTrue(slug)
				.orElseThrow(() -> new PostNotFoundException("Post not found with slug '" + slug + "'"));
	}

	@Override
	public Post getPostBySlug(String slug) {
		return postRepository.findBySlug(slug)
				.orElseThrow(() -> new PostNotFoundException("Post not found with slug '" + slug + "'"));
	}

	@Override
	public Post updatePost(Post post) {
		Optional<Post> obj = postRepository.findById(post.getId());
		if (obj.isPresent()) {
			post = postRepository.save(post);
			if(post.getPublished()) notifySubscribers(post);
			return post;
		} else
			throw new PostNotFoundException("Post not found with id '" + post.getId() + "'");
	}

	@Transactional
	@Override
	public void deletePost(String slug) {
		postRepository.deleteBySlug(slug);
	}

	@Override
	public List<Post> getLatestFivePosts() {
		return postRepository.findLatestFivePosts(PageRequest.of(0, 6));
	}

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public Integer countAll() {
		return (int) postRepository.count();
	}

	@Override
	public Integer countPublished() {
		return postRepository.countPublishedPosts();
	}

	@Override
	public Integer countDrafts() {
		return postRepository.countDraftedPosts();
	}

	private void notifySubscribers(Post post) {
		List<String> subscribers = subscriberRepository.findEmailsByActiveTrue();
		emailUtil.sendNewPostEmail(post.getTitle(), post.getSlug(), post.getSummary(), subscribers);
	}

}