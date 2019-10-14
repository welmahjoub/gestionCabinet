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

import com.spring.pfe.Dao.Impl.CommentaireDaoImpl;
import com.spring.pfe.Dao.Impl.ForumDaoImpl;
import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Entity.Commentaire;
import com.spring.pfe.Entity.Forum;
import com.spring.pfe.Entity.Medecin;




@Controller
@RequestMapping("/Medecin")
public class ForumMedecinController {
	
	ForumDaoImpl daoforum = new ForumDaoImpl();
	CommentaireDaoImpl comentdao=new CommentaireDaoImpl();
	MedecinDaoImpl dao = new MedecinDaoImpl();
	
	/*========================================================================================================================*/
   	/*=========================================  Controller Forum   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/Forum")
	public String ShowForum(Model model) {
		
		List<Forum> liste = daoforum.getAllForums();
		model.addAttribute("listeForums", liste);

		/* redirection vers page forum*/
		return "medecin/Forum";
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
		return "Medecin/Forum";
		
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
		return "Medecin/ForumDetails";
	}
	/*========================================================================================================================*/
   	/*=========================================  Ajouter une publication  ================================================*/
   	/*========================================================================================================================*/
	
	
	@RequestMapping(value = "/SaveForum", method = RequestMethod.POST)
	public String SaveForum(String titre,String contenu,HttpSession session) {
		
		Forum forum = new Forum();
		
		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
		Medecin publisher=dao.getByCin(CinMedecinConnecte);
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		forum.setDate(date);
		forum.setTitre(titre);
		forum.setContenu(contenu);
		forum.setPublisher(publisher);
		
		daoforum.Ajouter(forum);

		/* redirection vers page forum aprés l'ajout */
		return "redirect:/Medecin/Forum";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Ajouter une Commentaire   ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/SaveCommentaire", method = RequestMethod.POST)
	public String SaveCommentaire(String commentaire,String idForum,HttpSession session) {
		

		String CinMedecinConnecte =(String) session.getAttribute("CinMedecinConnecte");
		
		Medecin publisher=dao.getByCin(CinMedecinConnecte);
		
		UUID IdForum = UUID.fromString(idForum);
		Forum forum=daoforum.getById(IdForum);
		
		String date=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());
		
		Commentaire comment=new Commentaire();
		
		comment.setContenu(commentaire);
		comment.setDate(date);
		comment.setForum(forum);
		comment.setPublisher(publisher);
		
		comentdao.Ajouter(comment);

		/* redirection vers page detils commentaire */
		return "redirect:/Medecin/ShowDetailForum?idForum="+idForum;
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Supprimer publication   ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteForum")
	public String DeleteForum(Model model,String idForum) {
		
		UUID IdForum = UUID.fromString(idForum);
		daoforum.Supprimer(IdForum);
		comentdao.SupprimerCommentsByForum(IdForum);
		
		/* redirection vers page details forum*/
		
		return "redirect:/Medecin/Forum";
	}
	
	/*========================================================================================================================*/
   	/*========================================= DeleteCommenatire ================================================*/
   	/*========================================================================================================================*/
	
	@GetMapping("/DeleteCommenatire")
	public String DeleteCommenatire(Model model,String idCommenatire,String idForum) {
		
		UUID id = UUID.fromString(idCommenatire);
		comentdao.Supprimer(id);
		
		/* redirection vers page details forum*/
		
		return "redirect:/Medecin/ShowDetailForum?idForum="+idForum;
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
