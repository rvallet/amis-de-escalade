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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="topo")
public class Topo implements Serializable {

	private static final long serialVersionUID = 9115889704052440473L;

	@Id
	@Column(name="id_topo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 40)
	private String name;

	@NotNull
	@Size(min = 15, max = 250)
	private String shortDescription;
	
	private String location;
	
	private Date releaseDate;
	
	private Boolean isAvailableForLoan;
	
	private String belongTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;	
	

	public Topo() {
		super();
		this.name="";
		this.shortDescription="";
	}

	public Topo(@NotNull @Size(min = 5, max = 40) String name,
			@NotNull @Size(min = 15, max = 250) String shortDescription, String location, Date releaseDate,
			Boolean isAvailableForLoan, String belongTo, User user) {
		super();
		this.name = name;
		this.shortDescription = shortDescription;
		this.location = location;
		this.releaseDate = releaseDate;
		this.isAvailableForLoan = isAvailableForLoan;
		this.belongTo = belongTo;
		this.user = user;
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


	public String getShortDescription() {
		return shortDescription;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Date getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}


	public Boolean getIsAvailableForLoan() {
		return isAvailableForLoan;
	}


	public void setIsAvailableForLoan(Boolean isAvailableForLoan) {
		this.isAvailableForLoan = isAvailableForLoan;
	}


	public String getBelongTo() {
		return belongTo;
	}


	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Topo [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", location=" + location
				+ ", releaseDate=" + releaseDate + ", isAvailableForLoan=" + isAvailableForLoan + ", belongTo="
				+ belongTo + ", user=" + user + "]";
	}	
	
}
