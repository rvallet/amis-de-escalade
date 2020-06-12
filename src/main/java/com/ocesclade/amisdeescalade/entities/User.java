package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.ocesclade.amisdeescalade.pojo.UserAdresse;
import com.ocesclade.amisdeescalade.utils.RoleEnum;

@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = -5842824149114684177L;
	
	@Id
	@Column(name="id_user")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String pseudo;
	
	private String nom;
	
	private String prenom;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	private String resetToken;
	
	
	public User() {
		super();
	}
	
	
	public User(@NotNull String email, @NotNull String password, RoleEnum role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}	
	
}
