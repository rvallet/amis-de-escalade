package com.ocesclade.amisdeescalade.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.ocesclade.amisdeescalade.enumerated.RoleEnum;
import com.ocesclade.amisdeescalade.security.ValidPassword;

public class UserRegistrationDto {

	@NotEmpty
	private String pseudo;
	
	private String lastName;
	
	private String firstName;
	
	@Email
	@NotEmpty
	private String email;

	@ValidPassword
	@NotEmpty
	private String password;
	
	private RoleEnum role;

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
}
