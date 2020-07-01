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
	@Size(min = 5, max = 75)
	private String name;
	
	@NotNull
	@Size(min = 5, max = 250)
	private String description;
	
	private Date releaseDate;
	
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private Collection<Sector> sectors;
	
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private Collection<Comment> comments;	

	public Area() {
		super();
		this.name="";
		this.description="";
		this.releaseDate=new Date();
	}
	
	public Area(
			@NotNull @Size(min = 5, max = 75) String name,
			@NotNull @Size(min = 5, max = 250) String description
			) {
		this.name = name;
		this.description = description;
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Collection<Sector> getSector() {
		return sectors;
	}

	public void setSector(Collection<Sector> sector) {
		this.sectors = sector;
	}

	public Collection<Comment> getComment() {
		return comments;
	}

	public void setComment(Collection<Comment> comment) {
		this.comments = comment;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + ", description=" + description + ", releaseDate="
				+ releaseDate + ", climbingSector=" + sectors + ", climbingComment=" + comments + "]";
	}
	
}