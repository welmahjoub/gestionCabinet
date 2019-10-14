package com.spring.pfe.PatientController;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Patient;
import com.spring.pfe.Service.Service;




@Controller
@RequestMapping("/Patient")
public class ProfilePatientController {
	
	PatientDaoImpl daoPatient = new PatientDaoImpl();
	Service service=new Service();

	/*========================================================================================================================*/
   	/*========================================= Afficher profil du patient connecté ================================================*/
   	/*========================================================================================================================*/

	
	@GetMapping("/Profile")
	public String Profile(Model model,HttpSession session) {
		
		// récuperer patient connecté  depuis la session 
		
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");
		
		Patient patientConnecte=daoPatient.getByCin(CinPatientConnecte);
		
		model.addAttribute("patient", patientConnecte);
		
		return "Patient/Profile";
	}
	/*========================================================================================================================*/
   	/*=========================================  Enregistrer(modifier) Profile du patient connecté ================================================*/
   	/*========================================================================================================================*/
 
	@RequestMapping(value = "/EnregistrerProfile", method = RequestMethod.POST)
	
	public String EnregistrerProfile(MultipartFile image,String cin,String login,String password,
		   String nomprenom,String date,String telephone,String adresse,String domicile,String email)
	{
		
		String nomImage;
		
		Patient p=daoPatient.getByCin(cin);
		
		/*si l'image a été modifié*/
		 if (!image.isEmpty())
		 {	
			 nomImage=image.getOriginalFilename();
			 service.CopierImage(image); 
	     } 
		 else {
			 
			 /*si l'image n'a pas été modifié*/
			 nomImage=p.getImage();
		 }

		 p.setLogin(login);
		 p.setNomprenom(nomprenom);
		 p.setPassword(password);
		 p.setImage(nomImage);
		 p.setAdresse(adresse);
		 p.setEmail(email);
		 p.setDomicile(domicile);
		 p.setTelephone(telephone);
		 p.setDate(date);
		
		 daoPatient.Modifier(p);

		 
		 return "redirect:/Patient/";
		 
	}


	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
