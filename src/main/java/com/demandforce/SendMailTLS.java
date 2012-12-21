package com.demandforce;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
 
	public static void main(String[] args) {
		
		String fromEmailAddress = "dfrecommend@gmail.com";
		String toEmailAddress = "yzhangwei@gmail.com";
		String emailSubject = "More Revenue";
		String emailMessage = "Generate Campian for yoru upcoming slow days";
		SendMailTLS.email(fromEmailAddress, toEmailAddress, emailSubject, emailMessage);
		
	}
	
	public static void email(String fromEmailAddress, String toEmailAddress, String emailSubject, String emailMessage){
		
		
		final String username = "dfrecommend@gmail.com";
		final String password = "ondemand";
 
		fromEmailAddress = username;
		toEmailAddress = "rzhang@demandforce.com";
		
		System.out.println("fromEmailAddress:" + fromEmailAddress);
		System.out.println("toEmailAddress: " + toEmailAddress);
		System.out.println("emailSubject: " + emailSubject);
		System.out.println("emailMessage: " + emailMessage);
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmailAddress));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmailAddress));
			message.setSubject(emailSubject);
			//message.setText(emailMessage);
			message.setContent(emailMessage,"text/html; charset=utf-8");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}