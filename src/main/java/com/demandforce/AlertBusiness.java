package com.demandforce;

import java.util.List;

public class AlertBusiness {

	private String BusinessName;
	private String BusinessID;
	private String BusinessEmailAddress;
	private List<String> emails;
	private List<String> SlowDates;
	
	private String messageTitle;
	private String messageText;
	private String actionTitle;
	private String actionUrl;
	
	public String getBusinessName() {
		return BusinessName;
	}
	public void setBusinessName(String businessName) {
		BusinessName = businessName;
	}
	public String getBusinessID() {
		return BusinessID;
	}
	public void setBusinessID(String businessID) {
		BusinessID = businessID;
	}
	public List<String> getSlowDates() {
		return SlowDates;
	}
	public void setSlowDates(List<String> slowDates) {
		SlowDates = slowDates;
	}
	public String getBusinessEmailAddress() {
		return BusinessEmailAddress;
	}
	public void setBusinessEmailAddress(String businessEmailAddress) {
		BusinessEmailAddress = businessEmailAddress;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getActionTitle() {
		return actionTitle;
	}
	public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	
	
}
