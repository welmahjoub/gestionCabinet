package com.spring.pfe.AdminController;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.pfe.Dao.Impl.AdministrateurDaoImpl;
import com.spring.pfe.Entity.Administrateur;
import com.spring.pfe.Service.Service;




@Controller
@RequestMapping("/Admin")
public class ProfileAdminController {
	
	/*========================================================================================================================*/
   	/*================== Description : Controller qui gerer la gestion du profile de l'admin ==========*/
   	/*========================================================================================================================*/

	AdministrateurDaoImpl dao = new AdministrateurDaoImpl();
	Service service=new Service();
    
	/*========================================================================================================================*/
   	/*========================================= Afficher profil admin  ================================================*/
   	/*========================================================================================================================*/

	
	@GetMapping("/Profile")
	public String Profile(Model model,HttpSession session) {
		
		// recupere admin  depuis la session 
		
		String AdminConnecte =(String) session.getAttribute("CinAdminConnecte");
		
		Administrateur admin=dao.getByCin(AdminConnecte);
		
		model.addAttribute("admin", admin);
		
		return "Admin/Profile";
	}
	/*========================================================================================================================*/
   	/*=========================================  Enregistrer Profile Admin  ================================================*/
   	/*========================================================================================================================*/
 
	@RequestMapping(value = "/EnregistrerProfile", method = RequestMethod.POST)
	public String EnregistrerProfile(MultipartFile image,Model model,String cin,String login,String password,String nomprenom)
	{
		String nomImage=null;
		Administrateur admin=dao.getByCin(cin);
		
		/* si utilisateur choisi une nouvel image */
		 if (!image.isEmpty())
		 {	
			 nomImage=image.getOriginalFilename();
			 service.CopierImage(image); 
	     } 
		 else {
			 
			 nomImage=admin.getImage();
		 }

		 
		 admin.setLogin(login);
		 admin.setNomprenom(nomprenom);
		 admin.setPassword(password);
		 admin.setImage(nomImage);
		
		 dao.Modifier(admin);

		 model.addAttribute("admin", admin);
		 
		 return "redirect:/Admin/";
		 
	}

	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
