package com.ocesclade.amisdeescalade.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocesclade.amisdeescalade.dto.CommentDto;
import com.ocesclade.amisdeescalade.entities.Comment;
import com.ocesclade.amisdeescalade.repository.ClimbCommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	private ClimbCommentRepository climbCommentRepository;

	@Override
	public Comment save(CommentDto commentDto) {
		LOGGER.info("Enregistrement du commentaire {}", commentDto.toString());
		Comment comment = new Comment();
		comment.setTitle(commentDto.getTitle());
		comment.setContent(commentDto.getContent());
		comment.setAuthor(commentDto.getAuthor());
		comment.setArea(commentDto.getArea());
		return climbCommentRepository.save(comment);
	}
	

}