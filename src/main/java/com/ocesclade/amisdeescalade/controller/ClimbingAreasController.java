package com.ocesclade.amisdeescalade.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Route;
import com.ocesclade.amisdeescalade.entities.Sector;
import com.ocesclade.amisdeescalade.repository.ClimbAreaRepository;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;
import com.ocesclade.amisdeescalade.repository.ClimbRouteRepository;
import com.ocesclade.amisdeescalade.repository.ClimbSectorRepository;

@Controller
public class ClimbingAreasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClimbingAreasController.class);
	
	@Autowired
	private ClimbAreaRepository climbAreaRepository;

	@Autowired
	private ClimbSectorRepository climbSectorRepository;

	@Autowired
	private ClimbRouteRepository climbRouteRepository;

	@Autowired
	private ClimbCommentRepository climbCommentRepository;
	
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
			@RequestParam(name="id_area") Long idArea
			){
		LOGGER.info("Chargement du site id_area={}", idArea);
		model.addAttribute("id_area", idArea);
		Area area = climbAreaRepository.findOneById(idArea);
		model.addAttribute("area" , area );
		
		List<Sector> sectorList = climbSectorRepository.findSectorsByAreaId(idArea);
		model.addAttribute("sectorList" , sectorList );
		List<Route> routeList = climbRouteRepository.findRoutesBySectorAreaId(idArea);
		model.addAttribute("routeList", routeList);
		List<Comment> commentList = climbCommentRepository.findCommentsByAreaId(idArea);
		model.addAttribute("commentList", commentList);

		model.addAttribute("newComment", new Comment());

		return "site";
	}
}
