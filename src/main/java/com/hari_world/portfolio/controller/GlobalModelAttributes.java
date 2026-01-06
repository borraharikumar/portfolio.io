package com.hari_world.portfolio.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalModelAttributes {

	@ModelAttribute("currentPath")
	public String populateCurrentPath(HttpServletRequest request) {
		return request.getRequestURI();
	}
}