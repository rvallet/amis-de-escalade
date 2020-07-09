package com.ocesclade.amisdeescalade.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.TopoLoan;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.repository.ClimbAreaRepository;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;
import com.ocesclade.amisdeescalade.repository.TopoLoanRepository;
import com.ocesclade.amisdeescalade.repository.TopoRepository;
import com.ocesclade.amisdeescalade.repository.UserRepository;
import com.ocesclade.amisdeescalade.service.UserService;

@Controller
public class ProfilController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClimbAreaRepository climbAreaRepository;

	@Autowired
	private ClimbCommentRepository climbCommentRepository;
	
	@Autowired
	private TopoRepository topoRepository;
	
	@Autowired
	private TopoLoanRepository topoLoanRepository;

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
		
		List<TopoLoan> userTopoLoanList = topoLoanRepository.findAllByBorrower(u.getEmail());
		model.addAttribute("userTopoLoanList" , userTopoLoanList );
		
		LOGGER.info("chargment du profil {}", u.getEmail());
		return "user/profil";
	}
	
	@GetMapping("/user/edit-topo")
	public String editUserTopo(
			@RequestParam(name="id", required = false) Long topoId,
			Model model
			) {
		LOGGER.info("user EDIT Topo id {}", topoId);
		Topo topo = topoRepository.findTopoById(topoId);
		model.addAttribute("topo", topo);
		return "/user/edit-topo";
	}
	
	@PostMapping("/user/update-topo")
	public String updateUserTopo(
			@RequestParam(name="id", required = false) Long topoId,
			Topo topoToUpdate, 
			Model model
		) {
		User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		LOGGER.info("user UPDATE Topo id {}", topoId);
		Topo topo = topoRepository.findTopoById(topoId);
		topo.setIsAvailableForLoan(topoToUpdate.getIsAvailableForLoan());
		topoRepository.save(topo);
		model.addAttribute("userTopoList", topoRepository.findToposByUser_Id(u.getId()));
		return "redirect:/user/profil";
	}
	
	@GetMapping("/admin/profil")
	public String adminProfil(Model model) {
		User adminUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("adminUser" , adminUser );
		
		List<Topo> fullTopoList = topoRepository.findAllActiveTopos();
		model.addAttribute("fullTopoList" , fullTopoList );
		
		List<Comment> fullCommentList = climbCommentRepository.findAll();
		model.addAttribute("fullCommentList" , fullCommentList );
		
		List<Area> fullAreaList = climbAreaRepository.findAll();
		model.addAttribute("fullAreaList" , fullAreaList );
		
		List<User> userList = userRepository.findAll(); 
		model.addAttribute("userList" , userList );
		LOGGER.info("chargment du profil {}", adminUser.getEmail());
		return "admin/profil";
	}
	
	@GetMapping("/admin/edit-area")
	public String editArea(
			@RequestParam(name="id", required = false) Long areaId,
			Model model
			) {
		LOGGER.info("EDIT Area id {}", areaId);
		Area area = climbAreaRepository.findOneById(areaId);
		model.addAttribute("area", area);
		return "/admin/edit-area";
	}
	
	@PostMapping("/admin/update-area")
	public String updateArea(
			@RequestParam(name="id", required = false) Long areaId,
			Area areaToUpdate, 
			Model model
		) {
		LOGGER.info("UPDATE Area id {}", areaId);
		Area area = climbAreaRepository.findOneById(areaId);
		area.setIsPromoted(areaToUpdate.getIsPromoted());
		climbAreaRepository.save(area);
		model.addAttribute("fullAreaList", climbAreaRepository.findAll());
		return "redirect:/admin/profil";
	}
	
	@GetMapping("/admin/edit-comment")
	public String editComment(
			@RequestParam(name="id", required = false) Long commentId,
			Model model
			) {
		LOGGER.info("EDIT Comment id {}", commentId);
		Comment comment = climbCommentRepository.findOneById(commentId);
		model.addAttribute("comment", comment);
		return "/admin/edit-comment";
	}
	
	@PostMapping("/admin/update-comment")
	public String updateComment(
			@RequestParam(name="id", required = false) Long commentId,
			Comment commentToUpdate, 
			Model model
		) {
		LOGGER.info("UPDATE Comment id {}", commentId);
		Comment comment = climbCommentRepository.findOneById(commentId);
		comment.setTitle(commentToUpdate.getTitle());
		comment.setContent(commentToUpdate.getContent());
		climbCommentRepository.save(comment);
		model.addAttribute("fullCommentList", climbCommentRepository.findAll());
		return "redirect:/admin/profil";
	}
	
	@GetMapping("/admin/delete-comment")
	public String deleteComment(
			@RequestParam(name="id", required = false) Long commentId,
			@RequestParam(name="action", required = false) String action,
			Model model
		) {
		LOGGER.info("DELETE Comment id {}", commentId);
		Comment commentToDelete = climbCommentRepository.findOneById(commentId);
		climbCommentRepository.delete(commentToDelete);
		model.addAttribute("fullCommentList", climbCommentRepository.findAll());
		return "redirect:/admin/profil";
	}
	
	@Transactional
	@GetMapping("/admin/delete-topo")
	public String deleteTopo(
			@RequestParam(name="id", required = false) Long topoId,
			@RequestParam(name="action", required = false) String action,
			Model model
		) {
		LOGGER.info("DELETE Topo id {}", topoId);
		Topo topoToDelete = topoRepository.findTopoById(topoId);
		topoToDelete.setIsOnline(false);
		topoRepository.save(topoToDelete);
		model.addAttribute("fullTopoList", topoRepository.findAllActiveTopos());
		return "redirect:/admin/profil";
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
