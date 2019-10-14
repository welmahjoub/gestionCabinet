package com.spring.pfe.Entity;

import java.io.Serializable;

public class User  implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	
	protected String cin;
	protected String nomprenom;
	protected String login;
	protected String password;
	protected String image;
	
	/*========================================================================================================================*/
	/*=========================================   ================================================*/
	/*========================================================================================================================*/
	

	public User() {
		super();
		
	}

	public User(String cin, String nomprenom, String login, String password, String image) {
		super();
		this.cin = cin;
		this.nomprenom = nomprenom;
		this.login = login;
		this.password = password;
		this.image = image;
	}

	public User(String cin) {
		this.cin=cin;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getNomprenom() {
		return nomprenom;
	}

	public void setNomprenom(String nomprenom) {
		this.nomprenom = nomprenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [cin=" + cin + ", nomprenom=" + nomprenom + ", login=" + login + ", password=" + password
				+ ", image=" + image + "]";
	}
	
	/*========================================================================================================================*/
	/*=========================================  ================================================*/
	/*========================================================================================================================*/
	
	
}
