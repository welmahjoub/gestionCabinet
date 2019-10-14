package com.spring.pfe.Dao;

import java.util.List;
import java.util.UUID;

import com.spring.pfe.Entity.Chat;

public interface ChatDao {
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	
	public void Ajouter(Chat chat);
	public void Supprimer(UUID id);
	public void SupprimerChat(String cinMedecin,String cinPatient);
	public List<Chat> getAllChats();
    public List<Chat> getAllChatsMedecinWithPatient(String cinMedecin,String cinPatient);
    public int getCountChatsMedecinWithPatient(String cinMedecin,String cinPatient);
    
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
