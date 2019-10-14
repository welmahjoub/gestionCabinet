package com.spring.pfe.Dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.spring.pfe.Connection.CassandraConnection;
import com.spring.pfe.Dao.ForumDao;
import com.spring.pfe.Entity.Forum;
import com.spring.pfe.Entity.User;

public class ForumDaoImpl implements ForumDao{
	
	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter forum  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Ajouter(Forum forum) {
		
		prepared = null;
		prepared = session.prepare("insert into pfe.forum (id, titre, contenue,date, cin_publisher) values"
				+ " (?,?,?,?,?) ");

		
		session.execute(prepared.bind(UUIDs.timeBased(),forum.getTitre(),forum.getContenu(),forum.getDate(),
				forum.getPublisher().getCin()));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Modifier forum  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Modifier(Forum forum) {
		
		prepared = null;
		prepared = session.prepare("update pfe.forum set titre=?, contenue=?, date=?, cin_publisher=? where id=?");

		
		session.execute(prepared.bind(forum.getTitre(),forum.getContenu(),forum.getDate(),
				forum.getPublisher().getCin(),forum.getId()));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer forum  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Supprimer(UUID id) {
		
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.forum WHERE  id=?");
		
		session.execute(prepared.bind(id));
		
		
	}

	/*========================================================================================================================*/
   	/*=========================================  get forum by id  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public Forum getById(UUID id) {
		
		prepared = null;
		prepared = session.prepare("select * from pfe.forum WHERE id=?");
		
		
		rs=session.execute(prepared.bind(id));
		
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Forum p=new Forum();
			
			p.setId(ligne.getUUID("id"));
			p.setTitre(ligne.getString("titre"));
			p.setContenu(ligne.getString("contenue"));
			p.setDate(ligne.getString("date"));
			
			
			User publisher=new UserDaoImpl().getByCin(ligne.getString("cin_publisher"));
			p.setPublisher(publisher);
			
			return p;
		}
			return null;
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  get all forums  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public List<Forum> getAllForums() {
		
        ArrayList<Forum> liste =new ArrayList<>();
        
        UserDaoImpl Userdao=new UserDaoImpl();
		
		rs = session.execute("select * from pfe.forum");

		for (Row ligne : rs) {

			Forum p=new Forum();
			
			p.setId(ligne.getUUID("id"));
			p.setTitre(ligne.getString("titre"));
			p.setContenu(ligne.getString("contenue"));
			p.setDate(ligne.getString("date"));
			
			
			User publisher=Userdao.getByCin(ligne.getString("cin_publisher"));
			p.setPublisher(publisher);
			
			liste.add(p);
		}
		
		return liste;	
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  get All Forums By Titre  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public List<Forum> getAllForumsByTitre(String titre) {
 		
		List<Forum> liste = getAllForums();
		
		List<Forum> listFinal=new ArrayList<>();
		
		for (Forum p : liste) {
			
			if(p.getTitre().contains(titre) )
			{
				listFinal.add(p);
			}
		}
		return listFinal;
		
	}
	/*========================================================================================================================*/
   	/*===========================  Supprimer les publications d'un user  ===================================*/
   	/*========================================================================================================================*/

	@Override
	public void SupprimerForumByPublisher(String cin_publisher) {
		
		List<Forum> liste = getAllForums();
		
		for (Forum p : liste)
		{		
			if(p.getPublisher().getCin().equals(cin_publisher) )
			{
				Supprimer(p.getId());
			}
		}	
	}
	
	/*========================================================================================================================*/
   	/*=========================================    ================================================*/
   	/*========================================================================================================================*/


}
