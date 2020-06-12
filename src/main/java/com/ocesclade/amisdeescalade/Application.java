package com.ocesclade.amisdeescalade;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.security.WebSecurityConfig;
import com.ocesclade.amisdeescalade.utils.RoleEnum;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private WebSecurityConfig webSecurityConfig;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/* Initialize BDD with sample test users if empty (on first launch only) */
		if (userRepository.findUserByEmail("email@user1.fr") == null) {
		List<User> userList = Arrays.asList(
				new User("email@user1.fr", webSecurityConfig.passwordEncoder().encode("passwordUser1"), RoleEnum.USER), 
				new User("email@user2.fr", "passwordUser2", RoleEnum.USER),
				new User("email@admin1.fr", "passwordAdmin1", RoleEnum.ADMIN),
				new User("email@admin2.fr", "passwordAdmin2", RoleEnum.ADMIN));		

		userRepository.saveAll(userList);
		}
	}

}
