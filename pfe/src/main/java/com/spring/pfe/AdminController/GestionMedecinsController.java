package com.spring.pfe.AdminController;



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
import com.spring.pfe.Entity.User;
import com.spring.pfe.Service.Service;


@Controller
@RequestMapping("/Admin")
public class GestionMedecinsController {
	

	/*========================================================================================================================*/
   	/*================== Description : Controller qui gerer la gestion des medecins : ajout,modifier,supprimer etc ==========*/
   	/*========================================================================================================================*/

	
	MedecinDaoImpl medecinDao=new MedecinDaoImpl();
	PatientDaoImpl patientDao=new PatientDaoImpl();
	UserDaoImpl userDao=new UserDaoImpl();
	ForumDaoImpl forumDao=new ForumDaoImpl();
	Service service=new Service();
	
	/*========================================================================================================================*/
   	/*========================================= Ajouter Medecin  ================================================*/
   	/*========================================================================================================================*/

	@RequestMapping(value = "/AddMedecin", method = RequestMethod.POST)
	public String AddMedecin( Model model,MultipartFile image,String cin,String nomprenom,String login,
			String password,String telephone,String domicile,String email,String adresse,String specialite)
	{
		
		String nomImage=null;
		Medecin m=new Medecin();
		User test = userDao.getByCin(cin);
		
		Medecin test2=medecinDao.getByLoginPassword(login, password);
		
		if(test == null) {
			
			if(test2==null)
			{
				
				if (!image.isEmpty())
				 {	
					 nomImage=image.getOriginalFilename();
					 service.CopierImage(image); 
			     } 
				
				m=new Medecin(cin, nomprenom, login, password, nomImage, adresse, email, telephone, domicile, specialite);
				
				medecinDao.Ajouter(m);
				
				/* redirection vers liste des medecin */
				
			    return "redirect:/Admin/listeMedecins";
			}
			else
			{
				 model.addAttribute("medecin",m);
				 model.addAttribute("LoginPasswordExiste",true);
				 
				 /* retourner vers la page ajouter medecin */
				 
				 return "Admin/AddMedecin";
			}

		}
		else {
			
			 model.addAttribute("medecin",m);
			 model.addAttribute("CinExiste",true);
			 
			 /* retourner vers la page ajouter medecin */
			 
			 return "Admin/AddMedecin";
		}
	}


	/*========================================================================================================================*/
   	/*=========================================  liste Medecins  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/listeMedecins")
	public String listeMedecins(Model model)
	{
		
		model.addAttribute("listeMedecin",medecinDao.getAllMedecins());
		model.addAttribute("motCle", "");
		 
		return "Admin/ListeMedecins";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Forumlaire Medecin  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/ForumlaireAddMedecin")
	public String ForumlaireAddMedecin(Model model)
	{

		model.addAttribute("medecin",new Medecin());
		 
		return "Admin/AddMedecin";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Supprimer Medecin  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteMedecin")
	public String DeleteMedecin(String cin)
	{

		 medecinDao.Supprimer(cin);
		 forumDao.SupprimerForumByPublisher(cin);
		 patientDao.SupprimerByMedecin(cin);
		 
		 return "redirect:/Admin/listeMedecins";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Modifier Medecin  ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "ModifierMedecin", method = RequestMethod.POST)
	public String ModifierMedecin(MultipartFile image,String cin,String nomprenom,String login,
			String password,String telephone,String domicile,String email,String adresse,String specialite)
	
	{
		
		String nomImage="";
		
		if (!image.isEmpty())
		 {	
			 nomImage=image.getOriginalFilename();
			 service.CopierImage(image); 
	     } 
		
		Medecin m;
		m=new Medecin(cin, nomprenom, login, password, nomImage, adresse, email, telephone, domicile, specialite);
		
       medecinDao.Modifier(m);
      
       return "redirect:/Admin/listeMedecins";
	}

	/*========================================================================================================================*/
   	/*========================================= Afficher Formulaire  Modifier Medecin  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/FormulaireModifierMedecin")
	public String FormulaireModifierMedecin(String cin,Model model)
	{
		 Medecin p=medecinDao.getByCin(cin);
		 
         model.addAttribute("medecin",p );
         model.addAttribute("patients",patientDao.getAllPatientsByMedecin(cin) );
        
         
		 return "Admin/ModifierMedecin";
	}
	
	
	/*========================================================================================================================*/
   	/*========================================= Chercher Medecin  ================================================*/
   	/*========================================================================================================================*/

	@RequestMapping(value = "SearchMedecin", method = RequestMethod.POST)
	public String SearchMedecin(String motCle,Model model)
	{
     
     model.addAttribute("listeMedecin",medecinDao.getMedecinsByMotCle(motCle));
     model.addAttribute("motCle", motCle);
     
       return "Admin/ListeMedecins";
	}
	
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
