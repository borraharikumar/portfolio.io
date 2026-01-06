package com.hari_world.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hari_world.portfolio.entity.Banner;
import com.hari_world.portfolio.service.IBannerService;
import com.hari_world.portfolio.service.IProfileService;

@Controller
@RequestMapping("/admin/banners")
public class BannerController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private IBannerService bannerService;

	@GetMapping
	public String manageBannersPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("banners", bannerService.getAllbanners());
		return "admin/manage-banners";
	}

	@GetMapping("/new")
	public String newBannerPage(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("banner", new Banner());
		return "admin/new-banner";
	}

	@PostMapping("/new")
	public String saveBanner(@ModelAttribute Banner banner, RedirectAttributes attributes) {
		bannerService.saveBanner(banner);
		attributes.addFlashAttribute("message", "Banner saved successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:dashboard";
	}

	@GetMapping("/edit/{id}")
	public String showEditBannerPage(@PathVariable Integer id, Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("banner", bannerService.getBanner(id));
		return "admin/edit-banner";
	}

	@PostMapping("/edit")
	public String editBanner(@ModelAttribute Banner banner, RedirectAttributes attributes) {
		bannerService.saveBanner(banner);
		attributes.addFlashAttribute("message", "Banner updated successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/banners";
	}

	@GetMapping("/delete/{id}")
	public String deleteBanner(@PathVariable Integer id, RedirectAttributes attributes) {
		bannerService.deleteBanner(id);
		attributes.addFlashAttribute("message", "Banner deleted successfully...!");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/banners";
	}

}