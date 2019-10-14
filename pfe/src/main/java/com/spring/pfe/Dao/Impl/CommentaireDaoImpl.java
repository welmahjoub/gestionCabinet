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
import com.spring.pfe.Dao.CommentaireDao;
import com.spring.pfe.Entity.Commentaire;
import com.spring.pfe.Entity.Forum;
import com.spring.pfe.Entity.User;

public class CommentaireDaoImpl implements CommentaireDao {
	
	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter commentaire  ================================================*/
   	/*========================================================================================================================*/

	
	@Override
	public void Ajouter(Commentaire Commenatire) {
	
		prepared = null;
		prepared = session.prepare("insert into pfe.commentaire (id,contenue,date, cin_publisher,id_forum) values"
				+ " (?,?,?,?,?) ");

		
		session.execute(prepared.bind(UUIDs.timeBased(),Commenatire.getContenu(),Commenatire.getDate(),
				Commenatire.getPublisher().getCin(),Commenatire.getForum().getId()));
		
	}
	/*========================================================================================================================*/
   	/*========================================= Modifier commentaire ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Modifier(Commentaire Commenatire) {
		
		
		prepared = null;
		prepared = session.prepare("update pfe.commentaire set contenue=?, date=?, cin_publisher=?,"
				+ "id_forum=? where id=?");

		
		session.execute(prepared.bind(Commenatire.getContenu(),Commenatire.getDate(),
				Commenatire.getPublisher().getCin(),Commenatire.getForum().getId(),Commenatire.getId()));
		
	}
	/*========================================================================================================================*/
   	/*=========================================  Supprimer commentaire ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Supprimer(UUID id) {
		
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.commentaire WHERE  id=?");
		
		session.execute(prepared.bind(id));
		
	}
	
	/*========================================================================================================================*/
   	/*========================================= Supprimer Comments d'une publication   ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void SupprimerCommentsByForum(UUID idForum) {
		
		List<Commentaire> listeComments = getAllCommentsByForum(idForum);
		
		for (Commentaire p : listeComments)
		
		{		
				Supprimer(p.getId());
		}	
			
	}

	
	/*========================================================================================================================*/
   	/*========================================= getAllCommentsByForum   ================================================*/
   	/*========================================================================================================================*/

	@Override
	public List<Commentaire> getAllCommentsByForum(UUID idForum) {
	
		ArrayList<Commentaire> liste =new ArrayList<>();
        
        UserDaoImpl Userdao=new UserDaoImpl();
        ForumDaoImpl forumDao=new ForumDaoImpl();
		
    	prepared = null;
		prepared = session.prepare("select * FROM  pfe.commentaire WHERE  id_forum=?");
		
		rs=session.execute(prepared.bind(idForum));
		
		for (Row ligne : rs) {

			Commentaire p=new Commentaire();
			
			p.setId(ligne.getUUID("id"));
			p.setContenu(ligne.getString("contenue"));
			p.setDate(ligne.getString("date"));
			
			User publisher=Userdao.getByCin(ligne.getString("cin_publisher"));
			p.setPublisher(publisher);
			
            UUID idf=ligne.getUUID("id_forum");
            
			Forum f=forumDao.getById(idf);
			p.setForum(f);
			
			liste.add(p);
		}
		
		return liste;	
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
