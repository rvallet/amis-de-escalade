package com.ocesclade.amisdeescalade.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ocesclade.amisdeescalade.dto.UserRegistrationDto;
import com.ocesclade.amisdeescalade.entities.User;

public interface UserService extends UserDetailsService{
	
	User findByEmail (String email);
	User findByPseudo (String pseudo);
	User findById (Long id);
	User save(UserRegistrationDto userRegistrationDto);
	
}
