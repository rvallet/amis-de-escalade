package com.ocesclade.amisdeescalade;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.security.WebSecurityConfig;
import com.ocesclade.amisdeescalade.utils.RoleEnum;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
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
				new User("email@user2.fr", webSecurityConfig.passwordEncoder().encode("passwordUser2"), RoleEnum.USER),
				new User("email@admin1.fr", webSecurityConfig.passwordEncoder().encode("passwordAdmin1"), RoleEnum.ADMIN),
				new User("email@admin2.fr", webSecurityConfig.passwordEncoder().encode("passwordAdmin2"), RoleEnum.ADMIN),
				new User("email@user3.fr", "user3_pseudo", "user3_lastName","user3_firstName", webSecurityConfig.passwordEncoder().encode("passwordUser3"), RoleEnum.USER), 
				new User("email@user4.fr", "user4_pseudo", "user4_lastName","user4_firstName", webSecurityConfig.passwordEncoder().encode("passwordUser4"), RoleEnum.USER),
				new User("email@admin3.fr", "admin3_pseudo", "admin3_lastName","admin3_firstName", webSecurityConfig.passwordEncoder().encode("passwordAdmin3"), RoleEnum.ADMIN),
				new User("email@admin4.fr", "admin4_pseudo", "admin4_lastName","admin4_firstName", webSecurityConfig.passwordEncoder().encode("passwordAdmin4"), RoleEnum.ADMIN));		

		userRepository.saveAll(userList);
		}
	}

}
