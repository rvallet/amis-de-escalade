package com.ocesclade.amisdeescalade;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Route;
import com.ocesclade.amisdeescalade.entities.Sector;
import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.enumerated.ClimbingGradeEnum;
import com.ocesclade.amisdeescalade.enumerated.RoleEnum;
import com.ocesclade.amisdeescalade.repository.ClimbAreaRepository;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;
import com.ocesclade.amisdeescalade.repository.ClimbRouteRepository;
import com.ocesclade.amisdeescalade.repository.ClimbSectorRepository;
import com.ocesclade.amisdeescalade.repository.TopoRepository;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.security.WebSecurityConfig;
import com.ocesclade.amisdeescalade.utils.StringTools;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private ClimbAreaRepository climbAreaRepository;
	
	@Autowired
	private ClimbRouteRepository climbRouteRepository;
	
	@Autowired
	private ClimbSectorRepository climbSectorRepository;
	
	@Autowired
	private ClimbCommentRepository climbCommentRepository;
	
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
							"user1_pseudo", 
							"user1_lastName",
							"user1_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser1"), 
							RoleEnum.USER), 
					new User("email@user2.fr", 
							"user2_pseudo", 
							"user2_lastName",
							"user2_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordUser2"), 
							RoleEnum.USER),
					new User("email@admin1.fr", 
							"admin1_pseudo", 
							"admin1_lastName",
							"admin1_firstName",
							webSecurityConfig.passwordEncoder().encode("passwordAdmin1"), 
							RoleEnum.ADMIN),
					new User("email@admin2.fr", 
							"admin2_pseudo", 
							"admin2_lastName",
							"admin2_firstName",
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
			/* Randomize of feed some elements */
			Random ran = SecureRandom.getInstanceStrong();
			String loremIpsum = "Vi simulationem difficillimum aliis et placuerat placuerat simulationem ponderibus simulationem tamquam placuerat nimis obstaculo nimis confidentia auxilio commentis qua nimis.";
			
			/* Load previous users from DB */
			User user1 = userRepository.findUserByEmail("email@user1.fr");
			User user2 = userRepository.findUserByEmail("email@user2.fr");
			User user3 = userRepository.findUserByEmail("email@user3.fr");
			User user4 = userRepository.findUserByEmail("email@user4.fr");
			
			/* Initialize BDD with sample Topos */
			if (user1 != null && user2 !=null && user3!=null && user4!=null) {
				LOGGER.info("Initialisation des données de la table 'topo'");
				List<Topo> toposList = Arrays.asList(
					new Topo("topoName1",
							"topoShortDescription1"+loremIpsum, 
							"topoLocation1", 
							new Date(), 
							true, 
							user1.getPseudo(), 
							user1),
					new Topo("topoName2",
							"topoShortDescription2"+loremIpsum, 
							"topoLocation2", 
							null, 
							false, 
							user2.getPseudo(), 
							user2),
					new Topo("topoName3",
							"topoShortDescription3"+loremIpsum, 
							"topoLocation3", 
							new Date(), 
							true, 
							user3.getPseudo(), 
							user3),
					new Topo("topoName4",
							"topoShortDescription4"+loremIpsum, 
							"topoLocation4", 
							null, 
							false, 
							user4.getPseudo(), 
							user4)
					);
			topoRepository.saveAll(toposList);
			LOGGER.info("Ajout de {} Topos", toposList.size());
			
			/* Initialize BDD with sample Climbing Areas */
			LOGGER.info("Initialisation des données de la table 'area'");
			List<Area> climbAreaList = Arrays.asList(
				new Area(
						"areaName1",
						"aeraDescription1 "+loremIpsum),
				new Area(
						"areaName2",
						"aeraDescription2 "+StringTools.randomText(100,12)),
				new Area(
						"areaName3",
						"aeraDescription3 "+StringTools.randomText(100,12)),
				new Area(
						"areaName4",
						"aeraDescription4 "+StringTools.randomText(100,12))
					);
			
			climbAreaRepository.saveAll(climbAreaList);
			LOGGER.info("Ajout de {} Area", climbAreaList.size());
			
			/* Load previous Areas from DB */
			Area area1 = climbAreaRepository.findOneById(1L);
			Area area2 = climbAreaRepository.findOneById(2L);
			Area area3 = climbAreaRepository.findOneById(3L);
			Area area4 = climbAreaRepository.findOneById(4L);			
			
			/* Initialize BDD with sample Climbing Sectors */
			LOGGER.info("Initialisation des données de la table 'sector'");
			List<Sector> climbSectorList = Arrays.asList(
					new Sector(
							"sectorName1",
							"sectorDescription1 "+loremIpsum,
							area1),
					new Sector(
							"sectorName2",
							"sectorDescription2 "+loremIpsum,
							area2),
					new Sector(
							"sectorName3",
							"sectorDescription3 "+loremIpsum,
							area3),
					new Sector(
							"sectorName4",
							"sectorDescription4 "+loremIpsum,
							area4),
					new Sector(
							"sectorName5",
							"sectorDescription5 "+loremIpsum,
							area1),
					new Sector(
							"sectorName6",
							"sectorDescription6 "+loremIpsum,
							area2),
					new Sector(
							"sectorName7",
							"sectorDescription7 "+loremIpsum,
							area3),
					new Sector(
							"sectorName8",
							"sectorDescription8 "+loremIpsum,
							area4)
					);
			climbSectorRepository.saveAll(climbSectorList);
			LOGGER.info("Ajout de {} Sector", climbSectorList.size());

			/* Initialize BDD with sample Climbing Routes (random grades and sectors) */
			
			LOGGER.info("Initialisation des données de la table 'route'");
			List<Route> climbRouteList = Arrays.asList(
					new Route(
							"routeName1",
							"routeDescription1 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName2",
							"routeDescription2 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName3",
							"routeDescription3 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName4",
							"routeDescription4 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName5",
							"routeDescription5 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName6",
							"routeDescription6 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName7",
							"routeDescription7 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName8",
							"routeDescription8 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName9",
							"routeDescription9 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName10",
							"routeDescription10 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName11",
							"routeDescription11 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName12",
							"routeDescription12 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName13",
							"routeDescription13",
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName14",
							"routeDescription14 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName15",
							"routeDescription15 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							),
					new Route(
							"routeName16",
							"routeDescription16 "+loremIpsum,
							Arrays.asList(ClimbingGradeEnum.values()).get(ran.nextInt(Arrays.asList(ClimbingGradeEnum.values()).size())),
							climbSectorList.get(ran.nextInt(climbSectorList.size()))
							)
					);
			climbRouteRepository.saveAll(climbRouteList);
			LOGGER.info("Ajout de {} Route", climbRouteList.size());
			
			/* Initialize BDD with sample Climbing Comments */
			LOGGER.info("Initialisation des données de la table 'comment'");
			List<Comment> climbCommentList = Arrays.asList(
					new Comment(
							"commentTitle1",
							"commentContent1 "+loremIpsum,
							user1.getPseudo(),
							area1),
					new Comment(
							"commentTitle2",
							"commentContent2 "+loremIpsum,
							user2.getPseudo(),
							area2),
					new Comment(
							"commentTitle3",
							"commentContent3 "+loremIpsum,
							user3.getPseudo(),
							area3),
					new Comment(
							"commentTitle4",
							"commentContent4 "+loremIpsum,
							user4.getPseudo(),
							area4),
					new Comment(
							"commentTitle5",
							"commentContent5 "+loremIpsum,
							user3.getPseudo(),
							area1),
					new Comment(
							"commentTitle6",
							"commentContent6 "+loremIpsum,
							user4.getPseudo(),
							area2)
					);
			climbCommentRepository.saveAll(climbCommentList);
			LOGGER.info("Ajout de {} Comment", climbCommentList.size());
			LOGGER.info("La BDD possède maintenant un jeux de données de démonstration");
			}
		}
	}
}
