package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.Comment;

public interface ClimbCommentRepository extends JpaRepository<Comment,Integer>{

	List<Comment> findCommentsByAreaId(Long areaId);
	List<Comment> findByAreaIdOrderByIdDesc(Long areaId);
	List<Comment> findCommentsByAuthor(String author);
	List<Comment> findAll();
	Comment findOneById(Long id);
}
