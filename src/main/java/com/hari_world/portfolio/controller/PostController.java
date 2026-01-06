package com.hari_world.portfolio.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hari_world.portfolio.entity.Post;
import com.hari_world.portfolio.service.IPostService;
import com.hari_world.portfolio.service.IProfileService;

@Controller
@RequestMapping("/admin/posts")
public class PostController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostService postService;
	@Autowired
	private ImagesUploadController imagesUploadController;

	@GetMapping
	public String managePostsPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("posts", postService.getAllPosts());
		return "admin/manage-posts";
	}

	@GetMapping("/new")
	public String newPostPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("post", new Post());
		return "admin/new-post";
	}

	@PostMapping("/new")
	public String savePost(@ModelAttribute Post post, @RequestParam MultipartFile thumbnail,
			RedirectAttributes attributes) throws IOException {
		post.setThumnailUrl((String) imagesUploadController.uploadImage(thumbnail).get("location"));
		postService.savePost(post);
		attributes.addFlashAttribute("message", "Post saved successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:dashboard";
	}

	@GetMapping("/edit/{slug}")
	public String showEditPostPage(@PathVariable String slug, Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("post", postService.getPostBySlug(slug));
		return "admin/edit-post";
	}

	@PostMapping("/edit")
	public String updatePost(@ModelAttribute Post post, @RequestParam MultipartFile thumbnail,
			RedirectAttributes attributes) throws IOException {
		if (!thumbnail.isEmpty())
			post.setThumnailUrl((String) imagesUploadController.uploadImage(thumbnail).get("location"));
		postService.savePost(post);
		attributes.addFlashAttribute("message", "Post updated successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/posts";
	}

	@GetMapping("/delete/{slug}")
	public String deletePost(@PathVariable String slug, RedirectAttributes attributes) {
		postService.deletePost(slug);
		attributes.addFlashAttribute("message", "Post deleted successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/posts";
	}

}