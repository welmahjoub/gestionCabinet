package com.spring.pfe.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.spring.pfe.Connection.CassandraConnection;
import com.spring.pfe.Dao.MedecinDao;
import com.spring.pfe.Entity.Medecin;


public class MedecinDaoImpl implements MedecinDao {
	
	private Session session=CassandraConnection.getSession();;
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter Medecin  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public void Ajouter(Medecin p)
	{
		
		prepared = null;
		prepared = session.prepare("insert into pfe.medecin (cin,nomprenom,login,password,adresse ,"
				+ "telephone ,domicile ,email ,specialite ,image) values (?,?,?,?,?,?,?,?,?,?) ");

		

		session.execute(prepared.bind(p.getCin(),p.getNomprenom(),p.getLogin() ,p.getPassword(),
				p.getAdresse(),p.getTelephone(),p.getDomicile(),p.getEmail(),p.getSpecialite(),
				p.getImage()));
		
	}
	

	/*========================================================================================================================*/
   	/*=========================================  nb Medecins  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public int getCountMedecins()
	{
		List<Medecin> liste=getAllMedecins();

		return liste.size();
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Liste medecins  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public List<Medecin> getAllMedecins()
	{
		ArrayList<Medecin> liste =new ArrayList<>();
	
		
		rs = session.execute("select * from pfe.medecin");

		for (Row ligne : rs) {

			Medecin p=new Medecin();
			
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setSpecialite(ligne.getString("specialite"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));

			liste.add(p);

		}
		
		return liste;
	}

	/*========================================================================================================================*/
   	/*=========================================  Modifier Medecin  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public void Modifier(Medecin p)
	{
		prepared = null;
		prepared = session.prepare("UPDATE  pfe.medecin SET nomprenom=?,login=?,password=?,adresse=?,"
				+ "telephone=?,domicile=?,email=?,specialite=?, image=? WHERE cin=?");

		

		session.execute(prepared.bind(p.getNomprenom(),p.getLogin() ,p.getPassword(),
		p.getAdresse(),p.getTelephone(),p.getDomicile(),p.getEmail(),p.getSpecialite(),p.getImage(),p.getCin()));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer Medecin  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public void Supprimer(String cin)
	{
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.medecin WHERE  cin=?");
		
		session.execute(prepared.bind(cin));
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Get medecin By Cin  ================================================*/
   	/*========================================================================================================================*/
	@Override
	public Medecin getByCin(String cin)
	{
	
		prepared = null;
		prepared = session.prepare("select * from pfe.medecin WHERE cin=?");

		rs=session.execute(prepared.bind(cin));
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Medecin p=new Medecin();
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setSpecialite(ligne.getString("specialite"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));
			

			return p;
		}
			return null;
			
	}
	
	
	/*========================================================================================================================*/
   	/*========================================= Récupérer Medecins Par Motcle   ================================================*/
   	/*========================================================================================================================*/
	@Override
	
	public List<Medecin> getMedecinsByMotCle(String mc)
	{
		List<Medecin> liste=getAllMedecins();
		List<Medecin> listFinal=new ArrayList<>();
		
		for (Medecin p : liste) {
			
			if(p.getCin().contains(mc) ||p.getNomprenom().contains(mc) || p.getSpecialite().contains(mc) )
			{
				listFinal.add(p);
			}
		}
		return listFinal;
	}

	/*========================================================================================================================*/
   	/*========================================= getByLoginPassword  ================================================*/
   	/*========================================================================================================================*/
	

	@Override
	public Medecin getByLoginPassword(String login, String Password) {
		
		prepared = null;
		prepared = session.prepare("select * from pfe.medecin where login=? and password=? ALLOW FILTERING;");

		rs=session.execute(prepared.bind(login,Password));
		
		Row ligne=rs.one();

		if(ligne!=null)
		{
			
			Medecin p=new Medecin();
			p.setAdresse(ligne.getString("adresse"));
			p.setEmail(ligne.getString("email"));
			p.setTelephone(ligne.getString("telephone"));
			p.setSpecialite(ligne.getString("specialite"));
			p.setDomicile(ligne.getString("domicile"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			p.setImage(ligne.getString("image"));
			
			return p;
		}
			return null;
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	
}
