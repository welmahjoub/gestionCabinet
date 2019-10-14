package com.spring.pfe.Dao;

import java.util.List;

import com.spring.pfe.Entity.Administrateur;

public interface AdministrateurDao {

	/*========================================================================================================================*/
	/*=================================== Methodes  de l'interface  ================================================*/
	/*========================================================================================================================*/
	
	public void Ajouter(Administrateur admin);
	public void Modifier(Administrateur admin);
	public void Supprimer(String cin);
	public Administrateur getByCin(String cin);
	public int getCountAdmin();
	public List<Administrateur> getAllAdmin();
	public Administrateur getByLoginPassword(String login,String Password);
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
}
