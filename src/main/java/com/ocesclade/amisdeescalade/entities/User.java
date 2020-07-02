package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ocesclade.amisdeescalade.enumerated.RoleEnum;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {

	private static final long serialVersionUID = -5842824149114684177L;
	
	@Id
	@Column(name="id_user")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String pseudo;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	private String resetToken;
	
	private Date creationDate;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Collection<Topo> topos;
	
	public User() {
		super();
		this.creationDate= Calendar.getInstance().getTime();
	}	
	
	public User(String email, String password, RoleEnum role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.creationDate= Calendar.getInstance().getTime();
	}
	
	public User(String email, String pseudo, String lastName, String firstName, String password, RoleEnum role) {
		super();
		this.email = email;
		this.pseudo = pseudo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.role = role;
		this.creationDate= Calendar.getInstance().getTime();
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
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Collection<Topo> getTopos() {
		return topos;
	}

	public void setTopos(Collection<Topo> topos) {
		this.topos = topos;
	}

	@Override
	public String toString() {
		return "User [pseudo=" + pseudo + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", role=" + role + ", resetToken=" + resetToken + ", creationDate="
				+ creationDate + "]";
	}
	
}
