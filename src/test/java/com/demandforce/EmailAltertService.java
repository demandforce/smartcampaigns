package com.demandforce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmailAltertService {
	
	public static void main(String arg[]){
		EmailAltertService emailAltertService = new EmailAltertService();
		emailAltertService.generateEmailAlterts();
		System.err.println("done");
	}

	public void generateEmailAlterts(){
		AltertBusiness businesses = getEmailAlterBusinessList();
		emailAltertToBusiness(businesses);
	}


	private void emailAltertToBusiness(AltertBusiness business) {
		
			String fromEmailAddress = business.getBusinessEmailAddress();
			List<String> emailsToSendCampain = business.getEmails();
			Iterator<String> emailsToSendCompainIterator = emailsToSendCampain.iterator();
			while (emailsToSendCompainIterator.hasNext()) {
				String toEmailAddress = (String) emailsToSendCompainIterator.next();
				SendMailTLS.email(fromEmailAddress, toEmailAddress, business.getMessageTitle(), business.getMessageText());
			}
			
	}

	private AltertBusiness getEmailAlterBusinessList() {
		// TODO Auto-generated method stub
		AltertBusiness altertBusiness = new AltertBusiness();
		altertBusiness.setActionTitle("Create a campaign with 20% incentive");
		altertBusiness.setActionUrl("http://demo.demandforced3.com");
		altertBusiness.setBusinessEmailAddress("dfcampain@demandforce.com");
		altertBusiness.setBusinessID("1");
		altertBusiness.setBusinessName("Dentist Campain");
		List<String> emails = new ArrayList<String>();
//		emails.add("jmasilamani@demandforce.com");
//		emails.add("hcheung@demandforce.com");
//		emails.add("wli@demandforce.com");
		emails.add("rzhang@demandforce.com");
		altertBusiness.setEmails(emails);
		altertBusiness.setMessageText("Your appointments schedule is low compared to last year.");
		altertBusiness.setMessageTitle("Your Appointment schedule is low");
		List<String> slowDates = new ArrayList<String>();
		slowDates.add("Jan 3 2013");
		altertBusiness.setSlowDates(slowDates );
		
		
		return altertBusiness;
	}
//	
//	private void emailAltertToBusiness(List<AltertBusiness> businesses) {
//		
//		Iterator<AltertBusiness> iterator = businesses.iterator();
//		while (iterator.hasNext()) {
//			AltertBusiness business = (AltertBusiness) iterator.next();
//			String fromEmailAddress = business.getBusinessEmailAddress();
//			List<String> emailsToSendCampain = business.getEmails();
//			Iterator<String> emailsToSendCompainIterator = emailsToSendCampain.iterator();
//			while (emailsToSendCompainIterator.hasNext()) {
//				String toEmailAddress = (String) emailsToSendCompainIterator.next();
//				SendMailTLS.email(fromEmailAddress, toEmailAddress, business.getMessageTitle(), business.getMessageText());
//			}
//		}
//		
//	}
//
//	private List<AltertBusiness> getEmailAlterBusinessList() {
//		// TODO Auto-generated method stub
//		AltertBusiness altertBusiness = new AltertBusiness();
//		altertBusiness.setActionTitle("Create a campaign with 20% incentive");
//		altertBusiness.setActionUrl("http://demo.demandforced3.com");
//		altertBusiness.setBusinessEmailAddress("dfcampain@demandforce.com");
//		altertBusiness.setBusinessID("1");
//		altertBusiness.setBusinessName("Dentist Campain");
//		List<String> emails = new ArrayList<String>();
////		emails.add("jmasilamani@demandforce.com");
////		emails.add("hcheung@demandforce.com");
////		emails.add("wli@demandforce.com");
//		emails.add("rzhang@demandforce.com");
//		altertBusiness.setEmails(emails);
//		altertBusiness.setMessageText("Your appointments schedule is low compared to last year.");
//		altertBusiness.setMessageTitle("Your Appointment schedule is low");
//		List<String> slowDates = new ArrayList<String>();
//		slowDates.add("Jan 3 2013");
//		altertBusiness.setSlowDates(slowDates );
//		
//		List<AltertBusiness> altertBusinessesList = new ArrayList<AltertBusiness>();
//		
//		altertBusinessesList.add(altertBusiness);
//		return altertBusinessesList;
//	}
}
