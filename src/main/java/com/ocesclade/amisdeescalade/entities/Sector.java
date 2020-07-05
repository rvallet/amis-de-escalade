package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Sector implements Serializable {

	private static final long serialVersionUID = -174272375542973429L;

	@Id
	@Column(name="id_sector")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 75)
	private String name;
	
	@NotNull
	@Size(min = 5, max = 250)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	private Area area;
	
	@OneToMany(mappedBy = "sector", fetch = FetchType.LAZY)
	private Collection<Route> routeList;

	public Sector() {
		super();
		this.name = "";
		this.description = "";
		this.area = new Area();
	}

	public Sector(
			@NotNull @Size(min = 5, max = 75) String name,
			@NotNull @Size(min = 5, max = 250) String description,
			Area area
			) {
		this.name = name;
		this.description = description;
		this.area = area;
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Collection<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(Collection<Route> routeList) {
		this.routeList = routeList;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + ", description=" + description + ", area=" + area + "]";
	}


			
}
