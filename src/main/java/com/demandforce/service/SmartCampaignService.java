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
	public AlertBusiness getSlowAlertMessage(String businessId) {
		AlertBusiness alertBusiness = null;
		
		if (isNextWeekSlow(businessId)) {
			alertBusiness = getAlertBusiness(businessId);
			alertBusiness.setEmails(getUserEmails(businessId));
			
			alertBusiness.setSlowWeek(getNextWeek().get(Calendar.WEEK_OF_YEAR));
			alertBusiness.setActionTitle("10% Off Next Week");
			alertBusiness.setActionUrl("http://www.demandforced3.com/bp2/createSmartCampaign.jsp?templateId=99");
			alertBusiness.setMessageText("Next week only has a few appoinmts");
			alertBusiness.setMessageTitle("Create a campaing right now.");
		}
		
		return alertBusiness;
	}
	
	public AlertBusiness getAlertBusiness(String businessId) {
		AlertBusiness alertBusiness = new AlertBusiness();
		
		String sql = "SELECT * FROM Business where ID = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessId);

			rs = ps.executeQuery();

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
				if (conn != null) {
					conn.close();
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

			rs = ps.executeQuery();

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
				if (conn != null) {
					conn.close();
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

			rs = ps.executeQuery();

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
				if (conn != null) {
					conn.close();
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

			rs = ps.executeQuery();

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
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static Connection getConnect() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		     
			conn = DriverManager.getConnection(dbUrl, userId, userPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	private static final String dbUrl = "jdbc:mysql://172.16.12.15/df1";
	private static final String userId = "d3";
	private static final String userPassword = "ondemand";	
}
