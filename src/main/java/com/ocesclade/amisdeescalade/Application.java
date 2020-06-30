package com.ocesclade.amisdeescalade;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.enumerated.RoleEnum;
import com.ocesclade.amisdeescalade.repository.TopoRepository;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.security.WebSecurityConfig;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private WebSecurityConfig webSecurityConfig;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		boolean isBddInit = false;
		
		/* Initialize BDD with sample test users if empty (on first launch only) */
		LOGGER.info("Recherche de l'existance de l'utilisateur 'email@user1.fr' en BDD");
		if (userRepository.findUserByEmail("email@user1.fr") == null) {
			
			LOGGER.info("Initialisation des données de la table 'user'");
			isBddInit=true;
			
			List<User> userList = Arrays.asList(
					new User("email@user1.fr", 
							webSecurityConfig.passwordEncoder().encode("passwordUser1"), 
							RoleEnum.USER), 
					new User("email@user2.fr", 
							webSecurityConfig.passwordEncoder().encode("passwordUser2"), 
							RoleEnum.USER),
					new User("email@admin1.fr", 
							webSecurityConfig.passwordEncoder().encode("passwordAdmin1"), 
							RoleEnum.ADMIN),
					new User("email@admin2.fr", 
							webSecurityConfig.passwordEncoder().encode("passwordAdmin2"), 
							RoleEnum.ADMIN),
					new User("email@user3.fr", 
							"user3_pseudo", 
							"user3_lastName",
							"user3_firstName", 
							webSecurityConfig.passwordEncoder().encode("passwordUser3"), 
							RoleEnum.USER), 
					new User("email@user4.fr", 
							"user4_pseudo", 
							"user4_lastName",
							"user4_firstName", 
							webSecurityConfig.passwordEncoder().encode("passwordUser4"), 
							RoleEnum.USER),
					new User("email@admin3.fr", 
							"admin3_pseudo", 
							"admin3_lastName",
							"admin3_firstName", 
							webSecurityConfig.passwordEncoder().encode("passwordAdmin3"), 
							RoleEnum.ADMIN),
					new User("email@admin4.fr", 
							"admin4_pseudo", 
							"admin4_lastName",
							"admin4_firstName", 
							webSecurityConfig.passwordEncoder().encode("passwordAdmin4"), 
							RoleEnum.ADMIN)
					);		
	
			userRepository.saveAll(userList);
			LOGGER.info("Ajout de {} Utilisateurs", userList.size());
		} else {
			LOGGER.info("Utilisateur 'email@user1.fr' trouvé en BDD.");
		}
		
		if (isBddInit) {
			/* Load users for tests */
			User user1 = userRepository.findUserByEmail("email@user1.fr");
			User user2 = userRepository.findUserByEmail("email@user2.fr");
			User user3 = userRepository.findUserByEmail("email@user3.fr");
			User user4 = userRepository.findUserByEmail("email@user4.fr");
			
			/* Initialize BDD with sample Topos if empty (on first launch only) */
			if (user1 != null && user2 !=null && user3!=null && user4!=null) {
				LOGGER.info("Initialisation des données de la table 'topo'");
				List<Topo> toposList = Arrays.asList(
					new Topo("topoName1",
							"topoShortDescription1", 
							"topoLocation1", 
							new Date(), 
							true, 
							user1.getPseudo(), 
							user1),
					new Topo("topoName2",
							"topoShortDescription2", 
							"topoLocation2", 
							null, 
							false, 
							user2.getPseudo(), 
							user2),
					new Topo("topoName3",
							"topoShortDescription3", 
							"topoLocation3", 
							new Date(), 
							true, 
							user3.getPseudo(), 
							user3),
					new Topo("topoName4",
							"topoShortDescription4", 
							"topoLocation4", 
							null, 
							false, 
							user4.getPseudo(), 
							user4)
					);
			topoRepository.saveAll(toposList);
			LOGGER.info("Ajout de {} Topos", toposList.size());
			}
			
			//TODO : Sites, Secteurs et voies
		}
		
	}
}
