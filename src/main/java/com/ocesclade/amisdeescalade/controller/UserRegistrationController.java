package com.ocesclade.amisdeescalade.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocesclade.amisdeescalade.dto.UserRegistrationDto;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.service.UserService;


@Controller
@RequestMapping("/creation-compte")
public class UserRegistrationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "creation-compte";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result){
		LOGGER.info("AccountCreation for {}", userDto.getEmail());
		User isEmailExist = userService.findByEmail(userDto.getEmail());
		User isPseudoExist = userService.findByPseudo(userDto.getPseudo());
		
			if (isEmailExist != null){
				LOGGER.warn("Cet email existe déjà en BDD");
				result.rejectValue("email", null, "Le compte "+userDto.getEmail()+" existe déjà. Identifiez-vous ou réinitialisez votre mot de passe");
			}
			
			if (isPseudoExist != null){
				LOGGER.warn("Ce pseudo existe déjà en BDD");
				result.rejectValue("pseudo", null, "Ce pseudo est déjà utilisé");
			}
			
	
			if (result.hasErrors()){
				LOGGER.debug("form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
				return "creation-compte";
			}
	
		userService.save(userDto);
		return "redirect:/creation-compte?success";
	}
}
