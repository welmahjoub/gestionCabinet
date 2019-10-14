package com.spring.pfe.Entity;

import java.util.UUID;

public class Chat {
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	private UUID id;
	private String message;
	private String date;
	private User emetteur;
	private User recepteur;
	
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	public Chat() {
		super();
	}



	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public User getEmetteur() {
		return emetteur;
	}



	public void setEmetteur(User emetteur) {
		this.emetteur = emetteur;
	}



	public User getRecepteur() {
		return recepteur;
	}



	public void setRecepteur(User recepteur) {
		this.recepteur = recepteur;
	}



	public Chat(UUID id, String message, String date, User emetteur, User recepteur) {
		super();
		this.id = id;
		this.message = message;
		this.date = date;
		this.emetteur = emetteur;
		this.recepteur = recepteur;
	}



	@Override
	public String toString() {
		return "Chat [id=" + id + ", message=" + message + ", date=" + date + ", emetteur=" + emetteur + ", recepteur="
				+ recepteur + "]";
	}


	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	
}
