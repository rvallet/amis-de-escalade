package com.ocesclade.amisdeescalade.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.TopoLoan;
import com.ocesclade.amisdeescalade.repository.TopoLoanRepository;
import com.ocesclade.amisdeescalade.repository.TopoRepository;

@Controller
public class TopoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopoController.class);

	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private TopoLoanRepository topoLoanRepository;
	
	@GetMapping(value="/topos")
	public String topos (Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();		
		List<Topo> topoList = topoRepository.findAll();
		LOGGER.info("{} --> Affichage de {} topos", email, topoList.size());
		model.addAttribute("topoList" , topoList );
		return "topos";
	}
	
	@GetMapping(value="/topo-pret")
	public String topoLoan (@RequestParam(name="idTopo") Long id, Model model) {
		model.addAttribute("idTopo", id);
		Topo topo = topoRepository.findTopoById(id);
		model.addAttribute("topo", topo);
		model.addAttribute("topoLoan", new TopoLoan());
		return "topo-pret";
	}
	
	@PostMapping(value="/topo-pret-confirmation")
	public String topoLoanConfirmation (
			@RequestParam(name="idTopo") Long id, 
			TopoLoan topoLoan,
			BindingResult bindingResult,
			Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Topo topo = topoRepository.findTopoById(id);
		model.addAttribute("topo", topo);
		topoLoan.setBorrower(email);
		topoLoan.setLender(topo.getBelongTo());
		topoLoan.setTopo(topo);
		topoLoanRepository.save(topoLoan);
		model.addAttribute("topoLoan", topoLoan);
		return "topo-pret-confirmation";
	}

}
