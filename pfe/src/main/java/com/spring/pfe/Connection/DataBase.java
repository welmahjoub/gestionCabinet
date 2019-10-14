package com.spring.pfe.Connection;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.datastax.driver.core.Session;
import com.spring.pfe.Dao.Impl.AdministrateurDaoImpl;
import com.spring.pfe.Dao.Impl.DeviceDaoImp;
import com.spring.pfe.Dao.Impl.DonneePhysiologDaoImp;
import com.spring.pfe.Dao.Impl.MedecinDaoImpl;
import com.spring.pfe.Dao.Impl.PatientDaoImpl;
import com.spring.pfe.Entity.Administrateur;
import com.spring.pfe.Entity.Device;
import com.spring.pfe.Entity.DonnePhysiolog;
import com.spring.pfe.Entity.Medecin;
import com.spring.pfe.Entity.Patient;

public class DataBase {

	/*========================================================================================================================*/
   	/*=============== Description : creation de la base de données et les tableaux  ===============================*/
   	/*========================================================================================================================*/
	
	
	/*========================================================================================================================*/
   	/*========================================= Attributs  ================================================*/
   	/*========================================================================================================================*/
	
	
	private MedecinDaoImpl medecinDao;
	private PatientDaoImpl patientDao;
	private AdministrateurDaoImpl adminDao;
	private DeviceDaoImp deviceDao;
	private DonneePhysiologDaoImp donneDao;
	private Session session;

	
	/*========================================================================================================================*/
   	/*========================================= Constructeur sans paramètres  ================================================*/
   	/*========================================================================================================================*/
	
	
	public DataBase()
	{
		session = CassandraConnection.getSession();
		medecinDao=new MedecinDaoImpl();
		patientDao=new PatientDaoImpl();
		adminDao=new AdministrateurDaoImpl();
		deviceDao=new DeviceDaoImp();
		donneDao=new DonneePhysiologDaoImp();
	}
	
	/*========================================================================================================================*/
  	/*=========================================  creation de la base de données dans Cassandra   ==============================*/
  	/*========================================================================================================================*/
	
	public   void createKeySpace()
	{
		 session.execute("CREATE KEYSPACE IF NOT EXISTS pfe WITH replication "
				+ "= {'class': 'SimpleStrategy', 'replication_factor' : 1}");
		 
	}
	

	/*========================================================================================================================*/
   	/*=========================================  Création table medecin base de données  ====================================*/
   	/*========================================================================================================================*/

	public void createTableMedecin()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.medecin"
				+ " (cin text, nomprenom text, login text, password text,"  
				+  "adresse text,telephone text,domicile text,email text,specialite text,image text,"
				+ " PRIMARY KEY (cin));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.medecin(nomprenom);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.medecin(specialite);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.medecin(login);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.medecin(password);");

	}
	
	/*========================================================================================================================*/
   	/*=========================================  Création table patient base de données  ==============================*/
   	/*========================================================================================================================*/

	public void createTablePatient()
	{

		 session.execute("CREATE TABLE IF NOT EXISTS pfe.patient"
				+ " (cin text, nomprenom text, login text, password text,"  
				+  "adresse text,telephone text,domicile text,email text,date text,medecin text,image text,"
				+ " PRIMARY KEY (cin));");
		 
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.patient(medecin);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.patient(nomprenom);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.patient(login);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.patient(password);");
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================  Création table Admin base de données  ===========================*/
   	/*========================================================================================================================*/

	public void CreatetableAdmin()
	{
		 session.execute("CREATE TABLE IF NOT EXISTS pfe.admin"
					+ " (cin text, nomprenom text, login text, password text,"  
					+  "image text, PRIMARY KEY (cin));");
			 
			 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.admin(nomprenom);");
			 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.admin(login);");
			 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.admin(password);");
	}
	
	
	/*========================================================================================================================*/
   	/*=========================================  Création table forum base de données  ====================================*/
   	/*========================================================================================================================*/

	public void createTableForum()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.forum"
				+ " (id UUID , titre text, contenue text, cin_publisher text,date text,"  
				+ " PRIMARY KEY (id));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.forum(titre);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.forum(cin_publisher);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.forum(date);");

	}
	
	/*========================================================================================================================*/
   	/*=========================================  Création table Commenatire base de données  ====================================*/
   	/*========================================================================================================================*/

	public void createTableCommenatire()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.commentaire"
				+ " (id UUID ,contenue text, date text,cin_publisher text, id_forum UUID,"  
				+ " PRIMARY KEY (id));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.commentaire(contenue);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.commentaire(cin_publisher);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.commentaire(id_forum);");

	}
	
	/*========================================================================================================================*/
   	/*=========================================  Création table chat base de données  ====================================*/
   	/*========================================================================================================================*/

	public void createTableChat()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.chat"
				+ " (id UUID, cin_emeteur text, cin_recepteur text,message  text,date text,"  
				+ " PRIMARY KEY (id));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.chat(cin_emeteur);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.chat(cin_recepteur);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.chat(date);");

	}
	/*========================================================================================================================*/
   	/*=================================== Création table DonnePhysiologique base de données  ===========================*/
   	/*========================================================================================================================*/

	public void createTableDonnePhysiologique()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.donnephysiolog"
				+ " (id UUID ,rythmescardiaques text, natremie text, tauxprolactine text,tensionarterielle text,"  
				+ " temperatureCorporelle text,glycemies text,date text ,cinpatient text,iddevice text,"
				+ " PRIMARY KEY (id));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.donnephysiolog(cinpatient);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.donnephysiolog(iddevice);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.donnephysiolog(date);");

	}
	/*========================================================================================================================*/
   	/*=========================================  Création table device base de données  ====================================*/
   	/*========================================================================================================================*/

	public void createTableDevice()
	{

		session.execute("CREATE TABLE IF NOT EXISTS pfe.device"
				+ " (id UUID , nom text, description text, cinpatient text,"  
				+ " PRIMARY KEY (id));");
		
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.device(nom);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.device(description);");
		 session.execute("CREATE INDEX IF NOT EXISTS ON pfe.device(cinpatient);");

	}
	/*========================================================================================================================*/
  	/*========================================= remlpir Table Medecin pour tester   =================================*/
  	/*========================================================================================================================*/

	public void FullTableMedecin()
	{
		
		int nb=medecinDao.getCountMedecins();
		
		if(nb==0)
		{
			
			Medecin m1=new Medecin("55555555", "medecin1", "medecin1", "medecin1" ,"doctor2.jpg",
					"Bizerte","medecin@gmail.com","12345698","25874136","specialite1");
			
			Medecin m2=new Medecin("66666666", "medecin2" ,"medecin2", "medecin2","doctor2.jpg"
					, "ariana","medecin2@gmail.com", "12345698","25874136" ,"specialite2");
			
			Medecin m3=new Medecin("77777777", "medecin3", "medecin3", "medecin3","doctor2.jpg",
					"tunis","medecin3@gmail.com" ,"12345698","25874136", "specialite3");
			
			Medecin m4=new Medecin("88888888", "medecin4","medecin4", "medecin4","doctor2.jpg"
					,"hamamat","medecin4@gmail.com", "12345698","25874136","specialite4");
			
			
			medecinDao.Ajouter(m1);
			medecinDao.Ajouter(m2);
			medecinDao.Ajouter(m4);
			medecinDao.Ajouter(m3);
		
		}
		
		
	}
	
	/*========================================================================================================================*/
  	/*========================================= remlpir Table Patient pour tester  ============================*/
  	/*========================================================================================================================*/

	public void FullTablePatient()
	{
		int nb=patientDao.getCountPatient();
		
		if(nb==0)
		{
			
			String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
			Patient p1=new Patient("11111111", "dhia Sediri","patient1", "patient1" ,"thumb.png",
					"souse", "mahjoubwel@gmail.com", "22330545","22330545", date , new Medecin("55555555"));
			
			
			Patient p2=new Patient("22222222", "lafdal Mohamed ","patient2", "patient2","thumb.png",
					"tunis", "mahjoubwel@gmail.com", "33660202","33660202", date , new Medecin("55555555"));

			Patient p3=new Patient("33333333", "mahjoub Mohamed " , "patient3", "patient3" ,"thumb.png"
					,  "gabes", "mahjoubwel@gmail.com", "36001630","36001630",date , new Medecin("66666666"));
			
			Patient p4=new Patient("44444444", "abderhaman " , "patient4", "patient4","thumb.png"
					, "tunis", "mahjoubwel@gmail.com", "20859634","20859634",date , new Medecin("66666666"));
			
			patientDao.Ajouter(p1);
			patientDao.Ajouter(p2);
			patientDao.Ajouter(p3);
			patientDao.Ajouter(p4);
			
		}
	}
	/*========================================================================================================================*/
   	/*========================================= remplir table admin pour tester  ================================================*/
   	/*========================================================================================================================*/

	public void FullTableAdmin()
	{
		int nb=adminDao.getCountAdmin();
		
		if(nb==0) 
		{
			
		 Administrateur admin1 = new Administrateur("99999999", "dhiasediri", "admin1", "admin1", "doctor4.jpg");
		 Administrateur admin2 = new Administrateur("10101010", "mahjoub", "admin2", "admin2", "doctor4.jpg");
		 
		 adminDao.Ajouter(admin1);
		 adminDao.Ajouter(admin2);
		 
		}
	}
	
	/*========================================================================================================================*/
  	/*========================================= remlpir Table device s'il est vide pour tester   =================================*/
  	/*========================================================================================================================*/

	public void FullTableDevice()
	{
		
		int nb=deviceDao.getCountDevices();
		
		if(nb==0)
		{
			
			Device d1=new Device();
			d1.setDescription("gcc");
			d1.setNom("gcc");
			Patient patient= new Patient();
			patient.setCin("44444444");
			d1.setPatient(patient);
			
			
			Device d2=new Device();
			d2.setDescription("gcc");
			d2.setNom("gcc");
			d2.setPatient(patient);
			
			deviceDao.Ajouter(d1);
			deviceDao.Ajouter(d2);
			
		}
		
		
	}
	
	/*========================================================================================================================*/
  	/*========================================= remlpir Table donne physiolog s'il est vide pour tester   =================================*/
  	/*========================================================================================================================*/

	public void FullTableDonnePhysiolog()
	{
		
		int nb=donneDao.getCountDonnePhysiolog();
		
		if(nb==0)
		{
			Date date=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = formatter.format(date);	
			
			
			DonnePhysiolog d1=new DonnePhysiolog();
			d1.setCinPatient("44444444");
			d1.setDate(format);
			d1.setGlycemies("12");
			d1.setNatremie("14");
			d1.setTemperatureCorporelle("36");
			d1.setTensionArterielle("37");
			d1.setRythmesCardiaques("2563");
			d1.setTauxProlactine("456");
			d1.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d2=new DonnePhysiolog();
			d2.setCinPatient("11111111");
			d2.setDate(format);
			d2.setGlycemies("12");
			d2.setNatremie("14");
			d2.setTemperatureCorporelle("36");
			d2.setTensionArterielle("37");
			d2.setRythmesCardiaques("2563");
			d2.setTauxProlactine("456");
			d2.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			DonnePhysiolog d3=new DonnePhysiolog();
			d3.setCinPatient("22222222");
			d3.setDate(format);
			d3.setGlycemies("12");
			d3.setNatremie("14");
			d3.setTemperatureCorporelle("36");
			d3.setTensionArterielle("37");
			d3.setRythmesCardiaques("2563");
			d3.setTauxProlactine("456");
			d3.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d4=new DonnePhysiolog();
			d4.setCinPatient("33333333");
			d4.setDate(format);
			d4.setGlycemies("12");
			d4.setNatremie("14");
			d4.setTemperatureCorporelle("36");
			d4.setTensionArterielle("37");
			d4.setRythmesCardiaques("2563");
			d4.setTauxProlactine("456");
			d4.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d5=new DonnePhysiolog();
			d5.setCinPatient("44444444");
			d5.setDate(format);
			d5.setGlycemies("12");
			d5.setNatremie("14");
			d5.setTemperatureCorporelle("36");
			d5.setTensionArterielle("37");
			d5.setRythmesCardiaques("2563");
			d5.setTauxProlactine("456");
			d5.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d6=new DonnePhysiolog();
			d6.setCinPatient("11111111");
			d6.setDate(format);
			d6.setGlycemies("12");
			d6.setNatremie("14");
			d6.setTemperatureCorporelle("36");
			d6.setTensionArterielle("37");
			d6.setRythmesCardiaques("2563");
			d6.setTauxProlactine("456");
			d6.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d7=new DonnePhysiolog();
			d7.setCinPatient("22222222");
			d7.setDate(format);
			d7.setGlycemies("12");
			d7.setNatremie("14");
			d7.setTemperatureCorporelle("36");
			d7.setTensionArterielle("37");
			d7.setRythmesCardiaques("2563");
			d7.setTauxProlactine("456");
			d7.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
			DonnePhysiolog d8=new DonnePhysiolog();
			d8.setCinPatient("33333333");
			d8.setDate(format);
			d8.setGlycemies("12");
			d8.setNatremie("14");
			d8.setTemperatureCorporelle("36");
			d8.setTensionArterielle("37");
			d8.setRythmesCardiaques("2563");
			d8.setTauxProlactine("456");
			d8.setIddevice("8f512b10-4a5f-11e8-b566-6b3b535208c0");
			
			
		  donneDao.Ajouter(d1);
		  donneDao.Ajouter(d2);
		  donneDao.Ajouter(d3);
		  donneDao.Ajouter(d4);
		  donneDao.Ajouter(d5);
		  donneDao.Ajouter(d6);
		  donneDao.Ajouter(d7);
		  donneDao.Ajouter(d8);
		  
		}
		
	}
	/*========================================================================================================================*/
  	/*=========================================   =================================*/
  	/*========================================================================================================================*/

}
