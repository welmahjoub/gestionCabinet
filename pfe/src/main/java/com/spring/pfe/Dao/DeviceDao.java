package com.spring.pfe.Dao;

import java.util.List;
import java.util.UUID;

import com.spring.pfe.Entity.Device;

public interface DeviceDao {

	/*========================================================================================================================*/
   	/*=========================================  interface device ================================================*/
   	/*========================================================================================================================*/
	
	public void Ajouter(Device d);
	public void Modifier(Device d);
	public void Supprimer(UUID id);
	public Device getById(UUID id);
	public int getCountDevices();
	
	public List<Device> getAllDevices();
	public List<Device> getAllDevicesByPatient(String cinPatient);
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	
}
