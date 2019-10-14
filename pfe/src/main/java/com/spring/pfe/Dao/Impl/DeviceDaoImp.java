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
import com.spring.pfe.Dao.DeviceDao;
import com.spring.pfe.Entity.Device;
import com.spring.pfe.Entity.Patient;

public class DeviceDaoImp implements DeviceDao{

	private Session session=CassandraConnection.getSession();
	private ResultSet rs ;
	private PreparedStatement prepared;
	
	/*========================================================================================================================*/
   	/*========================================= Ajouter device  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public void Ajouter(Device d) {
		
		prepared = null;
		prepared = session.prepare("insert into pfe.device (id,nom,description,cinpatient) "
				+ "values (?,?,?,?) ");

		

		session.execute(prepared.bind(UUIDs.timeBased(),d.getNom(),d.getDescription()
				,d.getPatient().getCin()));
	}

	/*========================================================================================================================*/
   	/*========================================= Modifier device ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public void Modifier(Device d) {
		
		prepared = null;
		prepared = session.prepare("UPDATE  pfe.device SET nom=?,description=?,cinpatient=? WHERE id=?");

		

		session.execute(prepared.bind(d.getNom(),d.getDescription()
				,d.getPatient().getCin(),UUIDs.timeBased()));
		
	}

	/*========================================================================================================================*/
   	/*========================================= Supprimer device ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public void Supprimer(UUID id) {
		
		prepared = null;
		prepared = session.prepare("DELETE FROM  pfe.device WHERE  id=?");
		
		session.execute(prepared.bind(id));
		
	}

	/*========================================================================================================================*/
   	/*========================================= get device By Id  ================================================*/
   	/*========================================================================================================================*/
	
	@Override
	public Device getById(UUID id) {
	
		PatientDaoImpl daopatient=new PatientDaoImpl();
		
		prepared = null;
		prepared = session.prepare("select * from pfe.device WHERE id=?");

		rs=session.execute(prepared.bind(id));
		Row ligne=rs.one();

		if(ligne!=null)
		{
			Device d=new Device();
			d.setNom(ligne.getString("nom"));
			d.setDescription(ligne.getString("description"));
			
			Patient p=daopatient.getByCin(ligne.getString("cinpatient"));
			d.setPatient(p);
		
			return d;
		}
			return null;
	}

	/*========================================================================================================================*/
   	/*=========================================  getAllDevices ===================================*/
   	/*========================================================================================================================*/
	
	@Override
	public List<Device> getAllDevices() {
		
		PatientDaoImpl daopatient=new PatientDaoImpl();
		
		ArrayList<Device> liste =new ArrayList<>();
	
		rs = session.execute("select * from pfe.device");

		for (Row ligne : rs) {

			Device d=new Device();
			d.setNom(ligne.getString("nom"));
			d.setDescription(ligne.getString("description"));
			
			Patient p=daopatient.getByCin(ligne.getString("cinpatient"));
			d.setPatient(p);

			liste.add(d);

		}
		
		return liste;
	}

	/*========================================================================================================================*/
   	/*========================================= getAllDevicesByPatient =========================================*/
   	/*========================================================================================================================*/
	
	@Override
	public List<Device> getAllDevicesByPatient(String cinPatient) {
		
		List<Device> liste=getAllDevices();
		List<Device> listFinal=new ArrayList<>();
		
		for (Device d : liste) {
			
			if(d.getPatient().getCin().equals(cinPatient)  )
			{
				listFinal.add(d);
			}
		}
		return listFinal;
	}

	/*========================================================================================================================*/
   	/*======================================= retourner le nombre de devices dans la base de données ==========================================*/
   	/*========================================================================================================================*/
	
	@Override
	public int getCountDevices() {
	
		return getAllDevices().size();
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	

}
