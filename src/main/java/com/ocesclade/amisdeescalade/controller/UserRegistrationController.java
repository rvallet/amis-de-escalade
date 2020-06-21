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
		User existing = userService.findByEmail(userDto.getEmail());
		
			if (existing != null){
				LOGGER.warn("This email already exist");
				result.rejectValue("email", null, "There is already an account registered with that email");
			}
	
			if (result.hasErrors()){
				LOGGER.debug("form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
				return "creation-compte";
			}
	
		userService.save(userDto);
		return "redirect:/creation-compte?success";
	}
}
