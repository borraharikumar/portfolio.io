package com.hari_world.portfolio.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hari_world.portfolio.entity.Profile;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	@Value("${server.host}")
	private String host;

	public void sendResetPasswordLink(String email, String name, String token) {
		String resetLink = "https://" + host + "/reset-password/" + token;
		String subject = "Reset Your Password – Hari World";

		Context context = new Context();
		context.setVariable("name", name);
		context.setVariable("resetLink", resetLink);

		String body = templateEngine.process("mail/reset-password-link", context);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("harikumarborra@hari-world.click");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(body, true); // true = HTML
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send reset password email", e);
		}
	}

	public void sendSubscriptionConfirmedMessage(String email, String name) {
		String unsubscribeUrl = "https://" + host + "/unsubscribe?email=" + email;
		String homeUrl = "https://" + host + "/";
		String subject = "Welcome to Hari World – Subscription Confirmed 🎉";

		Context context = new Context();
		context.setVariable("name", name);
		context.setVariable("homeUrl", homeUrl);
		context.setVariable("unsubscribeUrl", unsubscribeUrl);

		String body = templateEngine.process("mail/subscription-confirmed", context);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("harikumarborra@hari-world.click");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(body, true); // true = HTML
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send subscription confirmation email", e);
		}
	}

	public void sendAdminAdditionConfirmationMail(String name, String username, String password, String role,
			String email) {
		String loginUrl = "https://" + host + "/login";
		String subject = "Your Admin Account Has Been Created – Hari World";
		Context context = new Context();
		context.setVariable("name", name);
		context.setVariable("username", username);
		context.setVariable("password", password);
		context.setVariable("role", role);
		context.setVariable("loginUrl", loginUrl);
		String body = templateEngine.process("mail/admin-addition-confirmation", context);
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("harikumarborra@hari-world.click");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(body, true); // true = HTML
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send Admin Account creation confirmation mail", e);
		}
	}

	public void sendNewPostEmail(String title, String slug, String summary, List<String> subscribers) {
		String subject = "New Post Alert: " + title;
		String postUrl = "https://" + host + "/post/" + slug;
		Context context = new Context();
		context.setVariable("title", title);
		context.setVariable("summary", summary);
		context.setVariable("postUrl", postUrl);
		context.setVariable("unsubscribeUrl", "https://hari-world.click/unsubscribe");
		String body = templateEngine.process("mail/new-post-notification", context);
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setFrom("harikumarborra@hari-world.click");
			helper.setBcc(subscribers.toArray(new String[0]));
			helper.setText(body, true);
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send new post notification email", e);
		}
	}

	public void sendPasswordChangeSuccessMail(Profile profile) {
		String subject = "Security Update: Password Changed";
		String loginUrl = "https://" + host + "/login";
		Context context = new Context();
		context.setVariable("name", profile.getName());
		context.setVariable("loginUrl", loginUrl);
		String body = templateEngine.process("mail/reset-password-success", context);
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setFrom("harikumarborra@hari-world.click");
			helper.setTo(profile.getEmail());
			helper.setText(body, true);
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send new post notification email", e);
		}
	}

}