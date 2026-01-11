package com.hari_world.portfolio.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.hari_world.portfolio.dto.ChangePasswordRequest;
import com.hari_world.portfolio.entity.LoginCredentials;
import com.hari_world.portfolio.entity.Profile;
import com.hari_world.portfolio.entity.ReplyMessage;
import com.hari_world.portfolio.service.IContactService;
import com.hari_world.portfolio.service.ILoginCredentialsService;
import com.hari_world.portfolio.service.IPostService;
import com.hari_world.portfolio.service.IProfileService;
import com.hari_world.portfolio.service.ISubscriberService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostService postService;
	@Autowired
	private ILoginCredentialsService credentialsService;
	@Autowired
	private ISubscriberService subscriberService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private ImagesUploadController imagesUploadController;

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("postCount", postService.countAll());
		model.addAttribute("publishedCount", postService.countPublished());
		model.addAttribute("draftCount", postService.countDrafts());
		model.addAttribute("latestPosts", postService.getLatestFivePosts());
		model.addAttribute("subscriberCount", subscriberService.getCountOfAllSubcribers());
		model.addAttribute("activeSubscriberCount", subscriberService.getCountOfActiveSubscribers());
		model.addAttribute("inactiveSubscriberCount", subscriberService.getCountOfInactiveSubscribers());
		model.addAttribute("unreadMessagesCount", contactService.getCountOfUnreadMessages());
		model.addAttribute("latestUnreadMessages", contactService.getLatestUnreadMessages());
		model.addAttribute("adminCount", credentialsService.getAdminsCount());
		return "admin/dashboard";
	}

	@GetMapping("/profile")
	public String editProfile(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		return "admin/edit-profile";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PostMapping("/profile")
	public String saveProfile(@ModelAttribute Profile profile, @RequestParam MultipartFile profilePic,
			RedirectAttributes attributes) throws IOException {
		if (!profilePic.isEmpty())
			profile.setProfilePicUrl((String) imagesUploadController.uploadImage(profilePic).get("location"));
		profileService.saveProfile(profile);
		attributes.addFlashAttribute("message", "Profile data saved successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:dashboard";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admin/new")
	public String showAddAdminPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		Profile profile = new Profile();
		LoginCredentials credentials = new LoginCredentials();
		credentials.setProfile(profile);
		model.addAttribute("credentials", credentials);
		return "admin/new-admin";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admins/edit/{id}")
	public String editAdmin(@PathVariable Integer id, Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("credentials", credentialsService.getCredentials(id));
		return "admin/edit-admin";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PostMapping("/admins/edit")
	public String updateAdmin(@ModelAttribute LoginCredentials credentials, RedirectAttributes attributes) {
		credentialsService.updateCredentials(credentials);
		attributes.addFlashAttribute("message", "Admin updated successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/admins";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PostMapping("/admin/new")
	public String addAdmin(@ModelAttribute LoginCredentials credentials, RedirectAttributes attributes) {
		credentialsService.saveCredentials(credentials);
		attributes.addFlashAttribute("message", "New admin added successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/dashboard";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admins")
	public String showManageAdminsPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("admins", credentialsService.getAdminsList());
		return "admin/manage-admins";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admins/disable/{id}")
	public String disableAdmin(@PathVariable Integer id, RedirectAttributes attributes) {
		credentialsService.disableAdmin(id);
		attributes.addFlashAttribute("message", "Admin disabled..!");
		attributes.addFlashAttribute("messageType", "warning");
		return "redirect:/admin/admins";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admins/enable/{id}")
	public String enableAdmin(@PathVariable Integer id, RedirectAttributes attributes) {
		credentialsService.enableAdmin(id);
		attributes.addFlashAttribute("message", "Admin enabled..!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/admins";
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/admins/delete/{id}")
	public String deleteAdmin(@PathVariable Integer id, RedirectAttributes attributes) {
		credentialsService.deleteAdmin(id);
		attributes.addFlashAttribute("message", "Admin deleted..!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/admins";
	}

	@GetMapping("/change-password")
	public String showChangePasswordPage(Model model, Principal principal) {
		model.addAttribute("profile", profileService.getProfileData());
		ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setUsername(principal.getName());
		model.addAttribute("changePasswordRequest", changePasswordRequest);
		return "admin/change-password";
	}

	@PostMapping("/change-password")
	public String showForgotPasswordPage(RedirectAttributes attributes, @ModelAttribute ChangePasswordRequest request) {
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			attributes.addFlashAttribute("message", "New passwords do not match");
			attributes.addFlashAttribute("messageType", "warning");
			return "redirect:/admin/change-password";
		}
		Boolean updated = credentialsService.changePassword(request);
		if (!updated) {
			attributes.addFlashAttribute("message", "Current password is incorrect");
			attributes.addFlashAttribute("messageType", "danger");
			return "redirect:/admin/change-password";
		}
		attributes.addFlashAttribute("message", "Password updated successfully");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:dashboard";
	}

	@GetMapping("/messages")
	public String showContactMessages(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("messages", contactService.getAllMessages());
		return "admin/contact-messages";
	}

	@GetMapping("/messages/read/{id}")
	public String markMessageAsRead(@PathVariable Integer id, RedirectAttributes attributes) {
		contactService.markMessageAsRead(id);
		attributes.addFlashAttribute("message", "Message marked as read...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/messages/reply/{id}")
	public String showReplyPage(@PathVariable Integer id, Model model) {
		ReplyMessage replyMessage = new ReplyMessage();
		replyMessage.setContactMessage(contactService.getContactMessage(id));
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("replyMessage", replyMessage);
		return "admin/reply-message";
	}

	@PostMapping("/messages/reply")
	public String replyToMessage(@ModelAttribute ReplyMessage replyMessage, RedirectAttributes attributes) {
		contactService.saveReplyMessage(replyMessage);
		attributes.addFlashAttribute("message", "Reply sent successfully!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/messages";
	}

}