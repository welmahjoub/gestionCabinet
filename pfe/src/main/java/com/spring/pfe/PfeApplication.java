package com.spring.pfe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.pfe.Connection.DataBase;

@SpringBootApplication
public class PfeApplication implements CommandLineRunner{

	/*========================================================================================================================*/
   	/*=========================================  Main    ================================================*/
   	/*========================================================================================================================*/
	
	public static void main(String[] args) {
		
		SpringApplication.run(PfeApplication.class, args);
		
	}

	/*========================================================================================================================*/
   	/*========================================= Initialisation   ================================================*/
   	/*========================================================================================================================*/
	
	
	@Override
	public void run(String... arg0) throws Exception {
		
		DataBase Db=new DataBase();
		
		/* cree la base de donne si n existe pas */
		Db.createKeySpace();
		
		/* cree les tableaux de la base si n existe pas */
		
		Db.CreatetableAdmin();
		Db.createTableMedecin();
		Db.createTablePatient();
		
		Db.createTableForum();
		Db.createTableChat();
		Db.createTableCommenatire();
		
		Db.createTableDevice();
		Db.createTableDonnePhysiologique();
		
		/* remplir les tableaux de la  base de donne si necesssaire pour la test  */
		
		Db.FullTableMedecin();
		Db.FullTablePatient();
		Db.FullTableAdmin();
		
		Db.FullTableDevice();
		Db.FullTableDonnePhysiolog();
		
		
		System.err.println("\n \n Application à demare \n\n");
		
	}
	

	/*========================================================================================================================*/
   	/*=========================================  fonction teste unitaire   ================================================*/
   	/*========================================================================================================================*/
	

	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/
	
}
