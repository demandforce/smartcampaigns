package com.demandforce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmailAlertService {

	public static void main(String arg[]) {
		EmailAlertService emailAlertService = new EmailAlertService();
		emailAlertService.generateEmailAlterts();
		System.err.println("done");
	}

	public void generateEmailAlterts() {
		AlertBusiness businesses = getEmailAlterForBusiness("");
		emailAlertToBusiness(businesses);
	}

	private void emailAlertToBusiness(AlertBusiness business) {

		String fromEmailAddress = business.getBusinessEmailAddress();
		List<String> emailsToSendCampain = business.getEmails();
		Iterator<String> emailsToSendCompainIterator = emailsToSendCampain
				.iterator();
		while (emailsToSendCompainIterator.hasNext()) {
			String toEmailAddress = (String) emailsToSendCompainIterator.next();
			SendMailTLS.email(fromEmailAddress, toEmailAddress,
					business.getMessageTitle(), business.getMessageText());
		}

	}

	public AlertBusiness getEmailAlterForBusiness(String businessId) {
		AlertBusiness alertBusiness = new AlertBusiness();
		alertBusiness.setActionTitle("Create a campaign with 20% incentive");
		alertBusiness.setActionUrl("http://demo.demandforced3.com");
		alertBusiness.setBusinessEmailAddress("dfcampain@demandforce.com");
		alertBusiness.setBusinessID("1");
		alertBusiness.setBusinessName("Dentist Campain");
		List<String> emails = new ArrayList<String>();
		// emails.add("jmasilamani@demandforce.com");
		// emails.add("hcheung@demandforce.com");
		// emails.add("wli@demandforce.com");
		emails.add("rzhang@demandforce.com");
		alertBusiness.setEmails(emails);
		alertBusiness
				.setMessageText("Your appointments schedule is low compared to last year.");
		alertBusiness.setMessageTitle("Your Appointment schedule is low");
		
		alertBusiness.setSlowMonth("Jan");

		return alertBusiness;
	}
}
