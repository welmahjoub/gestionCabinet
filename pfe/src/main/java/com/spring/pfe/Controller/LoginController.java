package com.spring.pfe.Controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.pfe.Dao.Impl.UserDaoImpl;
import com.spring.pfe.Entity.Administrateur;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;
import com.spring.pfe.Entity.User;


@Controller
public class LoginController {
	

	/*========================================================================================================================*/
   	/*============= Description : Controller qui gère la gestion d'authentification pour les differents Utilisateurs : ====*/
   	/*========================================================================================================================*/

	/*========================================================================================================================*/
   	/*========================================= Attribut  ================================================*/
   	/*========================================================================================================================*/
	
	UserDaoImpl daologin=new UserDaoImpl();

	/*========================================================================================================================*/
   	/*========================================= Afficher la  Page Login   ================================================*/
   	/*========================================================================================================================*/
		
	
	@GetMapping("/")
	public String login(Model model) {

		model.addAttribute("login", "");
		model.addAttribute("password", "");

		
		return "login/login";
	}
	


	/*========================================================================================================================*/
   	/*=========================================  User se déconnecter     ================================================*/
   	/*========================================================================================================================*/
	
	
	@GetMapping("/Logout")
	public String Logout() {

		return "redirect:/";
	}

	
	/*========================================================================================================================*/
   	/*================================= Verifier Connection et faire redirection   ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/seconneter", method = RequestMethod.POST)
	public String seconnecter(String login,String password,Model model,HttpSession session) {

		User user = daologin.Seconnecter(login, password);
		
		/* si compte existe */
		if(user!=null)
		{ 

				if(user instanceof Administrateur)
				{
					session.setAttribute("CinAdminConnecte", user.getCin());
					/* redirection vers page Home admin*/
					return "redirect:/Admin/";
				}
	
				if(user instanceof Medecin)
				{
					session.setAttribute("CinMedecinConnecte", user.getCin());
					/* redirection vers page Home medecin*/
					return "redirect:/Medecin/";
				}
				
				if(user instanceof Patient)
				{
					session.setAttribute("CinPatientConnecte", user.getCin());
					/* redirection vers page Home patient*/
					return "redirect:/Patient/";
					
				}
				
                /*si aucun type a été retourné*/
				return "redirect:/";
		}
		else {
			
			/* si le compe n'existe pas */
			
			model.addAttribute("login", login);
			model.addAttribute("password", password);
			model.addAttribute("compteInvalid", true);;
			
			return "login/login";
		}
	
		
	}
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	
	
}
