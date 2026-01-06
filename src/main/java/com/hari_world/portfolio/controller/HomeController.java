package com.hari_world.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hari_world.portfolio.dto.LoginRequest;
import com.hari_world.portfolio.entity.ContactMessage;
import com.hari_world.portfolio.entity.Subscriber;
import com.hari_world.portfolio.service.IBannerService;
import com.hari_world.portfolio.service.IContactService;
import com.hari_world.portfolio.service.ILoginCredentialsService;
import com.hari_world.portfolio.service.IPostService;
import com.hari_world.portfolio.service.IProfileService;
import com.hari_world.portfolio.service.ISubscriberService;

@Controller
@RequestMapping
public class HomeController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private IBannerService bannerService;
	@Autowired
	private IPostService postService;
	@Autowired
	private ILoginCredentialsService credentialsService;
	@Autowired
	private ISubscriberService subscriberService;
	@Autowired
	private IContactService contactService;

	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("banners", bannerService.getLatestFiveBanners());
		model.addAttribute("posts", postService.getLatestFivePosts());
		return "index";
	}

	@GetMapping("/posts")
	public String showPosts(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("posts", postService.getAllPosts());
		return "posts";
	}

	@GetMapping("post/{slug}")
	public String showPost(Model model, @PathVariable String slug) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("post", postService.getPost(slug));
		return "post-view";
	}

	@GetMapping("/about")
	public String showAbout(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		return "about";
	}

	@GetMapping("/contact")
	public String showContact(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("contact", new ContactMessage());
		return "contact";
	}

	@PostMapping("/contact")
	public String saveContact(@ModelAttribute ContactMessage contact, RedirectAttributes attributes) {
		contactService.saveContactMessage(contact);
		attributes.addFlashAttribute("message", "Your query submitted successfully..!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/contact";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("login", new LoginRequest());
		return "login";
	}

	@GetMapping("/forgot-password")
	public String showForgotPasswordPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String sendResetPasswordLink(@RequestParam String username, RedirectAttributes attributes) {
		Boolean sent = credentialsService.sendResetPasswordLink(username);
		if (sent) {
			attributes.addFlashAttribute("message", "Reset password link send to your registered mail...!");
			attributes.addFlashAttribute("messageType", "success");
		} else {
			attributes.addFlashAttribute("message", "User not found with username '" + username + "'...!");
			attributes.addFlashAttribute("messageType", "danger");
		}
		return "redirect:/login";
	}

	@GetMapping("/reset-password/{token}")
	public String showResetPasswordPage(@PathVariable String token, Model model, RedirectAttributes attributes) {
		if (!credentialsService.validResetPasswordToken(token)) {
			attributes.addFlashAttribute("message", "Reset link is invalid or expired.");
			attributes.addFlashAttribute("messageType", "danger");
			return "redirect:/login";
		}
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("token", token);
		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String password,
			@RequestParam String confirmPassword, RedirectAttributes attributes) {
		if (!password.equals(confirmPassword)) {
			attributes.addFlashAttribute("message", "Passwords do not match.");
			attributes.addFlashAttribute("messageType", "warning");
			return "redirect:/reset-password/" + token;
		}
		boolean updated = credentialsService.resetPassword(token, password);
		if (!updated) {
			attributes.addFlashAttribute("message", "Reset link expired.");
			attributes.addFlashAttribute("messageType", "danger");
			return "redirect:/login";
		}
		attributes.addFlashAttribute("message", "Password reset successful.");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/login";
	}

	@GetMapping("/subscribe")
	public String showSubscriptionPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		return "subscribe";
	}

	@PostMapping("/subscribe")
	public String subscribe(@RequestParam String email, @RequestParam String name, RedirectAttributes attributes) {

		if (subscriberService.existsByEmail(email)) {
			if (subscriberService.getSubscriber(email).getActive()) {
				attributes.addFlashAttribute("message", "You are already subscribed & active..!");
				attributes.addFlashAttribute("messageType", "info");
				return "redirect:/";
			}
			subscriberService.subscribe(email);
			attributes.addFlashAttribute("message", "You are already subscribed & activated now..!");
			attributes.addFlashAttribute("messageType", "info");
			return "redirect:/";
		}
		Subscriber subscriber = new Subscriber();
		subscriber.setName(name);
		subscriber.setEmail(email);
		subscriber.setActive(true);
		subscriberService.saveSubscriber(subscriber);

		attributes.addFlashAttribute("message", "Thanks for subscribing..!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/";
	}

	@GetMapping("/unsubscribe")
	public String unsubscribe(@RequestParam String email, RedirectAttributes attributes) {
		if (subscriberService.existsByEmail(email)) {
			subscriberService.unsubscribe(email);
			attributes.addFlashAttribute("message", "You have been unsubscribed...!");
			attributes.addFlashAttribute("messageType", "info");
			return "redirect:/";
		}
		attributes.addFlashAttribute("message", "You have not yet subscribed...!");
		attributes.addFlashAttribute("messageType", "danger");
		return "redirect:/";
	}

}