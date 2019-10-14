package com.spring.pfe.Dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.spring.pfe.Connection.CassandraConnection;
import com.spring.pfe.Dao.DonneePhysiologDao;
import com.spring.pfe.Entity.DonnePhysiolog;
import com.spring.pfe.Entity.Patient;
import com.spring.pfe.Service.Service;

public class DonneePhysiologDaoImp implements DonneePhysiologDao {

	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
	/*========================================= stocker les données physiologiques d'un patient  ==============================*/
	/*========================================================================================================================*/
	
	@Override
	public void Ajouter(DonnePhysiolog d) {
		
		prepared = null;
		prepared = session.prepare("insert into pfe.donnephysiolog (id,rythmescardiaques"
				+ ",natremie,tauxprolactine,tensionarterielle,temperatureCorporelle,glycemies,"
				+ "date,cinpatient,iddevice) "
				+ "values (?,?,?,?,?,?,?,?,?,?) ");

		

		session.execute(prepared.bind(UUIDs.timeBased(),d.getRythmesCardiaques(),
				d.getNatremie(),d.getTauxProlactine(),d.getTensionArterielle(),d.getTemperatureCorporelle(),
				d.getGlycemies(),d.getDate(),d.getCinPatient(),d.getIddevice()));
		
		TesterDonneesPhysiologique(d);
		
	}
	
	/*========================================================================================================================*/
	/*========================================= getAllDonnePhysiolog ================================================*/
	/*========================================================================================================================*/
	

	@Override
	public List<DonnePhysiolog> getAllDonnePhysiolog() {
		
		
		ArrayList<DonnePhysiolog> liste =new ArrayList<>();

		rs = session.execute("select * from pfe.donnephysiolog");

		for (Row ligne : rs) {

			DonnePhysiolog d=new DonnePhysiolog();
			d.setId(ligne.getUUID("id"));
			d.setRythmesCardiaques(ligne.getString("rythmescardiaques"));
			d.setNatremie(ligne.getString("natremie"));
			d.setTauxProlactine(ligne.getString("tauxprolactine"));
			d.setTensionArterielle(ligne.getString("tensionarterielle"));
			d.setTemperatureCorporelle(ligne.getString("temperatureCorporelle"));
			d.setGlycemies(ligne.getString("glycemies"));
			d.setDate(ligne.getString("date"));
			d.setIddevice(ligne.getString("iddevice"));
			d.setCinPatient(ligne.getString("cinpatient"));

			liste.add(d);

		}
		
		return liste;
	}

	/*========================================================================================================================*/
	/*================================= getAllDonnePhysiologByPatient ====================================*/
	/*========================================================================================================================*/
	
	@Override
	public List<DonnePhysiolog> getAllDonnePhysiologByPatient(String cinPatient) {
		
		
		List<DonnePhysiolog> liste=getAllDonnePhysiolog();
		List<DonnePhysiolog> listFinal=new ArrayList<>();
		
		for (DonnePhysiolog d : liste) {
			
			if(d.getCinPatient().equals(cinPatient)  )
			{
				listFinal.add(d);
			}
		}
		
		return listFinal;
	}
	
	/*========================================================================================================================*/
	/*========================================= getCountDonnePhysiolog  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public int getCountDonnePhysiolog() {
	
		return getAllDonnePhysiolog().size();
	}

	/*========================================================================================================================*/
	/*========================================= getDonnePhysiologById  ================================================*/
	/*========================================================================================================================*/
	
	@Override
	public DonnePhysiolog getDonnePhysiologById(UUID id) {
		
		return null;
	}

	/*========================================================================================================================*/
	/*============================== TesterLesDonneesPhysiologique ===========================================*/
	/*========================================================================================================================*/
	
	@Override
	public void TesterDonneesPhysiologique(DonnePhysiolog d){
		
		PatientDaoImpl daopatient=new PatientDaoImpl();
		Patient patient=daopatient.getByCin(d.getCinPatient());
		
		float temp=Float.parseFloat(d.getTemperatureCorporelle());
		
		if(temp>39)
		{
			Service.EnovyerEmaile(patient.getEmail(), "temperature corporelle est élevée ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "temperature corporelle est élevée ");
		}
		
		if(temp<36)
		{
			Service.EnovyerEmaile(patient.getEmail(), "Temperature corporelle est faible ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Temperature corporelle est faible ");
		}
		
		float glycemie=Float.parseFloat(d.getGlycemies());
		
		if(glycemie>126)
		{
			Service.EnovyerEmaile(patient.getEmail(), "Glycemie est élevée ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Glycemie est élevée ");
		}
		
		if(glycemie<126)
		{
            Service.EnovyerEmaile(patient.getEmail(), "Glycemie est élevée ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Glycemie est élevée ");	
		}
		
        float rythme=Float.parseFloat(d.getRythmesCardiaques());
		
		if(rythme>100)
		{
			Service.EnovyerEmaile(patient.getEmail(), "Rythmes Cardiaques est élevé ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Rythmes Cardiaques est élevée ");
		}
		
		if(rythme<60)
		{
            Service.EnovyerEmaile(patient.getEmail(), "Rythmes Cardiaques est faible ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Rythmes Cardiaques est faible ");	
		}
		
        float natremie=Float.parseFloat(d.getNatremie());
		
		if(natremie>142)
		{
			Service.EnovyerEmaile(patient.getEmail(), "Natremie est élevé ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Natremie est élevée ");
		}
		
		if(natremie<138)
		{
            Service.EnovyerEmaile(patient.getEmail(), "Natremie est faible ");
			
			Service.EnovyerEmaile(patient.getMedecin().getEmail(), "Natremie est faible ");	
		}
		
	}

	/*========================================================================================================================*/
	/*=========================================  ================================================*/
	/*========================================================================================================================*/
	
}
