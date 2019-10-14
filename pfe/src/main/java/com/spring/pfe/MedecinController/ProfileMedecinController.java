package com.spring.pfe.MedecinController;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Service.Service;




@Controller
@RequestMapping("/Medecin")
public class ProfileMedecinController {
	
	MedecinDaoImpl dao = new MedecinDaoImpl();
	Service service=new Service();
    
	/*========================================================================================================================*/
   	/*========================================= Afficher profil medecin  ================================================*/
   	/*========================================================================================================================*/

	
	@GetMapping("/Profile")
	public String Profile(Model model,HttpSession session) {
		
		// recuperer cin depuis la session 
		
		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
		Medecin MedecinConnecte=dao.getByCin(CinMedecinConnecte);
		
		model.addAttribute("medecin", MedecinConnecte);
		
		return "Medecin/Profile";
	}
	/*========================================================================================================================*/
   	/*=========================================  Enregistrer Profile medecin  ================================================*/
   	/*========================================================================================================================*/
 
	@RequestMapping(value = "/EnregistrerProfile", method = RequestMethod.POST)
	public String EnregistrerProfile(MultipartFile image,Model model,String cin,String login,String password,String nomprenom,
			String email,String adresse,String specialite,String telephone,String domicile)
	{
		String nomImage=null;
		Medecin medecin=dao.getByCin(cin);
		
		/* si utilisateur va modifier l'image */
		
		 if (!image.isEmpty())
		 {	
			 nomImage=image.getOriginalFilename();
			 service.CopierImage(image); 
	     } 
		 else {
			 
			 nomImage=medecin.getImage();
		 }

		 
		 medecin.setLogin(login);
		 medecin.setNomprenom(nomprenom);
		 medecin.setPassword(password);
		 medecin.setImage(nomImage);
		 medecin.setAdresse(adresse);
		 medecin.setDomicile(domicile);
		 medecin.setTelephone(telephone);
		 medecin.setEmail(email);
		 medecin.setSpecialite(specialite);
		
		 dao.Modifier(medecin);

		 
		 return "redirect:/Medecin/";
		 
	}

	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
