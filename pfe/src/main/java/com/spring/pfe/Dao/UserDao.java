package com.spring.pfe.Dao;

import com.spring.pfe.Entity.User;

public interface UserDao {
	
	/*========================================================================================================================*/
   	/*=========================================  Methods ================================================*/
   	/*========================================================================================================================*/

	public User Seconnecter(String username, String password);
	public User getByCin(String cin);
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}