package com.spring.pfe.Entity;

import java.util.UUID;

public class Commentaire {
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	private UUID id;
	private String contenu;
	private String date;
	private User publisher;
	private Forum forum;
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	public UUID getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Commenatire [id=" + id + ", contenu=" + contenu + ", date=" + date + ", publisher=" + publisher
				+ ", forum=" + forum + "]";
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public User getPublisher() {
		return publisher;
	}
	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	
	public Commentaire(UUID id, String contenu, String date, User publisher, Forum forum) {
		super();
		this.id = id;
		this.contenu = contenu;
		this.date = date;
		this.publisher = publisher;
		this.forum = forum;
	}
	public Commentaire() {
		super();
	}
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
