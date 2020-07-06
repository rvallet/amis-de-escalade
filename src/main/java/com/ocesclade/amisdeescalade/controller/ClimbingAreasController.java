package com.ocesclade.amisdeescalade.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ocesclade.amisdeescalade.dto.CommentDto;
import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Route;
import com.ocesclade.amisdeescalade.entities.Sector;
import com.ocesclade.amisdeescalade.entities.User;
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
			@RequestParam(name="param2", required = false) String param2){
		List<Area> areaList = climbAreaRepository.findAll();

		if (param1!=null && param2!=null) {
			LOGGER.info("Chargement des sites (Nom {} - Description {})",param1, param2);
			
			if (param1.length()>0 && param2.length()>0) {
				areaList = areaList.stream()
						.filter(e -> e.getName().toLowerCase().contains(param1.toLowerCase()) 
								|| e.getDescription().toLowerCase().contains(param2.toLowerCase()))
						.collect(Collectors.toList());
				model.addAttribute("param1", param1 );
				model.addAttribute("param2", param2 );
			} else if (param1.length()>0) {
				areaList = areaList.stream()
						.filter(e -> e.getName().toLowerCase().contains(param1.toLowerCase()))
						.collect(Collectors.toList());
				model.addAttribute("param1", param1 );
			} else {
				areaList = areaList.stream()
						.filter(e -> e.getDescription().toLowerCase().contains(param2.toLowerCase()))
						.collect(Collectors.toList());
				model.addAttribute("param2", param2 );
			}
			model.addAttribute("areaList" , areaList );
			LOGGER.info("Résultat : {} sites", areaList.size());
		} else {
		model.addAttribute("areaList" , areaList );
		LOGGER.info("Chargement de {} sites", areaList.size());
		}
		
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
		List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaId(idArea);
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
	
}
