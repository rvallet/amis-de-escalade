package com.ocesclade.amisdeescalade.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ocesclade.amisdeescalade.dto.UserRegistrationDto;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.security.WebSecurityConfig;
import com.ocesclade.amisdeescalade.utils.RoleEnum;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebSecurityConfig webSecurityConfig;

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		LOGGER.info("loadUserByUsername START with email = {}", email);
		User user = userRepository.findUserByEmail(email);
	
		if (user == null){
			LOGGER.warn("loadUserByUsername FAILED");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		LOGGER.info("loadUserByUsername with {} {} {}",user.getEmail(),user.getPassword(),user.getRole());
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				convertRoleEnumToAuthorities(user.getRole()));
	}
	
	private Collection<? extends GrantedAuthority> convertRoleEnumToAuthorities(RoleEnum role){
	return AuthorityUtils.createAuthorityList(role.toString());
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User save(UserRegistrationDto userRegistrationDto) {
		User user = new User();
		user.setPseudo(userRegistrationDto.getPseudo());
		user.setFirstName(userRegistrationDto.getFirstName());
		user.setLastName(userRegistrationDto.getLastName());
		user.setEmail(userRegistrationDto.getEmail());
		user.setPassword(webSecurityConfig.passwordEncoder().encode(userRegistrationDto.getPassword()));
		user.setRole(RoleEnum.USER);
		return null;
	}

}
