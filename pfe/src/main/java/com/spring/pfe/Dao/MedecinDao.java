package com.spring.pfe.Dao;

import java.util.List;

import com.spring.pfe.Entity.Medecin;

public interface MedecinDao {

	/*========================================================================================================================*/
	/*========================================= Methodes  de l'interface ================================================*/
	/*========================================================================================================================*/
	
	
	public void Ajouter(Medecin p);
	public void Modifier(Medecin p);
	public void Supprimer(String cin);
	public int getCountMedecins();
	public Medecin getByCin(String cin);
	
	public List<Medecin> getAllMedecins();
	public List<Medecin> getMedecinsByMotCle(String mc);
	public Medecin getByLoginPassword(String login,String Password);
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
}
