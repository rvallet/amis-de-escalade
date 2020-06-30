package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 3006667379187218657L;
	
	@Id
	@Column(name="id_comment")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 5, max = 75)
	private String title;
	
	@NotNull
	@Size(min = 5, max = 250)
	private String content;
	
	private String author;
	
	private Date releaseDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	private Area area;

	public Comment() {
		super();
		this.title="";
		this.content="";
		this.releaseDate = new Date();
	}

	public Comment(
			@NotNull @Size(min = 5, max = 75) String title, 
			@NotNull @Size(min = 5, max = 250) String content, 
			String author,
			Area area) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.area = area;
		this.releaseDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", title=" + title + ", content=" + content + ", author=" + author
				+ ", releaseDate=" + releaseDate + ", area=" + area + "]";
	}

	
}
