package com.ems.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.entity.Employee;
import com.ems.webapp.entity.RL;

public class RLDao {
	private static final Logger logger = LogManager.getLogger(RLDao.class.getName());

	public static boolean addRL(RL rl) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"insert into compliance(rl_type, details, create_date, dept_id) " + "values(?,?,?,?)");
			stmt.setString(1, rl.getType());
			stmt.setString(2, rl.getDetails());
			stmt.setString(3, rl.getCreateDate());
			stmt.setInt(4, rl.getDeptID());
			int rowsUpdated = stmt.executeUpdate();
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("RL record added");
				return true;
			}
			logger.debug("Coundn't add RL record");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public static RL getRL(int rid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from compliance where compl_id = " + rid);

			RL rl = null;
			if (rs.next()) {
				String type = rs.getString(2);
				String details = rs.getString(3);
				Date creatDate = rs.getDate(4);
				int deptID = rs.getInt(5);
				String dept = DepartmentDao.getDeptName(deptID);

				logger.debug("RL record found");
				rl = new RL();
				rl.setRid(rid);
				rl.setType(type);
				rl.setDetails(details);
				rl.setCreateDate(creatDate);
				rl.setDeptID(deptID);
				rl.setDept(dept);
			} else {
				logger.debug("RL record not found");
			}
			con.close();
			return rl;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static List<RL> getallRLs() {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			ArrayList<RL> res = new ArrayList<RL>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from compliance");

			while (rs.next()) {
				int rid = rs.getInt(1);
				String type = rs.getString(2);
				String details = rs.getString(3);
				Date creatDate = rs.getDate(4);
				int deptID = rs.getInt(5);
				String dept = DepartmentDao.getDeptName(deptID);

				RL rl = new RL();
				rl.setRid(rid);
				rl.setType(type);
				rl.setDetails(details);
				rl.setCreateDate(creatDate);
				rl.setDept(dept);

				// Query the db again to get status for this RL
				try {
					// count employees in department
					stmt = con.createStatement();
					ResultSet tmp = stmt.executeQuery("select count(*) from employees where dept_id = " + deptID);
					tmp.next();
					int numEmployees = tmp.getInt(1);

					// count number of report submitted on RL
					stmt = con.createStatement();
					tmp = stmt.executeQuery("select count(*) from statusreport where compl_id = " + rid);
					tmp.next();
					int numReports = tmp.getInt(1);

					// If everyone in dept submitted a report, the status of RL is closed
					boolean statusClosed = numEmployees == numReports;
					rl.setStatus(statusClosed);

				} catch (Exception e) {
					logger.error(e);
					rl.setStatus(false);
				}
				res.add(rl);
			}
			con.close();
			return res;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<RL>();
		}
	}

	public static List<RL> getNewRLs(int eid) {
		Connection con = null;
		try {
			Employee emp = EmployeeDao.getEmployee(eid);
			if (emp == null) {
				logger.debug("Employee record not found");
				return new ArrayList<RL>();
			}
			int deptId = emp.getDeptID();

			// New RLs : RLs for which employee has not submitted a report yet
			// approach : get all the RLs for which no record exists in employee's status
			// report subtable
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT t1.compl_id, t1.rl_type, t1.details, t1.create_date, t1.dept_id "
					+ "FROM compliance t1 " + "LEFT JOIN (SELECT compl_id from statusreport WHERE emp_id = " + eid
					+ ") t2 ON t2.compl_id = t1.compl_id " + "WHERE t2.compl_id IS NULL AND t1.dept_id = " + deptId);

			ArrayList<RL> res = new ArrayList<RL>();
			while (rs.next()) {
				int rid = rs.getInt(1);
				String type = rs.getString(2);
				String details = rs.getString(3);
				Date creatDate = rs.getDate(4);

				RL rl = new RL();
				rl.setRid(rid);
				rl.setType(type);
				rl.setDetails(details);
				rl.setCreateDate(creatDate);
				res.add(rl);
			}

			con.close();
			return res;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<RL>();
		}
	}
}
