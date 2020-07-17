package com.ocesclade.amisdeescalade.enumerated;

import java.util.Arrays;

public enum RoleEnum {
	USER ("USER"),
	ADMIN ("ADMIN");
	
	private final String role;
	
	RoleEnum(String role){
		this.role = role;
	}
	
	@Override
	public String toString(){
		return role;
	}
	
	public static RoleEnum of(String value) {
		return Arrays.stream(values()).filter(v -> v.toString().equalsIgnoreCase(value)).findFirst().orElse(null);
	}
	
	
}
