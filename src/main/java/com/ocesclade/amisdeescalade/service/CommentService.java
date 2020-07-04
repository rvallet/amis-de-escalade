package com.ocesclade.amisdeescalade.service;

import com.ocesclade.amisdeescalade.dto.CommentDto;
import com.ocesclade.amisdeescalade.entities.Comment;

public interface CommentService {
	
	Comment save(CommentDto commentDto);
	
}
