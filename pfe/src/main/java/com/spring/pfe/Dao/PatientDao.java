package com.spring.pfe.Dao;

import java.util.List;

import com.spring.pfe.Entity.Patient;

public interface PatientDao {

	/*========================================================================================================================*/
	/*================================== Methodes  de l'interface  ================================================*/
	/*========================================================================================================================*/
	
	public void Ajouter(Patient p);
	public void Modifier(Patient p);
	public void Supprimer(String cin);
	public void SupprimerByMedecin(String cinMedecin);
	public Patient getByCin(String cin);
	
	public List<Patient> getAllPatients();
	public List<Patient> getAllPatientsByMedecin(String cinMedecin);
	public List<Patient> getAllPatientsContactMedecin(String cinMedecin);
	public List<Patient> getAllPatientsByMotCle(String mc);
	public Patient getByLoginPassword(String login,String Password);
	
	public int getCountPatient();
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
}
