package com.spring.pfe.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.web.multipart.MultipartFile;

public class Service {
	
	/*========================================================================================================================*/
   	/*=========================================  stocker l'image choisie dans un dossier   ================================================*/
   	/*========================================================================================================================*/
    
	@SuppressWarnings("resource")
	public void CopierImage(MultipartFile file)
	{
		// récuperer le chemain du project 
		String cheminProject=System.getProperty("user.dir");
		
		// récuperer le chemain du dossier ou on va stocker les images 
	    String cheminCopyImage=cheminProject+"\\src\\main\\resources\\static\\img\\";

        // nom de l'image 
        String Filename=file.getOriginalFilename();
        
	    try {
	            	    
		      byte[] bytes = file.getBytes();
		      
              BufferedOutputStream stream =
                      new BufferedOutputStream(new FileOutputStream(new File(cheminCopyImage+Filename)));
              stream.write(bytes);
   
	      } catch (Exception e) {  }
	            
	   
	}
	
	/*========================================================================================================================*/
   	/*========================================= EnovyerEmaile  ================================================*/
   	/*========================================================================================================================*/

	
	public static void EnovyerEmaile(String email,String msg)
	{

		final String username = "bambeyesoueid@gmail.com";
		final String password = "soueid22152724";
		
		Properties pros = new Properties();

		pros.put("mail.smtp.auth","true");

		pros.put("mail.smtp.starttls.enable","true");

		pros.put("mail.smtp.host","smtp.gmail.com");

		pros.put("mail.smtp.port","587");
		
		Session session = Session.getDefaultInstance(pros,
				new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);

			}});
			try{


			Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(username));
             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			message.setSubject("notification");

			message.setText(msg);

			Transport.send(message);

          System.err.println("email envoyer");

			} catch(Exception e) {e.printStackTrace();}
		
	}
	
	/*========================================================================================================================*/
   	/*=========================================   ================================================*/
   	/*========================================================================================================================*/


}
