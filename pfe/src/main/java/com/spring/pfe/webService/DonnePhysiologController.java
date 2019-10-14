package com.spring.pfe.webService;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.pfe.Dao.Impl.DeviceDaoImp;
import com.spring.pfe.Dao.Impl.DonneePhysiologDaoImp;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.DonnePhysiolog;




@Controller
@RequestMapping("/DonnePhysiolog")
public class DonnePhysiologController {
	
 
	 DonneePhysiologDaoImp daoDonnephy=new DonneePhysiologDaoImp();
	 PatientDaoImpl daoPatient=new PatientDaoImpl();
	 DeviceDaoImp daoDevice=new DeviceDaoImp();
	/*========================================================================================================================*/
   	/*=========================================  add ================================================*/
   	/*========================================================================================================================*/
/*
 * 
 *
 *{
    "rythmesCardiaques": "36",
    "natremie": "012",
    "temperatureCorporelle": "25",
    "tauxProlactine": "12",
    "tensionArterielle": "23",
    "glycemies": "27",
    "cinPatient": "44444444",
    "iddevice": "8f512b10-4a5f-11e8-b566-6b3b535208c0"
}
*/
		
	@RequestMapping(value = "/add/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public String AddDonnePhysiologique(@RequestBody DonnePhysiolog data ) {
				
		
		// convertit date today 
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = formatter.format(date);	
		data.setDate(format);
		
		System.out.println(data);

		daoDonnephy.Ajouter(data);
		
		return "Ajout a été bien effectue !! ";
	}
	
	/*========================================================================================================================*/
   	/*=========================================  get all DonnePhysiolog d'un patient identifié  ================================================*/
   	/*========================================================================================================================*/
	
	@RequestMapping(value = "/get/{cin}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DonnePhysiolog> getDonnePhysiologiqueByPatient(@PathVariable("cin")String cin) {
		
		List<DonnePhysiolog> data=daoDonnephy.getAllDonnePhysiologByPatient(cin);
		
		return data;
	}

	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
