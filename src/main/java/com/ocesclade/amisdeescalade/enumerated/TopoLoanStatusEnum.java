package com.ocesclade.amisdeescalade.enumerated;

public enum TopoLoanStatusEnum {
	IN_PROGRESS ("En cours"),
	ACCEPTED ("Acceptée"),
	REFUSED ("Refusée"),
	CLOSED ("Fermée");
	
	private String topoLoanStatus;

	TopoLoanStatusEnum(String topoLoanStatus) {
		this.topoLoanStatus = topoLoanStatus;
	}
	
	@Override
	public String toString(){
		return topoLoanStatus;
	}	
}
