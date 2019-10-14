package com.spring.pfe.PatientController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.pfe.Dao.Impl.ChatDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Chat;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;




@Controller
@RequestMapping("/Patient")
public class ChatPatientController {
	
	ChatDaoImpl chatdao = new ChatDaoImpl();
	PatientDaoImpl daoPatient = new PatientDaoImpl();
 
	/*========================================================================================================================*/
   	/*============================  afficher discussion entre patient et son medecin     ===============================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/Chat")
	public String ShowChat(Model model,HttpSession session) {
		
		
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");
		
		Patient patient=daoPatient.getByCin(CinPatientConnecte);
		
		
		Medecin medecin=patient.getMedecin();
		
		List<Chat> listeschats = chatdao.getAllChatsMedecinWithPatient(medecin.getCin(), patient.getCin());
		 
		model.addAttribute("listeChats", listeschats);
		model.addAttribute("patient", patient);
		
		/* redirection vers page chat*/
		return "Patient/Chat";
	}

	/*========================================================================================================================*/
   	/*========================================= Envoyer Message   ================================================*/
   	/*========================================================================================================================*/

	
	@RequestMapping(value = "/SaveMessage", method = RequestMethod.POST)
	public String SaveMessage(String message,HttpSession session) {
		
		Chat chat = new Chat();
		
	
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");
		
		Patient patient=daoPatient.getByCin(CinPatientConnecte);
		
		Medecin medecin = patient.getMedecin();
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		message = message.replace("\n", "").replace("\r", "").replace("\t", "");
		
		chat.setMessage(message);
		chat.setDate(date);
		chat.setEmetteur(patient);
		chat.setRecepteur(medecin);
		
		chatdao.Ajouter(chat);	

		/* redirection vers page chat*/
		return "redirect:/Patient/Chat";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer message   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteChat")
	public String DeleteCommenatire(Model model,String idChat) {
		
		UUID id = UUID.fromString(idChat);
		chatdao.Supprimer(id);
		
		/* redirection vers page details forum*/	
		return "redirect:/Patient/Chat";
	}

	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
