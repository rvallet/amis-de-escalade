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

import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.ClimbAreaRepository;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;
import com.ocesclade.amisdeescalade.repository.ClimbRouteRepository;
import com.ocesclade.amisdeescalade.repository.ClimbSectorRepository;
import com.ocesclade.amisdeescalade.repository.TopoLoanRepository;
import com.ocesclade.amisdeescalade.repository.TopoRepository;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.service.CommentService;
import com.ocesclade.amisdeescalade.service.UserService;

@Controller
public class ProfilController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilController.class);

	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClimbAreaRepository climbAreaRepository;

	@Autowired
	private ClimbCommentRepository climbCommentRepository;

	@GetMapping("/user/profil")
	public String userProfil(Model model) {
		User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("user" , u );
		
		List<Topo> userTopoList = topoRepository.findToposByUser_Id(u.getId());
		model.addAttribute("userTopoList" , userTopoList );
		
		List<Comment> userCommentList = climbCommentRepository.findCommentsByAuthor(u.getPseudo());
		model.addAttribute("userCommentList" , userCommentList );
		
		List<Area> userAreaList = climbAreaRepository.findByAuthor(u.getPseudo());
		model.addAttribute("userAreaList" , userAreaList );
		
		return "user/profil";
	}
	
	@GetMapping("/admin/profil")
	public String adminProfil(Model model) {
		User adminUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("adminUser" , adminUser );
		
		List<Topo> fullTopoList = topoRepository.findAll();
		model.addAttribute("fullTopoList" , fullTopoList );
		
		List<Comment> fullCommentList = climbCommentRepository.findAll();
		model.addAttribute("fullCommentList" , fullCommentList );
		
		List<Area> fullAreaList = climbAreaRepository.findAll();
		model.addAttribute("fullAreaList" , fullAreaList );
		
		List<User> userList = userRepository.findAll(); 
		model.addAttribute("userList" , userList );
		
		return "admin/profil";
	}
	
//	@PostMapping("/admin/update")
//	public String updateData(
//			@RequestParam(name="type", required = false) String dataType,
//			@RequestParam(name="id", required = false) Long dataId,
//			BindingResult result, 
//			Model model
//		) {
//		
//		switch (dataType) {
//		case "area":
//			Area updatedArea = (Area) model.getAttribute(dataType);
//			Area dbArea = climbAreaRepository.findOneById(dataId);
//			dbArea.setIsPromoted(updatedArea.getIsPromoted());
//			climbAreaRepository.save(dbArea);
//			break;
//		case "user":
//			User updatedUser = (User) model.getAttribute(dataType);
//			User dbUser = userService.findById(dataId);
//			dbUser.setRole(updatedUser.getRole());
//			result.getFieldValue("role");
//			userRepository.save(dbUser);
//			break;
//		case "topo":
//			break;
//		case "comment":
//			break;
//		default:
//			break;
//		}
//		
//		return null;
//	}
}
