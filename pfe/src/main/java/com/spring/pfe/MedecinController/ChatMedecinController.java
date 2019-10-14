package com.spring.pfe.MedecinController;


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
import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Chat;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;




@Controller
@RequestMapping("/Medecin")
public class ChatMedecinController {
	
	ChatDaoImpl chatdao = new ChatDaoImpl();
	PatientDaoImpl patientDao=new PatientDaoImpl();
	MedecinDaoImpl dao = new MedecinDaoImpl();
 
	/*========================================================================================================================*/
   	/*=========================================  afficher liste des messages    ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/Chat")
	public String ShowListeChat(Model model,HttpSession session) {
		
     
		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
        
		List<Patient> listp=patientDao.getAllPatientsContactMedecin(CinMedecinConnecte);
		model.addAttribute("patients",listp );
		
		
		/* redirection vers page forum*/
		
		return "Medecin/Chat";
	}

	/*========================================================================================================================*/
   	/*=========================================  Affiche Discussion     ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/ShowChatWithPatient")
	public String ShowChat(Model model,String cin,HttpSession session) {
		

		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");

		
		List<Chat> listeschats = chatdao.getAllChatsMedecinWithPatient(CinMedecinConnecte, cin);
		
		 
		model.addAttribute("listeChats", listeschats);
	
		model.addAttribute("cinPatient", cin);
		
		/* redirection vers page messages*/
		
		return "Medecin/ChatDetails";
	}
	/*========================================================================================================================*/
   	/*========================================= SaveMessage   ================================================*/
   	/*========================================================================================================================*/

	
	@RequestMapping(value = "/SaveMessage", method = RequestMethod.POST)
	public String SaveMessage(String message,String cinPatient,HttpSession session) {
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
		Medecin MedecinConnecte=dao.getByCin(CinMedecinConnecte);

		Patient patient=patientDao.getByCin(cinPatient);
		
		message = message.replace("\n", "").replace("\r", "").replace("\t", "");
		
		Chat chat = new Chat();
		chat.setMessage(message);
		chat.setDate(date);
		chat.setEmetteur(MedecinConnecte);
		chat.setRecepteur(patient);
		
		chatdao.Ajouter(chat);	

		/* retourner vers la meme page */
		
		return "redirect:/Medecin/ShowChatWithPatient?cin="+cinPatient;
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Delete message   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteChat")
	public String DeleteChat(Model model,String idChat,String cinPatient) {
		
		UUID id = UUID.fromString(idChat);
		chatdao.Supprimer(id);
		
		/* redirection vers page details forum*/
		
		return "redirect:/Medecin/ShowChatWithPatient?cin="+cinPatient;
	}
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
