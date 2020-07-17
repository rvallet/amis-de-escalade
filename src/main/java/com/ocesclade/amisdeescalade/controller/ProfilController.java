package com.ocesclade.amisdeescalade.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ocesclade.amisdeescalade.entities.Area;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.entities.Topo;
import com.ocesclade.amisdeescalade.entities.TopoLoan;
import com.ocesclade.amisdeescalade.entities.User;
import com.ocesclade.amisdeescalade.enumerated.RoleEnum;
import com.ocesclade.amisdeescalade.enumerated.TopoLoanStatusEnum;
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
		
		List<TopoLoan> myTopoLoanList = topoLoanRepository.findAllByLender(u.getEmail());
		model.addAttribute("myTopoLoanList" , myTopoLoanList );
		
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
		return "redirect:/user/profil#nav-topos";
	}
	
	@GetMapping("/user/update-topoloan")
	public String updateUserTopoLoan(
			@RequestParam(name="id", required = false) Long topoloanId,
			@RequestParam(name="action", required = false) String action,
			Model model
		) {
		User u = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		LOGGER.info("user UPDATE TopoLoan id {}", topoloanId);
		TopoLoan topoloan = topoLoanRepository.findTopoLoanById(topoloanId);
		Topo topo = topoloan.getTopo();
		
		if (action.equalsIgnoreCase("accept")) {
		topo.setIsAvailableForLoan(false);
		topoloan.setTopoLoanStatus(TopoLoanStatusEnum.ACCEPTED);
		topoRepository.save(topo);
		}
		
		if (action.equalsIgnoreCase("close")) {		
		topoloan.setTopoLoanStatus(TopoLoanStatusEnum.REFUSED);
		}
		
		if (action.equalsIgnoreCase("delete")) {		
		topoloan.setTopoLoanStatus(TopoLoanStatusEnum.CLOSED);
		}
		
		if (action.equalsIgnoreCase("deletedmyself")) {		
		topoloan.setTopoLoanStatus(TopoLoanStatusEnum.CLOSED);
		topoLoanRepository.save(topoloan);
		model.addAttribute("myTopoLoanList", topoLoanRepository.findAllByBorrower(u.getEmail()));
		return "redirect:/user/profil#nav-toposloan";
		}
		
		topoLoanRepository.save(topoloan);
		model.addAttribute("userTopoLoanList", topoLoanRepository.findAllByLender(u.getEmail()));
		return "redirect:/user/profil#nav-topos";
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
		return "redirect:/admin/profil#nav-area";
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
		return "redirect:/admin/profil#nav-comments";
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
		return "redirect:/admin/profil#nav-comments";
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
		return "redirect:/admin/profil#nav-topos";
	}
	
	@GetMapping("/admin/edit-user")
	public String editUser (
			@RequestParam(name="id", required = false) Long userId,
			Model model
			) {
		LOGGER.info("EDIT User id {}", userId);
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		return "/admin/edit-user";
	}
	
	@PostMapping("/admin/update-user")
	public String updateUser (
			@RequestParam(name="id", required = false) Long userId,
			@RequestParam(name="role", required = false) String role,
			Model model
		) {
		LOGGER.info("UPDATE User id {}", userId);
		User user = userService.findById(userId);
		user.setRole(RoleEnum.of(role));
		userRepository.save(user);
		model.addAttribute("userList" , userRepository.findAll());
		return "redirect:/admin/profil#nav-users";
	}
}
