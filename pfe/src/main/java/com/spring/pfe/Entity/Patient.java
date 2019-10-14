package com.spring.pfe.Entity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User{

	 
  /*========================================================================================================================*/
  /*========================================= Attrubut  ================================================*/
  /*========================================================================================================================*/
		
	private static final long serialVersionUID = 1L;
	
	
	private String adresse;
	private String email;
	private String telephone;
	private String domicile;
	private String date;
	private Medecin medecin;
	private List<Chat> listChats;
	
	
	/*========================================================================================================================*/
	/*========================================= Method  ================================================*/
	/*========================================================================================================================*/
	
	public Patient() {
		super();
		listChats=new ArrayList<>();
	
		
	}

	public Patient(String cin, String nomprenom, String login, String password, String image,String adresse, String email, String telephone, String domicile, String date, Medecin medecin) {
		
		super(cin, nomprenom, login, password, image);
		this.adresse = adresse;
		this.email = email;
		this.telephone = telephone;
		this.domicile = domicile;
		this.date = date;
		this.medecin = medecin;
		listChats=new ArrayList<>();
	}
	
	
	public List<Chat> getListChats() {
		return listChats;
	}

	public void setListChats(List<Chat> listChats) {
		this.listChats = listChats;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Medecin getMedecin() {
		return medecin;
	}
	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}	
	
	public String getAdresse() {
		return adresse;
	}

	@Override
	public String toString() {
		return "Patient [adresse=" + adresse + ", email=" + email + ", telephone=" + telephone + ", domicile="
				+ domicile + ", date=" + date + ", medecin=" + medecin + ", cin=" + cin + ", nomprenom=" + nomprenom
				+ ", login=" + login + ", password=" + password + ", image=" + image + "]";
	}
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
	
}
