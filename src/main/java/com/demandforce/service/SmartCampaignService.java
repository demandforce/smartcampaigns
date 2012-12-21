package com.demandforce.service;


public class SmartCampaignService {
	public String[] getSlowDays(String businessId) {
		String sql = "SELECT Date(ScheduledDate) ApptDate, COUNT(*) " +
                "FROM df1..Appointment where BusinessID='124001687' AND ScheduledDate > DATE_ADD(CURDATE(), INTERVAL 4 DAY) GROUP BY ApptDate";
		return null;
	}

}
