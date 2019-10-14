package com.spring.pfe.MedecinController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.pfe.Dao.Impl.ChatDaoImpl;
import com.spring.pfe.Dao.Impl.DonneePhysiologDaoImp;
import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Chat;
import com.spring.pfe.Entity.DonnePhysiolog;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;




@Controller
@RequestMapping("/Medecin")
public class ListePatientsController {
	
	PatientDaoImpl patientdao = new PatientDaoImpl();
	MedecinDaoImpl medecindao = new MedecinDaoImpl();
	ChatDaoImpl chatdao = new ChatDaoImpl();
	DonneePhysiologDaoImp donneDao=new DonneePhysiologDaoImp();
	
	/*========================================================================================================================*/
   	/*=========================================  AfficherListePatients pour medecin    ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/ListePatients")
	public String AfficherListePatients(Model model, HttpSession session) {
		
		String cin = (String) session.getAttribute("CinMedecinConnecte");
		
		List<Patient> listePatients = patientdao.getAllPatientsByMedecin(cin);
		
		model.addAttribute("listePatient", listePatients);
		 model.addAttribute("motcle", "");

		return "Medecin/ListePatient";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Chercher Patient  ================================================*/
   	/*========================================================================================================================*/

	@RequestMapping(value = "SearchPatient", method = RequestMethod.POST)
	public String SearchPatient(String motCle,Model model)
	{
     
     model.addAttribute("listePatient",patientdao.getAllPatientsByMotCle(motCle));
     model.addAttribute("motcle", motCle);
     
       return "Admin/listePatient";
	}
	
	/*========================================================================================================================*/
   	/*========================================= Afficher patient  ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/AfficherProfilePatient")
	public String AfficherProfilePatient(String cin,Model model)
	{
		 Patient p=patientdao.getByCin(cin);
		 
         model.addAttribute("patient",p );
         
 		List<DonnePhysiolog> list=donneDao.getAllDonnePhysiologByPatient(cin);
		

 		model.addAttribute("ListeStatisque", list);
         
		 return "Medecin/ProfilePatient";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  SendMessageToPatient ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/SendMessageToPatient", method = RequestMethod.POST)
	public String SendMessageToPatient(String message,String cinPatient,HttpSession session) {
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
		Medecin MedecinConnecte=medecindao.getByCin(CinMedecinConnecte);

		Patient patient=patientdao.getByCin(cinPatient);
		
		message = message.replace("\n", "").replace("\r", "").replace("\t", "");
		
		Chat chat = new Chat();
		chat.setMessage(message);
		chat.setDate(date);
		chat.setEmetteur(MedecinConnecte);
		chat.setRecepteur(patient);
		
		chatdao.Ajouter(chat);	

		/* retourner vers la mem page  */
		return "redirect:/Medecin/AfficherProfilePatient?cin="+cinPatient;
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
