package com.ocesclade.amisdeescalade.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ocesclade.amisdeescalade.dto.CommentDto;
import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Route;
import com.ocesclade.amisdeescalade.entities.Sector;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.enumerated.ClimbingGradeEnum;
import com.ocesclade.amisdeescalade.repository.ClimbAreaRepository;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;
import com.ocesclade.amisdeescalade.repository.ClimbRouteRepository;
import com.ocesclade.amisdeescalade.repository.ClimbSectorRepository;
import com.ocesclade.amisdeescalade.service.CommentService;
import com.ocesclade.amisdeescalade.service.UserService;

@Controller
public class ClimbingAreasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClimbingAreasController.class);
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClimbAreaRepository climbAreaRepository;

	@Autowired
	private ClimbSectorRepository climbSectorRepository;

	@Autowired
	private ClimbRouteRepository climbRouteRepository;

	@Autowired
	private ClimbCommentRepository climbCommentRepository;
	
	@ModelAttribute("newComment")
	public CommentDto commentDto() {
		return new CommentDto();
	}
	
	@GetMapping(value="/sites")
	public String climbAreas(
			Model model,
			@RequestParam(name="param1", required = false) String param1,
			@RequestParam(name="param2", required = false) String param2,
			@RequestParam(name="param3", required = false) String param3,
			@RequestParam(name="param4", required = false) String param4,
			@RequestParam(name="param5", required = false) String param5,
			@RequestParam(name="param5", required = false) String param6
			){
		List<Area> areaList = climbAreaRepository.findAll();
		LOGGER.info("Chargement des sites paramètres de filtres : [{}, {}, {}, {}, {}]", param1, param2, param3, param4, param5);
				
		if (param1!=null && param1.length()>0) {
			areaList = areaList.stream()
					.filter(e -> e.getName().toLowerCase().contains(param1.toLowerCase()))
					.collect(Collectors.toList());
			model.addAttribute("param1", param1 );
		}
		
		if (param2!=null && param2.length()>0) {
			areaList = areaList.stream()
					.filter(e -> e.getDescription().toLowerCase().contains(param2.toLowerCase()))
					.collect(Collectors.toList());
			model.addAttribute("param2", param2 );
		}
		
		if (param3!=null && param3.length()>0) {
			areaList = areaList.stream()
					.filter(e -> e.getLocation().toLowerCase().contains(param3.toLowerCase()))
					.collect(Collectors.toList());
			model.addAttribute("param3", param3 );
		}
		
		if (param4!=null && param4.length()>0) {
			LOGGER.info("Recherche Area avec Route de grade {} - Résultat = {} Area", param4, climbAreaRepository.findAreaBySectorAndByRouteClimbingGrade(param4).size());
			areaList = climbAreaRepository.findAreaBySectorAndByRouteClimbingGrade(param4);
			model.addAttribute("param4", param4 );
		}
		
		if (param5!=null && param5.length()>0) {
			areaList = areaList.stream()
					.filter(e -> e.getSectorList().size() == Integer.valueOf(param5))
					.collect(Collectors.toList());
			model.addAttribute("param5", param5 );
		}
		
		if (param6!=null && param6.length()>0) {
			LOGGER.info("Recherche Area avec Route de {} longeur - Résultat = {} Area", param6, climbAreaRepository.findAreaBySectorAndByRouteNbLength(Integer.valueOf(param6)).size());
			areaList = climbAreaRepository.findAreaBySectorAndByRouteNbLength(Integer.valueOf(param6));
			model.addAttribute("param6", param6 );
		}
		
		Set<Integer> nbSectorSet = new HashSet<>();
		Set<Integer> nbLengthSet = new HashSet<>();		
		Set<String> gradeSet = new HashSet<>();
		
		for (Area area : areaList) {
			nbSectorSet.add(area.getSectorList().size());
			for (Sector sector : area.getSectorList()) {				
				for (Route route : sector.getRouteList()) {
					gradeSet.add(route.getClimbingGrade());
					nbLengthSet.add(route.getNbLength());					
				}
			}
		}
		
		List<Integer> nbLengthList = new ArrayList<>(nbLengthSet);		
		List<Integer> nbSectorList = new ArrayList<>(nbSectorSet);
		List<String> gradeList = new ArrayList<>(gradeSet);
		Collections.sort(nbSectorList);
		Collections.sort(nbLengthList);
		gradeList.sort(Comparator.comparing( String::toString ));
		
		model.addAttribute("gradeList" , gradeList);
		model.addAttribute("nbSectorList" , nbSectorList);
		model.addAttribute("nbLengthList" , nbLengthList);
		model.addAttribute("areaList" , areaList);
		LOGGER.info("Chargement de {} sites", areaList.size());
		
		return "sites";		
	}
	
	@GetMapping(value="/site")
	public String displayArea(
			Model model,
			@RequestParam(name="id_area", required = false) Long idArea
			){
		if (idArea==null) {return "sites";}
		LOGGER.info("Chargement du site id_area={}", idArea);
		model.addAttribute("id_area", idArea);
		Area area = climbAreaRepository.findOneById(idArea);
		
		model.addAttribute("area" , area );		
		List<Sector> sectorList = climbSectorRepository.findSectorsByAreaId(idArea);
		model.addAttribute("sectorList" , sectorList );
		List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaIdOrderBySectorIdDesc(idArea);
		model.addAttribute("routeList", routeList);
		List<Comment> commentList = climbCommentRepository.findByAreaIdOrderByIdDesc(idArea);
		model.addAttribute("commentList", commentList);

		model.addAttribute("newComment", new CommentDto());

		return "site";
	}
	
	@PostMapping(value="/site")
	public String addComment(
			@RequestParam(name="id_area", required = false) Long idArea,
			@ModelAttribute("newComment") @Valid CommentDto commentDto,
//			@Valid Comment newComment,
			BindingResult result,
			Model model
			) {
		if (idArea==null) {return "sites";}
		LOGGER.info("Ajout d'un commentaire sur le site id_area={}", idArea);
		model.addAttribute("id_area", idArea);
		Area area = climbAreaRepository.findOneById(idArea);
		
		commentDto.setArea(area);
		if(result.hasErrors()){
			LOGGER.debug("form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
			model.addAttribute("area" , area );
			List<Sector> sectorList = climbSectorRepository.findSectorsByAreaId(idArea);
			model.addAttribute("sectorList" , sectorList );
			List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaId(idArea);
			model.addAttribute("routeList", routeList);
			List<Comment> commentList = climbCommentRepository.findByAreaIdOrderByIdDesc(idArea);
			model.addAttribute("commentList", commentList);
			return "site";
		}
		User u = userService.findByEmail(commentDto.getAuthor());
		commentDto.setAuthor(u.getPseudo());
		commentService.save(commentDto);
		model.addAttribute("newComment", commentDto);
		
		return "redirect:/site?id_area="+idArea+"#nav-comments";
	}
	
	@GetMapping(value="/create-area")
	public String createAreaForm(Model model){
		Area area = new Area();
		model.addAttribute("area", area);
		return "create-area";
	}
	
	@PostMapping(value="/create-area")
	public String createArea(
			@ModelAttribute("area") Area areaToCreate, 
			@RequestParam(name = "file", required = false) MultipartFile file,
			RedirectAttributes attributes,
			BindingResult result
			){
		
		if (result.hasErrors()){
			LOGGER.debug("Area form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
			return "create-area";
		}
		
		User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Area area = new Area(
				areaToCreate.getName(), 
				areaToCreate.getDescription(),
				areaToCreate.getLocation(),
				u.getPseudo());
		if(!file.isEmpty()) {
			// FileName normalize and store
			final String UPLOAD_DIR = "./src/main/resources/static/img/uploads/";
			final String TH_IMG_ROOT_PATH = "/img/uploads/";
			String fileName = "area_"+System.currentTimeMillis()+"_"+StringUtils.cleanPath(file.getOriginalFilename());
			try {
				Path path = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				area.setImgPathThAttribute(TH_IMG_ROOT_PATH+fileName);
			} catch (IOException e) {
				LOGGER.debug("Area --> {} upload failed copy into {}", fileName, UPLOAD_DIR);
				e.printStackTrace();
			}
		}
		
		LOGGER.info("user {} create a new Area {} (Uploaded IMG : {})", u.getEmail(), area.getName(), !file.isEmpty());		
	
		climbAreaRepository.save(area);		
		return "redirect:/sites";
	}
	
	@PostMapping(value="/create-sector")
	public String formCreateSector(
			Model model, 
			@RequestParam(name="id_area", required = false) Long idArea
			) {
		Sector sector = new Sector();
		model.addAttribute("id_area", idArea);
		model.addAttribute("sector", sector);
		return "create-sector";
	}
	
	@PostMapping(value="/send-sector")
	public String createSector(
			@RequestParam(name="id_area", required = false) Long idArea,
			@ModelAttribute("sector") @Valid Sector sectorToAdd,
			BindingResult result,
			Model model
			) {
		if (idArea==null) {
			LOGGER.info("Echec d'ajout du secteur {} (id_area={}", sectorToAdd.getName(), idArea);
			return "sites";
			}
		
		Area area = climbAreaRepository.findOneById(idArea);
		int initSectorListSize = area.getSectorList().size();
		Sector sector = new Sector(
				sectorToAdd.getName(),
				sectorToAdd.getDescription(),
				area
				);
		climbSectorRepository.save(sector);
		area.getSectorList().add(sectorToAdd);
		
		if(result.hasErrors()){
			LOGGER.debug("Sector form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
			model.addAttribute("area" , area );
			List<Sector> sectorList = climbSectorRepository.findSectorsByAreaId(idArea);
			model.addAttribute("sectorList" , sectorList );
			List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaId(idArea);
			model.addAttribute("routeList", routeList);
			List<Comment> commentList = climbCommentRepository.findByAreaIdOrderByIdDesc(idArea);
			model.addAttribute("commentList", commentList);
			return "site";
		}		
		
		LOGGER.info("Ajout d'un secteur sur le site id_area={} (Nb Secteurs {} --> {}", idArea, initSectorListSize, area.getSectorList().size());
		climbAreaRepository.save(area);
		model.addAttribute("area", area);		
		return "redirect:/site?id_area="+idArea+"#nav-sectors";
	}
	
	@PostMapping(value="/create-route")
	public String formCreateRoute(
			Model model, 
			@RequestParam(name="id_sector", required = false) Long idSector,
			@RequestParam(name="id_area", required = false) Long idArea
			) {
		Route route = new Route();
		model.addAttribute("id_sector", idSector);
		model.addAttribute("id_area", idArea);
		model.addAttribute("route", route);
		return "create-route";
	}
	
	@PostMapping(value="/send-route")
	public String createRoute(
			@RequestParam(name="id_sector", required = false) Long idSector,
			@RequestParam(name="id_area", required = false) Long idArea,
			@ModelAttribute("route") @Valid Route routeToAdd,
			BindingResult result,
			Model model
			) {
		
		if (idArea==null) {
			LOGGER.info("Echec d'ajout du secteur {} (id_area={}", routeToAdd.getName(), idArea);
			return "sites";
			}
		
		if (idSector==null) {
			LOGGER.info("Echec d'ajout du secteur {} (id_sector={}", routeToAdd.getName(), idSector);
			return "sites";
			}
		
		Sector sector = climbSectorRepository.findOneById(idSector);
		int initRouteListSize = sector.getRouteList().size();
		Route route = new Route(
				routeToAdd.getName(),
				routeToAdd.getDescription(),
				ClimbingGradeEnum.of(routeToAdd.getClimbingGrade()),
				sector
				);
		route.setNbLength(routeToAdd.getNbLength());
		climbRouteRepository.save(route);
		sector.getRouteList().add(routeToAdd);
		
		if(result.hasErrors()){
			LOGGER.debug("Route form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
			model.addAttribute("route" , route );
			List<Sector> sectorList = climbSectorRepository.findSectorsByAreaId(idArea);
			model.addAttribute("sectorList" , sectorList );
			List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaId(idArea);
			model.addAttribute("routeList", routeList);
			List<Comment> commentList = climbCommentRepository.findByAreaIdOrderByIdDesc(idArea);
			model.addAttribute("commentList", commentList);
			return "site";
		}
				
		LOGGER.info("Ajout d'une voie le site id_area={} (Nb voie {} --> {}", idArea, initRouteListSize, sector.getRouteList().size());
		climbSectorRepository.save(sector);
		model.addAttribute("sector", sector);		
		return "redirect:/site?id_area="+idArea+"#nav-routes";
	}
}
