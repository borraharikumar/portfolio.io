package com.hari_world.portfolio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ErrorController {

	@GetMapping("/access-denied")
	public String accessDenied(Authentication authentication, RedirectAttributes attributes) {
		attributes.addFlashAttribute("message", "You do not have permission to access this resource.");
		attributes.addFlashAttribute("messageType", "danger");
		if (authentication != null && authentication.isAuthenticated()) {
			return "redirect:/admin/dashboard";
		}
		return "redirect:/login";
	}
}
