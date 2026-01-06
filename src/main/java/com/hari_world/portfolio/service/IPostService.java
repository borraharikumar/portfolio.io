package com.hari_world.portfolio.service;

import java.util.List;

import com.hari_world.portfolio.entity.Post;

public interface IPostService {
	
	public Post savePost(Post post);
	public Post getPost(String slug);
	public Post getPostBySlug(String slug);
	public Post updatePost(Post post);
	public void deletePost(String slug);
	public List<Post> getLatestFivePosts();
	public List<Post> getAllPosts();
	public Integer countAll();
	public Integer countPublished();
	public Integer countDrafts();

}
