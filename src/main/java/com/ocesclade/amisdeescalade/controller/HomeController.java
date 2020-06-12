package com.ocesclade.amisdeescalade.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {
	
//	@Autowired
//	private UserService userService;

	@GetMapping(path= {"/", "/accueil"})
	public String home(Model model){
		model.addAttribute("message", message);
		return "accueil";
	}
	
	@GetMapping(path="/login")
	public String login(Model model){
		return "login";
		}
	
//	@PostMapping(path="/identification")
//	public String login(){
//		userService.loadUserByUsername(email);
//		return "identification";
//	}
	
//	@GetMapping("/identification-error.html")
//	public String identificationError(Model model) {
//		model.addAttribute("identificationError", true);
//		return "identification";
//	}
	
	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}
	
//	@GetMapping(value="/creation-compte")
//	public String creationCompte(Model model){
//		model.addAttribute("user", new User());
//		return "creation-compte";
//	}
	
	/* Thymeleaf Page content */
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
}
