package com.spring.pfe.Dao;

import java.util.List;
import java.util.UUID;

import com.spring.pfe.Entity.DonnePhysiolog;

public interface DonneePhysiologDao {
	
	/*========================================================================================================================*/
	/*========================================= Methodes  de l'interface Données physiologiques ================================================*/
	/*========================================================================================================================*/
	
	public void Ajouter(DonnePhysiolog d);
	public int getCountDonnePhysiolog();
	public List<DonnePhysiolog> getAllDonnePhysiolog();
	public DonnePhysiolog getDonnePhysiologById(UUID id);
	public List<DonnePhysiolog> getAllDonnePhysiologByPatient(String cinPatient);
	public void TesterDonneesPhysiologique(DonnePhysiolog d);
	
	/*========================================================================================================================*/
	/*========================================= ================================================*/
	/*========================================================================================================================*/
	

}
