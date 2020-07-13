package com.ocesclade.amisdeescalade.enumerated;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum ClimbingGradeEnum {

	UN("1"), 
	DEUX ("2"), 
	TROIS ("3"), 
	QUATRES ("4"), 
	CINQ_A ("5a"), 
	CINQ_B ("5b"), 
	CINQ_C ("5c"), 
	SIX_A ("6a"), 
	SIX_A_PLUS ("6a+"), 
	SIX_B ("6b"), 
	SIX_B_PLUS ("6b+"), 
	SIX_C ("6c"), 
	SIX_C_PLUS ("6c+"), 
	SEPT_A ("7a"), 
	SEPT_A_PLUS ("7a+"), 
	SEPT_B ("7b"), 
	SEPT_B_PLUS ("7b+"), 
	SEPT_C ("7c"), 
	SEPT_C_PLUS ("7c+"), 
	HUIT_A ("8a"), 
	HUIT_A_PLUS ("8a+"), 
	HUIT_B ("8b"), 
	HUIT_B_PLUS ("8b+"), 
	HUIT_C ("8c"), 
	HUIT_C_PLUS ("8c+"), 
	NEUF_A ("9a"), 
	NEUF_A_PLUS ("9a+"), 
	NEUF_B ("9b"), 
	NEUF_B_PLUS ("9b+"), 
	NEUF_C ("9c");

	private final String climbingGrade;
	
	public static ClimbingGradeEnum of(String value) {
		return Arrays.stream(values()).filter(v -> v.toString().equalsIgnoreCase(value)).findFirst().orElse(null);
	}

	ClimbingGradeEnum(String climbingGrade){
		this.climbingGrade = climbingGrade;
	}

	@Override
	public String toString(){
		return climbingGrade;
	}	

}
