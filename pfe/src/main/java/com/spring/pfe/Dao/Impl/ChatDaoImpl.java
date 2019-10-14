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
import com.spring.pfe.Dao.ChatDao;
import com.spring.pfe.Entity.Chat;
import com.spring.pfe.Entity.User;

public class ChatDaoImpl implements ChatDao {
	
	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter chat  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Ajouter(Chat chat) {
		
		prepared = null;
		prepared = session.prepare("insert into pfe.chat (id, cin_emeteur, cin_recepteur,message,date) values"
				+ " (?,?,?,?,?) ");

		
		session.execute(prepared.bind(UUIDs.timeBased(),chat.getEmetteur().getCin(),chat.getRecepteur().getCin(),
				chat.getMessage(),chat.getDate()));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer chat  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public void Supprimer(UUID id) {
		
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.chat WHERE  id=?");
		
		session.execute(prepared.bind(id));
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  get all chats  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public List<Chat> getAllChats()
	{
		ArrayList<Chat> liste =new ArrayList<>();
		UserDaoImpl Userdao=new UserDaoImpl();
		
		rs = session.execute("select * from pfe.chat");

		for (Row ligne : rs) {

			Chat p=new Chat();
			
			p.setId(ligne.getUUID("id"));
			
			User emeteur=Userdao.getByCin(ligne.getString("cin_emeteur"));
			p.setEmetteur(emeteur);
			
			User recpeteur=Userdao.getByCin(ligne.getString("cin_recepteur"));
			p.setRecepteur(recpeteur);
			
			p.setMessage(ligne.getString("message"));
			p.setDate(ligne.getString("date"));
			
			liste.add(p);
		}
		
		return liste;	
	}

	
	/*========================================================================================================================*/
   	/*=========================================  get all chats entre un patient et un medecin  ================================================*/
   	/*========================================================================================================================*/

	@Override
	public List<Chat> getAllChatsMedecinWithPatient(String cinMedecin,String cinPatient)
	{
		List<Chat> liste = getAllChats();
		
		List<Chat> listFinal=new ArrayList<>();
		
		for (Chat p : liste) {
			
			if((p.getEmetteur().getCin().equals(cinPatient) && p.getRecepteur().getCin().equals(cinMedecin)) ||
					(p.getEmetteur().getCin().equals(cinMedecin) && p.getRecepteur().getCin().equals(cinPatient))	)	
			{
				listFinal.add(p);
			}
		}
		
		return listFinal;   
	}

	/*========================================================================================================================*/
   	/*================================= SupprimerChat entre patient et son medecin ================================*/
   	/*========================================================================================================================*/

	@Override
	public void SupprimerChat(String cinMedecin, String cinPatient) {
		
		List<Chat> liste = getAllChatsMedecinWithPatient(cinMedecin, cinPatient);
		
		for (Chat c : liste) {
			
			Supprimer(c.getId());
			
		}
		
	}

	/*========================================================================================================================*/
   	/*========================================= nb de message entre medecin et son pateint ================================================*/
   	/*========================================================================================================================*/

	@Override
	public int getCountChatsMedecinWithPatient(String cinMedecin, String cinPatient) {
		
		
		return 0;
	}

	/*========================================================================================================================*/
   	/*=========================================  ================================================*/
   	/*========================================================================================================================*/

}
