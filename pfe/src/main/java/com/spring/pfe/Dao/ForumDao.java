package com.spring.pfe.Dao;

import java.util.List;
import java.util.UUID;

import com.spring.pfe.Entity.Forum;

public interface ForumDao {
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	public void Ajouter(Forum forum);
	public void Modifier(Forum forum);
	public void Supprimer(UUID id);
	public void SupprimerForumByPublisher(String cin_publisher);
	public Forum getById(UUID id);
	public List<Forum> getAllForums();
	public List<Forum> getAllForumsByTitre(String titre);

	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
