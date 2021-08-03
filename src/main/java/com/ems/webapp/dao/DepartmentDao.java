package com.ems.webapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.entity.Department;

public class DepartmentDao {
	private static final Logger logger = LogManager.getLogger(DepartmentDao.class.getName());

	public static int getDeptID(String deptName) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from department where dept_name = '" + deptName + "'");

			int deptID = -1;
			if (rs.next()) {
				deptID = rs.getInt(1);
				logger.debug("Department Record found");
			} else {
				logger.debug("Department Record not found");
			}
			con.close();
			return deptID;
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}

	public static String getDeptName(int deptID) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from department where dept_id = " + deptID);

			String deptName = "";
			if (rs.next()) {
				deptName = rs.getString(2);
				logger.debug("Department Record found");
			} else {
				logger.debug("Department Record not found");
			}
			con.close();
			return deptName;
		} catch (Exception e) {
			logger.error(e);
			return "";
		}
	}

	public static boolean addDepartment(Department dept) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			String deptName = dept.getName();
			int rowsUpdated = stmt.executeUpdate("insert into department(dept_name) values('" + deptName + "')");
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("Department record added");
				return true;
			}
			logger.debug("Couldn't add Department record added");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public static List<Department> getallDepartments() {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			ArrayList<Department> res = new ArrayList<Department>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from department");

			while (rs.next()) {
				int deptId = rs.getInt(1);
				String deptName = rs.getString(2);
				Department dept = new Department();
				dept.setDid(deptId);
				dept.setName(deptName);
				res.add(dept);
			}
			con.close();
			return res;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<Department>();
		}
	}

}
