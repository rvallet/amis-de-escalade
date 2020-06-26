package com.ocesclade.amisdeescalade.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.repository.TopoRepository;

@Controller
public class TopoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopoController.class);

	@Autowired
	private TopoRepository topoRepository;
	
	@GetMapping(value="/topos")
	public String Topos (Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();		
		List<Topo> topoList = topoRepository.findAll();
		LOGGER.info("{} --> Affichage de {} topos", email, topoList.size());
		model.addAttribute("topoList" , topoList );
		return "topos";
	}

}
