package com.spring.pfe.AdminController;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.pfe.Dao.Impl.ForumDaoImpl;
import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Dao.Impl.UserDaoImpl;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;
import com.spring.pfe.Entity.User;
import com.spring.pfe.Service.Service;


@Controller
@RequestMapping("/Admin")
public class GestionPatientsController {
	
	
	/*========================================================================================================================*/
   	/*================== Description : Controller qui gerer la gestion des patients : ajout,modifier,supprimer etc ==========*/
   	/*========================================================================================================================*/

	PatientDaoImpl patientDao=new PatientDaoImpl();
	MedecinDaoImpl medecinDao=new MedecinDaoImpl();
	UserDaoImpl userDao=new UserDaoImpl();
	ForumDaoImpl forumDao=new ForumDaoImpl();
	Service service=new Service();
	
	/*========================================================================================================================*/
   	/*=========================================  listePatient  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/listePatients")
	public String ListePatient(Model model)
	{
		
		model.addAttribute("listePatient", patientDao.getAllPatients());
		model.addAttribute("motcle", "");
		
		return "Admin/ListePatient";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter Patient  ================================================*/
   	/*========================================================================================================================*/

	@RequestMapping(value = "AddPatient", method = RequestMethod.POST)
	public String  AddPatient( Model model,MultipartFile image,String cin,String nomprenom,String login,
			String password,String telephone,String domicile,String email,String adresse,String date,String medecin)
	{
		
		
		User test = userDao.getByCin(cin);
		Patient test2=patientDao.getByLoginPassword(login, password);
		String nomImage=null;
		Patient pat=new Patient();
		/* si cin est unique */
		if(test == null) {
			
			/* si login et password sont   unique */
			
			if(test2==null)
			{
				 if (!image.isEmpty())
				 {	
					 nomImage=image.getOriginalFilename();
					 service.CopierImage(image); 
			     } 
				 
				 Medecin m=new Medecin();
				 m.setCin(medecin);
				 
				 pat=new Patient(cin, nomprenom, login, password, nomImage, adresse, email, telephone, domicile, date, m);
					
				 patientDao.Ajouter(pat);
				
			    return "redirect:/Admin/listePatients";
			}
			else {
				
				 model.addAttribute("listeMedecin", medecinDao.getAllMedecins());
				 model.addAttribute("patient",pat);
				 model.addAttribute("LoginPasswordExiste",true);
				 
				 return "Admin/AddPatient";
			}

		}
		else {
			
			 model.addAttribute("listeMedecin", medecinDao.getAllMedecins());
			 model.addAttribute("patient",pat);
			 model.addAttribute("CinExiste",true);
			 
			 return "Admin/AddPatient";
		}
       
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Forumlaire Ajouter Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/ForumlaireAddPatient")
	public String ForumlaireAddPatient(Model model)
	{

		 String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		 Patient p=new Patient();
		 p.setDate(date);
		 
		 model.addAttribute("patient",p);
		 model.addAttribute("listeMedecin", medecinDao.getAllMedecins());

		 return "Admin/AddPatient";
	}

	
	/*========================================================================================================================*/
   	/*========================================= Supprimer Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeletePatient")
	public String DeletePatient(String cin)
	{

		patientDao.Supprimer(cin);
		forumDao.SupprimerForumByPublisher(cin);
	      
	    return "redirect:/Admin/listePatients";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Modifier Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "ModifierPatient", method = RequestMethod.POST)
	public String ModifierPatient(MultipartFile image,String cin,String nomprenom,String login,
			String password,String telephone,String domicile,String email,String adresse,String date,String medecin)
	
	{
		
		String nomImage=null;
		Patient pat;
		
		 if (!image.isEmpty())
		 {	
			 nomImage=image.getOriginalFilename();
			 service.CopierImage(image); 
	     } 
		 
		 Medecin m=new Medecin();
		 m.setCin(medecin);
		 
		 pat=new Patient(cin, nomprenom, login, password, nomImage, adresse, email, telephone, domicile, date, m);
			
		 patientDao.Modifier(pat);
      
       return "redirect:/Admin/listePatients";
	}

	/*========================================================================================================================*/
   	/*========================================= Afficher Formulaire  Modifier Patient  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/FormulaireModifierPatient")
	public String FormulaireModifierPatient(String cin,Model model)
	{
		 Patient p=patientDao.getByCin(cin);
		 
         model.addAttribute("patient",p );
         model.addAttribute("listeMedecin", new MedecinDaoImpl().getAllMedecins());
         
		 return "Admin/ModifierPatient";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Chercher Patient  ================================================*/
   	/*========================================================================================================================*/

	@RequestMapping(value = "SearchPatient", method = RequestMethod.POST)
	public String SearchPatient(String motCle,Model model)
	{
     
     model.addAttribute("listePatient",patientDao.getAllPatientsByMotCle(motCle));
     model.addAttribute("motcle", motCle);
     
       return "Admin/listePatient";
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
}
