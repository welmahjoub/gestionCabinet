package com.spring.pfe.Entity;

import java.util.UUID;

public class DonnePhysiolog {

	/*========================================================================================================================*/
   	/*=========================================  attributs ================================================*/
   	/*========================================================================================================================*/
	
	private UUID id;
	private String rythmesCardiaques;
	private String natremie;
	private String tauxProlactine ;
	private String tensionArterielle ;
	private String temperatureCorporelle;
	private String glycemies;
	private String date;
	private String cinPatient;
	private String iddevice;
	
	
	/*========================================================================================================================*/
   	/*=========================================  methodes ================================================*/
   	/*========================================================================================================================*/
	
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getRythmesCardiaques() {
		return rythmesCardiaques;
	}
	public void setRythmesCardiaques(String rythmesCardiaques) {
		this.rythmesCardiaques = rythmesCardiaques;
	}
	public String getNatremie() {
		return natremie;
	}
	public void setNatremie(String natremie) {
		this.natremie = natremie;
	}
	public String getTauxProlactine() {
		return tauxProlactine;
	}
	public void setTauxProlactine(String tauxProlactine) {
		this.tauxProlactine = tauxProlactine;
	}
	public String getTensionArterielle() {
		return tensionArterielle;
	}
	public void setTensionArterielle(String tensionArterielle) {
		this.tensionArterielle = tensionArterielle;
	}
	public String getTemperatureCorporelle() {
		return temperatureCorporelle;
	}
	public void setTemperatureCorporelle(String temperatureCorporelle) {
		this.temperatureCorporelle = temperatureCorporelle;
	}
	public String getGlycemies() {
		return glycemies;
	}
	public void setGlycemies(String glycemies) {
		this.glycemies = glycemies;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCinPatient() {
		return cinPatient;
	}
	public void setCinPatient(String cinPatient) {
		this.cinPatient = cinPatient;
	}
	public String getIddevice() {
		return iddevice;
	}
	public void setIddevice(String iddevice) {
		this.iddevice = iddevice;
	}
	@Override
	public String toString() {
		return "DonnePhysiolog [id=" + id + ", rythmesCardiaques=" + rythmesCardiaques + ", natremie=" + natremie
				+ ", tauxProlactine=" + tauxProlactine + ", tensionArterielle=" + tensionArterielle
				+ ", temperatureCorporelle=" + temperatureCorporelle + ", glycemies=" + glycemies + ", date=" + date
				+ ", cinPatient=" + cinPatient + ", iddevice=" + iddevice + "]";
	}

	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/	
	
}
