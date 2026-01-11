package com.hari_world.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hari_world.portfolio.entity.Subscriber;
import com.hari_world.portfolio.service.IProfileService;
import com.hari_world.portfolio.service.ISubscriberService;

@Controller
public class SubscriberController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private ISubscriberService subscriberService;

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

	@GetMapping("/subscribers")
	public String showSubscribers(Model model) {
		model.addAttribute("profile", profileService.getProfileData());
		model.addAttribute("subscribers", subscriberService.getAllSubscribers());
		return "admin/subscribers";
	}

	@GetMapping("/subscribers/deactivate/{email}")
	public String deactivateSubscriber(@PathVariable String email, RedirectAttributes attributes) {
		subscriberService.unsubscribe(email);
		attributes.addFlashAttribute("message", "Subscriber deactivated");
		attributes.addFlashAttribute("messageType", "warning");
		return "redirect:/admin/subscribers";
	}

	@GetMapping("/subscribers/activate/{email}")
	public String activateSubscriber(@PathVariable String email, RedirectAttributes attributes) {
		subscriberService.subscribe(email);
		attributes.addFlashAttribute("message", "Subscriber activated");
		attributes.addFlashAttribute("messageType", "success");
		return "redirect:/admin/subscribers";
	}

}