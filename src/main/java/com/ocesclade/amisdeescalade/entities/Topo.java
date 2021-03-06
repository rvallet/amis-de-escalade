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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@Size(min = 1, max = 75)
	private String name;

	@NotNull
	@Size(min = 1, max = 1000)
	private String description;
	
	private String shortDescription;
	
	private String location;
	
	private Date releaseDate;
	
	private Boolean isAvailableForLoan;
	
	private String belongTo;
	
	private Boolean isOnline;
	
	private String imgPathThAttribute;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;
	
	@OneToMany(mappedBy = "topo", fetch = FetchType.LAZY)
	private Collection<TopoLoan> topoLoan;
	
	public Topo() {
		super();
		this.name="";
		this.description="";
		this.isOnline=false;
		this.releaseDate = new Date();
	}

	public Topo(@NotNull @Size(min = 1, max = 75) String name,
			@NotNull @Size(min = 1, max = 1000) String description, 
			String location, 
			Boolean isAvailableForLoan, 
			String belongTo, 
			User user) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.releaseDate = new Date();
		this.isAvailableForLoan = isAvailableForLoan;
		this.isOnline=true;
		this.belongTo = belongTo;
		this.user = user;
		this.setShortDescription(description);
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
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription.length() > 75 ? shortDescription.substring(0, 72)+"...": shortDescription;
	}

	public Collection<TopoLoan> getTopoLoan() {
		return topoLoan;
	}

	public void setTopoLoan(Collection<TopoLoan> topoLoan) {
		this.topoLoan = topoLoan;
	}

	
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}	
	

	public String getImgPathThAttribute() {
		return imgPathThAttribute;
	}

	public void setImgPathThAttribute(String imgPathThAttribute) {
		this.imgPathThAttribute = imgPathThAttribute;
	}

	@Override
	public String toString() {
		return "Topo [id=" + id + ", name=" + name + ", description=" + description + ", shortDescription="
				+ shortDescription + ", location=" + location + ", releaseDate=" + releaseDate + ", isAvailableForLoan="
				+ isAvailableForLoan + ", belongTo=" + belongTo + ", isOnline=" + isOnline + ", imgPathThAttribute="
				+ imgPathThAttribute + ", user=" + user + ", topoLoan=" + topoLoan + "]";
	}

	
}
