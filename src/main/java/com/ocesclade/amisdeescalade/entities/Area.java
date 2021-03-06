package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Area implements Serializable {

	private static final long serialVersionUID = -1760533882442155084L;

	@Id
	@Column(name="id_area")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 75)
	private String name;
	
	@NotNull
	@Size(min = 1, max = 1000)
	private String description;
	
	private String shortDescription;
	
	@NotNull
	private String author;
	
	private Boolean isPromoted;	
	
	private Date releaseDate;
	
	private String imgPathThAttribute;

	@NotNull
	@Size(min = 1, max = 75)
	private String location;
	
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private Collection<Sector> sectorList;
	
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private Collection<Comment> commentList;	

	public Area() {
		super();
		this.name="";
		this.description="";
		this.setShortDescription(description);
		this.location = "";
		this.author="";
		this.releaseDate=new Date();
	}
	
	public Area(
			@NotNull @Size(min = 1, max = 75) String name,
			@NotNull @Size(min = 1, max = 1000) String description,
			@NotNull @Size(min = 1, max = 75) String location,
			@NotNull String author
			) {
		this.name = name;
		this.description = description;
		this.setShortDescription(description);
		this.location = location;
		this.author = author;
		this.releaseDate=new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getIsPromoted() {
		return isPromoted;
	}

	public void setIsPromoted(Boolean isPromoted) {
		this.isPromoted = isPromoted;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getImgPathThAttribute() {
		return imgPathThAttribute;
	}

	public void setImgPathThAttribute(String imgPathThAttribute) {
		this.imgPathThAttribute = imgPathThAttribute;
	}

	public Collection<Sector> getSectorList() {
		return sectorList;
	}

	public void setSectorList(Collection<Sector> sectorList) {
		this.sectorList = sectorList;
	}

	public Collection<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(Collection<Comment> commentList) {
		this.commentList = commentList;
	}	

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription.length() > 75 ? shortDescription.substring(0, 72)+"...": shortDescription;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + ", description=" + description + ", shortDescription="
				+ shortDescription + ", author=" + author + ", isPromoted=" + isPromoted + ", releaseDate="
				+ releaseDate + ", imgPathThAttribute=" + imgPathThAttribute + ", location=" + location + "]";
	}

	
}
