package com.spring.pfe.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.spring.pfe.Connection.CassandraConnection;
import com.spring.pfe.Dao.PatientDao;
import com.spring.pfe.Entity.Chat;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;

public class PatientDaoImpl implements PatientDao  {
	
	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter Patient  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public void Ajouter(Patient p)
	{
		
		prepared = null;
		prepared = session.prepare("insert into pfe.patient (cin,nomprenom,login,password,adresse ,"
				+ "telephone ,domicile ,email ,date ,medecin,image) values (?,?,?,?,?,?,?,?,?,?,?) ");

		

		session.execute(prepared.bind(p.getCin(),p.getNomprenom(),p.getLogin() ,p.getPassword(),
				p.getAdresse(),p.getTelephone(),p.getDomicile(),p.getEmail(),p.getDate() , p.getMedecin().getCin(),
				p.getImage()));
		
	}
	

	/*========================================================================================================================*/
   	/*=========================================  Retourner le nombre des Patients  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public int getCountPatient()
	{
		List<Patient> liste=getAllPatients();

		return liste.size();
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Liste Patients  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public List<Patient> getAllPatients()
	{
		ArrayList<Patient> liste =new ArrayList<>();
		MedecinDaoImpl medecindao=new MedecinDaoImpl();
		
		rs = session.execute("select * from pfe.patient");

		for (Row ligne : rs) {

			Patient p=new Patient();
			
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setDate(ligne.getString("date"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));
			
			Medecin m = medecindao.getByCin(ligne.getString("medecin"));			
			p.setMedecin(m);
			
			liste.add(p);

		}
		
		return liste;
	}

	/*========================================================================================================================*/
   	/*=========================================  Liste Patients By Medecin ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public List<Patient> getAllPatientsByMedecin(String cinMedecin)
	{
		
		ChatDaoImpl daoChat=new ChatDaoImpl();
		try {

			ArrayList<Patient> liste =new ArrayList<>();
			
			prepared = null;
			prepared = session.prepare("select * from pfe.patient where medecin=?");
			
			rs = session.execute(prepared.bind(cinMedecin));
	
			for (Row ligne : rs) {
	
				Patient p=new Patient();
				String cinPatient=ligne.getString("cin");
				p.setAdresse(ligne.getString("adresse"));
				p.setEmail(ligne.getString("email"));
				p.setTelephone(ligne.getString("telephone"));
				p.setDate(ligne.getString("date"));
				p.setDomicile(ligne.getString("domicile"));
				p.setNomprenom(ligne.getString("nomprenom"));
				p.setPassword(ligne.getString("password"));
				p.setImage(ligne.getString("image"));
				p.setLogin(ligne.getString("login"));
				p.setCin(cinPatient);
				
				Medecin m = new MedecinDaoImpl().getByCin(cinMedecin);
				
				List<Chat> listchat=daoChat.getAllChatsMedecinWithPatient(cinMedecin, cinPatient);
				
				p.setListChats(listchat);
				
				p.setMedecin(m);
				
				liste.add(p);

			}
			
			return liste;
		
		} catch (Exception e) { System.err.println(e.getMessage());return null; }
		
		
	}


	/*========================================================================================================================*/
   	/*=========================================  Modifier Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public void Modifier(Patient p)
	{
		prepared = null;
		prepared = session.prepare("UPDATE  pfe.patient SET nomprenom=?,login=?,password=?,adresse=?,"
				+ "telephone=?,domicile=?,email=?,date=?,medecin=?,image=? WHERE cin=?");

		

		session.execute(prepared.bind(p.getNomprenom(),p.getLogin() ,p.getPassword(),
		p.getAdresse(),p.getTelephone(),p.getDomicile(),p.getEmail(),p.getDate() , p.getMedecin().getCin(),
		p.getImage(),p.getCin()));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
   public void Supprimer(String cin)
	{
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.patient WHERE  cin=?");
		
		session.execute(prepared.bind(cin));
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@Override	
	public void SupprimerByMedecin(String cinMedecin)
	{
		List<Patient> list=getAllPatientsByMedecin(cinMedecin);
		
		for (Patient p: list) {
			
			Supprimer(p.getCin());
		}
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Get patient by cin  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public Patient getByCin(String cin)
	{

		prepared = null;
		prepared = session.prepare("select * from pfe.patient WHERE cin=?");

		rs=session.execute(prepared.bind(cin));
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Patient p=new Patient();
			
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setDate(ligne.getString("date"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));
			
			Medecin m = new MedecinDaoImpl().getByCin(ligne.getString("medecin"));			
			p.setMedecin(m);
			
			return p;
		}
			return null;
			
	}
	
	/*========================================================================================================================*/
   	/*=========================================  chercher Patients par motcle   ================================================*/
   	/*========================================================================================================================*/
	
	@Override	
	public List<Patient> getAllPatientsByMotCle(String mc)
	{
		List<Patient> liste=getAllPatients();
		List<Patient> listFinal=new ArrayList<>();
		for (Patient p : liste) {
			
			if(p.getCin().contains(mc) ||p.getNomprenom().contains(mc) || p.getMedecin().getNomprenom().contains(mc) )
			{
				listFinal.add(p);
			}
		}
		return listFinal;
	}
	
	/*========================================================================================================================*/
   	/*=========================================  chercher Patients par login et password   ===============================*/
   	/*========================================================================================================================*/
	

	@Override
	public Patient getByLoginPassword(String login, String Password) {
		
		prepared = null;
		prepared = session.prepare("select * from pfe.patient where login=? and password=? ALLOW FILTERING;");

		rs=session.execute(prepared.bind(login,Password));
		
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Patient p=new Patient();
			
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setDate(ligne.getString("date"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));
			
			Medecin m = new MedecinDaoImpl().getByCin(ligne.getString("medecin"));			
			p.setMedecin(m);
			
			return p;
		}
			return null;
	}

	/*========================================================================================================================*/
   	/*============================ retourner les patients qui ont envoyer des message a leur medecin   =================*/
   	/*========================================================================================================================*/
	
	@Override
	public List<Patient> getAllPatientsContactMedecin(String cinMedecin) {
		
		ChatDaoImpl daoChat=new ChatDaoImpl();
		List<Patient> listInitiale=getAllPatientsByMedecin(cinMedecin);
		List<Patient> listPatient=new ArrayList<>();
		
		for (int i = 0; i < listInitiale.size(); i++) {
			
			Patient p=listInitiale.get(i);
			
			List<Chat> listChat=daoChat.getAllChatsMedecinWithPatient(cinMedecin, p.getCin());
			int nb=listChat.size();
			
			// si nb > 0 donc le patient a deja envoyer message vers le medecin 
			if(nb>0)
			{
				listPatient.add(p);
			}
		}
		
		return listPatient;
	}
	
	/*========================================================================================================================*/
   	/*=========================================    ================================================*/
   	/*========================================================================================================================*/
	
}
