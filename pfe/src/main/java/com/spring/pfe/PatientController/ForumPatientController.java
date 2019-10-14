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

import com.spring.pfe.Dao.Impl.CommentaireDaoImpl;
import com.spring.pfe.Dao.Impl.ForumDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Commentaire;
import com.spring.pfe.Entity.Forum;
import com.spring.pfe.Entity.Patient;




@Controller
@RequestMapping("/Patient")
public class ForumPatientController {
	
	ForumDaoImpl daoforum = new ForumDaoImpl();
	CommentaireDaoImpl comentdao=new CommentaireDaoImpl();
	PatientDaoImpl daoPatient = new PatientDaoImpl();
	
	/*========================================================================================================================*/
   	/*=========================================  Afficher liste des Forums   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/Forum")
	public String ShowForum(Model model) {
		
		List<Forum> liste = daoforum.getAllForums();
		model.addAttribute("listeForums", liste);

		/* redirection vers page forum*/
		return "Patient/Forum";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Afficher liste des Forums by mot cle   ================================================*/
   	/*========================================================================================================================*/

	
	@RequestMapping(value = "/ForumByMotCle", method = RequestMethod.POST)
	public String ShowForumByMotCle(Model model,String MotCle) {
		
		List<Forum> liste = daoforum.getAllForumsByTitre(MotCle);
		model.addAttribute("listeForums", liste);
		model.addAttribute("MotCle", MotCle);
		
		/* redirection vers page forum*/
		return "Patient/Forum";
		
	}
	/*========================================================================================================================*/
   	/*=========================================  Afficher une publication   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/ShowDetailForum")
	public String ShowDetailForum(Model model,String idForum) {
		
		UUID IdForum = UUID.fromString(idForum);
		model.addAttribute("forum", daoforum.getById(IdForum));
		model.addAttribute("comments", comentdao.getAllCommentsByForum(IdForum));
		
		/* redirection vers page details forum*/
		
		return "patient/ForumDetails";
	}
	/*========================================================================================================================*/
   	/*=========================================  Ajouter une publication(forum)  ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/SaveForum", method = RequestMethod.POST)
	public String SaveForum(String titre,String contenu,HttpSession session) {
		
		Forum forum = new Forum();
		
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");
		
		Patient publisher=daoPatient.getByCin(CinPatientConnecte);

		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		forum.setDate(date);
		forum.setTitre(titre);
		forum.setContenu(contenu);
		forum.setPublisher(publisher);
		
		daoforum.Ajouter(forum);

		/* redirection vers page forum aprés l'ajout */
		return "redirect:/Patient/Forum";
	}
	/*========================================================================================================================*/
   	/*=========================================  Ajouter une Commentaire   ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/SaveCommentaire", method = RequestMethod.POST)
	public String SaveCommentaire(String commentaire,String idForum,HttpSession session) {
		


		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");
		
		Patient publisher=daoPatient.getByCin(CinPatientConnecte);
		
		Commentaire comment=new Commentaire();

		UUID IdForum = UUID.fromString(idForum);
		Forum forum=daoforum.getById(IdForum);
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		comment.setContenu(commentaire);
		comment.setDate(date);
		comment.setForum(forum);
		comment.setPublisher(publisher);
		
		comentdao.Ajouter(comment);

		/* redirection vers page detils commentaire */
		return "redirect:/Patient/ShowDetailForum?idForum="+idForum;
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Delete publication   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteForum")
	public String DeleteForum(Model model,String idForum) {
		
		UUID IdForum = UUID.fromString(idForum);
		daoforum.Supprimer(IdForum);
		comentdao.SupprimerCommentsByForum(IdForum);
		
		/* redirection vers page details forum*/
		
		return "redirect:/Patient/Forum";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Delete publication   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteCommenatire")
	public String DeleteCommenatire(Model model,String idCommenatire,String idForum) {
		
		UUID id = UUID.fromString(idCommenatire);
		comentdao.Supprimer(id);
		
		/* redirection vers page details forum*/
		
		return "redirect:/Patient/ShowDetailForum?idForum="+idForum;
	}
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
