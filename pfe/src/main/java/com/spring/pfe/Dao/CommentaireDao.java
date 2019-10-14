package com.spring.pfe.Dao;

import java.util.List;
import java.util.UUID;

import com.spring.pfe.Entity.Commentaire;

public interface CommentaireDao {
	
	/*========================================================================================================================*/
   	/*=========================================  Methods ================================================*/
   	/*========================================================================================================================*/

	public void Ajouter(Commentaire Commenatire);
	public void Modifier(Commentaire Commenatire);
	public void Supprimer(UUID id);
	public void SupprimerCommentsByForum(UUID idForum);
	
	public List<Commentaire> getAllCommentsByForum(UUID idForum);

	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
