package com.spring.pfe.PatientController;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.pfe.Dao.Impl.DonneePhysiologDaoImp;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.DonnePhysiolog;
import com.spring.pfe.Service.Service;




@Controller
@RequestMapping("/Patient")
public class EtatPatientController {
	
	PatientDaoImpl daoPatient = new PatientDaoImpl();
	DonneePhysiologDaoImp donneDao=new DonneePhysiologDaoImp();
	Service service=new Service();

	/*========================================================================================================================*/
   	/*========================================= Afficher Etat du patient connecté ================================================*/
   	/*========================================================================================================================*/

	
	@GetMapping("/Etat")
	public String AfficherDonneesPhysiologique(Model model,HttpSession session) {
		
		// récuperer patient connecté  depuis la session 
		
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");

		List<DonnePhysiolog> list=donneDao.getAllDonnePhysiologByPatient(CinPatientConnecte);
		
		model.addAttribute("motcle", "");
		model.addAttribute("ListeStatisque", list);
		
		return "Patient/Etat";
	}

	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

	@GetMapping("/donnePhysiolog")
	@ResponseBody
	public List<DonnePhysiolog> ListeDonnePhysiolog(HttpSession session) {
		
		// récuperer patient connecté  depuis la session 
		
		String CinPatientConnecte =(String) session.getAttribute("CinPatientConnecte");

		List<DonnePhysiolog> list=donneDao.getAllDonnePhysiologByPatient("44444444");

		
		return list;
	}

	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/

}
