package com.ocesclade.amisdeescalade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path= {"/", "/accueil"})
	public String accueil(Model model){
		model.addAttribute("message", message);
		return "accueil";
	}
	
	@GetMapping(path="/identification")
	public String identification(){
		return "identification";
		}
	
	@GetMapping("/identification-error.html")
	public String identificationError(Model model) {
		model.addAttribute("identificationError", true);
		return "identification";
	}
	
	@GetMapping(value="/creation-compte")
	public String creationCompte(Model model){
		model.addAttribute("user", new User());
		return "creation-compte";
	}
	
	/* Thymeleaf Page content */
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
}
