package com.ocesclade.amisdeescalade.controller;

import org.springframework.beans.factory.annotation.Value;
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
	
	/* Thymeleaf Page content */
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
}
