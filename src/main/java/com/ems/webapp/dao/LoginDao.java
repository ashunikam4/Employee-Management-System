package com.ems.webapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.entity.LoginDetails;
import com.ems.webapp.utility.LoginUtil;

public class LoginDao {
	private static final Logger logger = LogManager.getLogger(LoginDao.class.getName());
	
	public static LoginDetails validate(String uid, String pwd) {
		Connection con = null;
		try {
			// Check if the user id is int, and encrypt the passwrod
			int numId = Integer.parseInt(uid);
			String encrPwd = LoginUtil.encryptMD5(pwd);
			
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select admin_access from login_master where userid = " + numId
					+ " and password = '" + encrPwd + "'");
			
			LoginDetails login = null;
			if (rs.next()) {
				boolean adminAccess = rs.getBoolean(1); // true -> admin, false-> user

				logger.debug("User Login Record found");
				login = new LoginDetails();
				login.setAdminAccess(adminAccess);
			} else {
				logger.debug("User Login Record not found");
			}
			con.close();
			return login;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public static LoginDetails getAccessLevel(int eid) {
		Connection con = null;
		try {
			// Check if the user id is int, and encrypt the passwrod
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select admin_access from login_master where userid = " + eid);
			
			LoginDetails login = null;
			if (rs.next()) {
				boolean adminAccess = rs.getBoolean(1); // true -> admin, false-> user

				logger.debug("User Login Record found");
				login = new LoginDetails();
				login.setAdminAccess(adminAccess);
			} else {
				logger.debug("User Login Record not found");
			}
			con.close();
			return login;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	} 
}
