package com.ocesclade.amisdeescalade.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(path= {"/", "/accueil"})
	public String home(Model model){
		model.addAttribute("message", message);
		return "accueil";
	}
	
	@GetMapping(path="/login")
	public String login(Model model){
		return "login";
		}
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(Model model) {
		model.addAttribute("message", message);
		return "access-denied";
	}
	
	@GetMapping(value="/logout")
	public String logout (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	return "redirect:/login?logout";
	}
	
	/* Thymeleaf Page content */
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
}
