package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;

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

import com.ocesclade.amisdeescalade.enumerated.ClimbingGradeEnum;

@Entity
public class Route implements Serializable {

	private static final long serialVersionUID = -7459691663387171587L;

	@Id
	@Column(name="id_route")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 75)
	private String name;
	
	@NotNull
	@Size(min = 5, max = 250)
	private String description;
	
	private String climbingGrade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sector")
	private Sector sector;

	public Route() {
		super();
		this.name="";
		this.description="";
		this.sector = new Sector();
	}

	public Route(
			@NotNull @Size(min = 5, max = 75) String name,
			@NotNull @Size(min = 5, max = 250) String description,
			ClimbingGradeEnum climbingGrade,
			Sector sector
			) {
		this.name = name;
		this.description = description;
		this.climbingGrade = climbingGrade.toString();
		this.sector = sector;
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public String getClimbingGrade() {
		return climbingGrade;
	}

	public void setClimbingGrade(ClimbingGradeEnum climbingGrade) {
		this.climbingGrade = climbingGrade.toString();
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", name=" + name + ", description=" + description + ", sector="
				+ sector + ", climbingGrade=" + climbingGrade + "]";
	}
	
		
}
