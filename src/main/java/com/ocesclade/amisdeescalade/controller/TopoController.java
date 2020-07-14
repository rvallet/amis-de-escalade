package com.ocesclade.amisdeescalade.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.TopoLoan;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.TopoLoanRepository;
import com.ocesclade.amisdeescalade.repository.TopoRepository;
import com.ocesclade.amisdeescalade.service.UserService;

@Controller
public class TopoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopoController.class);

	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private TopoLoanRepository topoLoanRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/topos")
	public String topos (Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();		
		List<Topo> topoList = topoRepository.findAll();
		LOGGER.info("{} --> Affichage de {} topos", email, topoList.size());
		model.addAttribute("topoList" , topoList );
		return "topos";
	}
	
	@PostMapping(value="/topo-pret")
	public String topoLoanConfirmation (
			@RequestParam(name="idTopo") Long id, 
			TopoLoan topoLoan,
			BindingResult bindingResult,
			Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Topo topo = topoRepository.findTopoById(id);
		model.addAttribute("topo", topo);
		topoLoan.setBorrower(email);
		topoLoan.setLender(topo.getUser().getEmail());
		topoLoan.setTopo(topo);
		topoLoanRepository.save(topoLoan);
		model.addAttribute("topoLoan", topoLoan);
		return "redirect:/user/profil#nav-toposloan";
	}
	
	@GetMapping(value="/create-topo")
	public String createTopoForm (Model model) {
		Topo topo = new Topo();
		model.addAttribute("topo", topo);
		return "create-topo";
	}
	
	@PostMapping(value="/create-topo")
	public String createTopo(
			@ModelAttribute("topo") Topo topoToCreate,
			@RequestParam(name = "file", required = false) MultipartFile file,
			RedirectAttributes attributes,
			BindingResult result
			){
		
		if (result.hasErrors()){
			LOGGER.debug("Topo form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
			return "create-topo";
		}
		
		User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Topo topo = new Topo(
				topoToCreate.getName(), 
				topoToCreate.getDescription(), 
				topoToCreate.getLocation(), 
				topoToCreate.getIsAvailableForLoan(), 
				u.getPseudo(), 
				u);
		topo.setIsOnline(true);
		
		// FileName normalize and store
		final String UPLOAD_DIR = "./src/main/resources/static/img/uploads/";
		final String TH_IMG_ROOT_PATH = "/img/uploads/";
		String fileName = "topo_"+System.currentTimeMillis()+"_"+StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Path path = Paths.get(UPLOAD_DIR + fileName);
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			topo.setImgPathThAttribute(TH_IMG_ROOT_PATH+fileName);
		} catch (IOException e) {
			LOGGER.warn("============EXCEPTION TOPO-CREATE============");
			LOGGER.debug("TOPO -> {} upload failed copy into {}", fileName, UPLOAD_DIR);
			e.printStackTrace();
		}
				
		LOGGER.info("user {} create a new Topo {}", u.getEmail(), topo.getName());		
		topoRepository.save(topo);
		return "redirect:/topos";
	}

}
