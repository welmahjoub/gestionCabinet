package com.spring.pfe.Entity;

import java.util.UUID;

public class Forum {
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	private UUID id;
	private String titre;
	private String contenu;
	private String date;
	private User publisher;
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	public Forum() {
		super();
	}



	public Forum(UUID id, String titre, String contenu, String date, User publisher) {
		super();
		this.id = id;
		this.titre = titre;
		this.contenu = contenu;
		this.date = date;
		this.publisher = publisher;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}



	@Override
	public String toString() {
		return "Forum [id=" + id + ", titre=" + titre + ", contenu=" + contenu + ", date=" + date + ", publisher="
				+ publisher + "]";
	}





	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
