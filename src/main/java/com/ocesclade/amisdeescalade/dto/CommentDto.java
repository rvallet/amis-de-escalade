package com.ocesclade.amisdeescalade.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ocesclade.amisdeescalade.entities.Area;


public class CommentDto {
	
	@NotEmpty
	@Size(min = 5, max = 75)
	private String title;
	
	@NotEmpty
	@Size(min = 5, max = 1000)
	private String content;
	
	private String author;
	
	@Valid
	private Area area;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	

}
