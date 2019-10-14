package com.spring.pfe.Entity;

public class Medecin extends User{
	
	/*========================================================================================================================*/
	/*========================================= Attrubut  ================================================*/
	/*========================================================================================================================*/
	
	private static final long serialVersionUID = 1L;

	private String adresse;
	private String email;
	private String telephone;
	private String domicile;
	private String specialite;
	

	/*========================================================================================================================*/
	/*========================================= Method  ================================================*/
	/*========================================================================================================================*/
	
	public Medecin() {
		super();
		
	}

	public Medecin(String cin, String nomprenom, String login, String password, String image,String adresse, String email, String telephone, String domicile, String specialite) {
		
		super(cin, nomprenom, login, password, image);
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.domicile = domicile;
		this.specialite = specialite;
	}

	public Medecin(String cin) {
		this.cin=cin;
	}

	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	@Override
	public String toString() {
		return "Medecin [adresse=" + adresse + ", email=" + email + ", telephone=" + telephone + ", domicile="
				+ domicile + ", specialite=" + specialite + ", cin=" + cin + ", nomprenom=" + nomprenom + ", login="
				+ login + ", password=" + password + ", image=" + image + "]";
	}

	
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
	
}
