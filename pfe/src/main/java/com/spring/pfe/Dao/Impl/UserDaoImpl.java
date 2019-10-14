package com.spring.pfe.Dao.Impl;

import com.spring.pfe.Dao.UserDao;
import com.spring.pfe.Entity.Administrateur;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;
import com.spring.pfe.Entity.User;

public class UserDaoImpl implements UserDao {

	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	

	PatientDaoImpl patientdao=new PatientDaoImpl();
	MedecinDaoImpl medecindao=new MedecinDaoImpl();
	AdministrateurDaoImpl admindao=new AdministrateurDaoImpl();
	
	
	/*========================================================================================================================*/
	/*=========== Seconnecter : retourner null si utilisateur n'existe pas   ================================================*/
	/*=========== Seconnecter : retourner object user =======================================*/
	/*========================================================================================================================*/
	@Override
	public User Seconnecter(String username, String password)
	{
		
		Patient patient=patientdao.getByLoginPassword(username, password);
		Medecin medecin=medecindao.getByLoginPassword(username, password);
		Administrateur admin=admindao.getByLoginPassword(username, password);

		
		if(patient!=null)
		{
			/* si Patient existe*/
			
			return patient;
		}
		else if(medecin!=null)
		{
			
			/* si Medecin  existe*/
			
			return medecin;
		}
		else if(admin!=null)
		{
			/* si Admin  existe*/
			
			return admin;
		}
		
	       /*si le compte n'existe pas*/
		
			return null;	
	}
	
	/*========================================================================================================================*/
	/*=========== Seconnecter : retourner null si utilisateur n'existe pas   ================================================*/
	/*=========== Seconnecter : retourn object user =======================================*/
	/*========================================================================================================================*/
  
	@Override
	public User getByCin(String cin)
	{
		
		Patient patient=patientdao.getByCin(cin);
		Medecin medecin=medecindao.getByCin(cin);
		Administrateur admin=admindao.getByCin(cin);
		
		
		/* si le patient existe*/
		
		if(patient!=null)
		{
			return patient;
		}
		
		/* si le medecin existe*/
		
		else if(medecin!=null)
		{	
			return medecin;
		}
		
		/* si l'admin existe*/
		
		else if(admin!=null)
		{
			
			return admin;
		}
		
	       /*si le compte n'existe pas*/
		
			return null;	
	}
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
}
