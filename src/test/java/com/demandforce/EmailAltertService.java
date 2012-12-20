package com.demandforce;

import java.util.Iterator;
import java.util.List;

public class EmailAltertService {

	public void generateEmailAlterts(){
		List<AltertBusiness> businesses = getEmailAlterBusinessList();
		emailAltertToBusiness(businesses);
	}

	private void emailAltertToBusiness(List<AltertBusiness> businesses) {
		
		Iterator iterator = businesses.iterator();
		while (iterator.hasNext()) {
			AltertBusiness business = (AltertBusiness) iterator.next();
			String fromEmailAddress = business.getBusinessEmailAddress();
			List<String> emailsToSendCampain = business.getEmails();
			Iterator<String> emailsToSendCompainIterator = emailsToSendCampain.iterator();
			while (emailsToSendCompainIterator.hasNext()) {
				String toEmailAddress = (String) emailsToSendCompainIterator.next();
				SendMailTLS.email(fromEmailAddress, toEmailAddress, business.getActionTitle(), business.getMessageText());
			}
			
		}
		
	}

	private List<AltertBusiness> getEmailAlterBusinessList() {
		// TODO Auto-generated method stub
		return null;
	}
}
