package com.demandforce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.demandforce.AlertBusiness;


public class SmartCampaignService {
    private static Connection conn = null;
	public AlertBusiness getSlowAlertMessage(String businessId) {
		AlertBusiness alertBusiness = null;
		
		if (isNextWeekSlow(businessId)) {
			alertBusiness = getAlertBusiness(businessId);
			alertBusiness.setEmails(getUserEmails(businessId));
			
			long templateId = getMostPopularCampaignTemplateId(businessId);
			String templateName = getMostPopularCampaignTemplateName(templateId);
			System.out.println("templateId = " + templateId +", templateName = " + templateName);
			
			alertBusiness.setSlowWeek(getNextWeek().get(Calendar.WEEK_OF_YEAR));
			alertBusiness.setActionTitle(templateName);
			alertBusiness.setActionUrl("https://172.16.12.15/bp2/campaigns/promotion/step/selectMessageProcess.jsp?campaignId=&campaignTemplateId=" + templateId + "&cat=1");
			alertBusiness.setMessageText("We suggest you create a campaign. Here is one that works best for businesses in your industry.");
			alertBusiness.setMessageTitle("Scheduled appointments for next week is lower than your past history.");
		}
		
		return alertBusiness;
	}
	
	private AlertBusiness getAlertBusiness(String businessId) {
		AlertBusiness alertBusiness = new AlertBusiness();
		
		String sql = "SELECT name,email FROM Business where ID = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);

            long time = System.currentTimeMillis();
            rs = ps.executeQuery();
            System.out.println(time - System.currentTimeMillis());

			if (rs.next()) {
				alertBusiness.setBusinessID(businessId);
				alertBusiness.setBusinessName(rs.getString("name"));
				alertBusiness.setBusinessEmailAddress(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return alertBusiness;
	}

	private boolean isNextWeekSlow(String businessId) {
		Calendar calendar = getNextWeek();
		
		int year = calendar.get(Calendar.YEAR);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		
		int nextWeekApptCount = getWeekApptCount(businessId, year, weekOfYear);
		int avgApptCount = getWeekAvgApptCount(businessId, weekOfYear);
		
		System.out.println("nextWeekApptCount = " + nextWeekApptCount);
		System.out.println("avgApptCount = " + avgApptCount);
		
		return nextWeekApptCount < avgApptCount;
	}

	private Calendar getNextWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 24 * 7);	// next week
		return calendar;
	}
	
	private int getWeekApptCount(String businessId, int year, int weekOfYear) {
		String sql = "SELECT `Count` AS ApptCount " +
                "FROM WeeklyScheduledAppointmentsCount where BusinessID = ? AND Year = ? AND WeekOfYear = ?";
		int result = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);
			ps.setInt(2, year);
			ps.setInt(3, weekOfYear);
            long time = System.currentTimeMillis();
            rs = ps.executeQuery();
            System.out.println(time - System.currentTimeMillis());
			if (rs.next()) {
				result = rs.getInt("ApptCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	private long getMostPopularCampaignTemplateId(String businessId) {
		String sql = "SELECT s.CustomCampaigntemplateId " +
                "FROM temp_conversion_stats AS s JOIN Business AS b ON s.Industry = b.Industry WHERE b.Id = ? ORDER BY s.ConversionCount DESC LIMIT 1";
		long result = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);
            rs = ps.executeQuery();
            
			if (rs.next()) {
				result = rs.getLong("CustomCampaigntemplateId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	private String getMostPopularCampaignTemplateName(long templateId) {
		String sql = "SELECT Name " +
                "FROM CustomCampaignTemplate WHERE Id = ?";
		String result = "";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, templateId);
            rs = ps.executeQuery();
            
			if (rs.next()) {
				result = rs.getString("Name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}	
	private int getWeekAvgApptCount(String businessId, int weekOfYear) {
		String sql = "SELECT avg(`Count`) AS ApptCount " +
                "FROM WeeklyScheduledAppointmentsCount where BusinessID = ? AND WeekOfYear = ?";
		int result = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);
			ps.setInt(2, weekOfYear);
            long time = System.currentTimeMillis();
            rs = ps.executeQuery();
            System.out.println(time - System.currentTimeMillis());
			if (rs.next()) {
				result = rs.getInt("ApptCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	private List<String> getUserEmails(String businessId) {
		String sql = "SELECT u.email " +
                " FROM User AS u JOIN BusinessUser AS bu" +
                "   ON u.id = bu.UserId" +
                "   JOIN Business AS b " +
                "   ON bu.BusinessId = b.id" +
                " where BusinessID = ?";
		List<String> result = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);
            long time = System.currentTimeMillis();
			rs = ps.executeQuery();
            System.out.println(time - System.currentTimeMillis());
			if (rs.next()) {
				result.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static Connection getConnect() throws SQLException {
		try {
            if(conn == null || conn.isClosed()){
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbUrl, userId, userPassword);
            }

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	private static final String dbUrl = "jdbc:mysql://172.16.12.15/df1";
	private static final String userId = "d3";
	private static final String userPassword = "ondemand";	
}
