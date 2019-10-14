package com.spring.pfe.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.spring.pfe.Connection.CassandraConnection;
import com.spring.pfe.Dao.AdministrateurDao;
import com.spring.pfe.Entity.Administrateur;

public class AdministrateurDaoImpl  implements AdministrateurDao{

	/*========================================================================================================================*/
   	/*================== Description : Implementation de l'interface AdminDao  ==========*/
   	/*========================================================================================================================*/

	private Session session=CassandraConnection.getSession();;
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
	/*========================================= Ajouter admin  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public void Ajouter(Administrateur admin) {
		
		prepared = null;
		prepared = session.prepare("insert into pfe.admin (cin,nomprenom,login,password,image )"
				+ " values (?,?,?,?,?) ");

		session.execute(prepared.bind(admin.getCin(),admin.getNomprenom(),admin.getLogin(),
				admin.getPassword(),admin.getImage()));
		
	}

	/*========================================================================================================================*/
	/*========================================= Modifier admin  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public void Modifier(Administrateur admin) {
		
		prepared = null;
		prepared = session.prepare("UPDATE  pfe.admin SET nomprenom=?,login=?,password=?,"
				+ "image=? WHERE cin=?");

		

		session.execute(prepared.bind(admin.getNomprenom(),admin.getLogin(),admin.getPassword(),
				admin.getImage(),admin.getCin()));
		
	}

	/*========================================================================================================================*/
	/*========================================= Supprimer admin  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public void Supprimer(String cin) {
		
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.admin WHERE  cin=?");
		
		session.execute(prepared.bind(cin));
		
	}

	/*========================================================================================================================*/
	/*========================================= get admin By Cin  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public Administrateur getByCin(String cin) {
		
		prepared = null;
		prepared = session.prepare("select * from pfe.admin WHERE cin=?");

		rs=session.execute(prepared.bind(cin));
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Administrateur p=new Administrateur();
			
			p.setImage(ligne.getString("image"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));

			

			return p;
		}
			return null;		
	
	}
	
	/*========================================================================================================================*/
	/*========================================= get Count Admin  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public int getCountAdmin() {
		
		List<Administrateur> liste=getAllAdmin();

		return liste.size();
		
	}
	
	/*========================================================================================================================*/
	/*========================================= liste Admins  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public List<Administrateur> getAllAdmin() {
	
        ArrayList<Administrateur> liste =new ArrayList<>();
	
		rs = session.execute("select * from pfe.admin");

		for (Row ligne : rs) {

			Administrateur p=new Administrateur();
			
			p.setImage(ligne.getString("image"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));
			
			liste.add(p);

		}
		
		return liste;
		
	}
	/*========================================================================================================================*/
	/*========================================= recuperer Admin Par son lOgin Password   ==================================*/
	/*========================================================================================================================*/

	@Override
	public Administrateur getByLoginPassword(String login, String Password) {
	
		prepared = null;
		prepared = session.prepare("select * from pfe.admin where login=? and password=? ALLOW FILTERING;");

		rs=session.execute(prepared.bind(login,Password));
		
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Administrateur p=new Administrateur();
			
			p.setImage(ligne.getString("image"));
			p.setNomprenom(ligne.getString("nomprenom"));
			p.setPassword(ligne.getString("password"));
			p.setLogin(ligne.getString("login"));
			p.setCin(ligne.getString("cin"));

			return p;
		}
			return null;	
	}
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/

	
}
