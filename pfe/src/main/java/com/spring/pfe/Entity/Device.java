package com.spring.pfe.Entity;

import java.util.UUID;

public class Device {
	
	/*========================================================================================================================*/
   	/*=========================================  attributs ================================================*/
   	/*========================================================================================================================*/
	
	private UUID id;
	private String nom;
	private String description;
	private Patient patient;
	
	/*========================================================================================================================*/
   	/*=========================================  Methodes  ================================================*/
   	/*========================================================================================================================*/
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	
}
