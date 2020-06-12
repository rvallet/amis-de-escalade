package com.ocesclade.amisdeescalade.pojo;

public class UserAdresse {
	
	private String libelle;
	private String code_postale;
	private String ville;
	private String pays;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCode_postale() {
		return code_postale;
	}
	public void setCode_postale(String code_postale) {
		this.code_postale = code_postale;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	@Override
	public String toString() {
		return "Adresse [libelle=" + libelle + ", code_postale=" + code_postale + ", ville=" + ville + ", pays=" + pays
				+ "]";
	}
}
