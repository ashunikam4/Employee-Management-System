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
import com.ems.webapp.entity.LoginDetails;
import com.ems.webapp.utility.LoginUtil;

public class EmployeeDao {
	private static final Logger logger = LogManager.getLogger(EmployeeDao.class.getName());

	public static boolean addEmployee(Employee emp, LoginDetails login) {
		Connection con = null;
		try {
			// For adding both employee and records we need atomic transactions
			con = BaseDao.getConnection();
			con.setAutoCommit(false); // enable transactions

			// Add Employee record
			PreparedStatement stmt = con.prepareStatement("insert into employees " + "values(?,?,?,?,?,?)");
			stmt.setInt(1, emp.getEid());
			stmt.setString(2, emp.getFname());
			stmt.setString(3, emp.getLname());
			stmt.setString(4, emp.getDob());
			stmt.setString(5, emp.getEmail());
			stmt.setInt(6, emp.getDeptID());
			stmt.executeUpdate();

			// Add Login details
			PreparedStatement stmt2 = con.prepareStatement("insert into login_master " + "values(?,?,?)");
			stmt2.setInt(1, login.getUid());
			stmt2.setString(2, LoginUtil.encryptMD5(login.getPwd()));
			stmt2.setBoolean(3, login.getAdminAccess());
			stmt2.executeUpdate();

			con.commit(); // end and commit transaction
			con.close();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	public static Employee getEmployee(int eid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			ArrayList<Employee> res = new ArrayList<Employee>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from employees where emp_id = " + eid);

			Employee emp = null;
			if (rs.next()) {
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				Date dob = rs.getDate(4);
				String email = rs.getString(5);
				int deptID = rs.getInt(6);
				String dept = DepartmentDao.getDeptName(deptID);

				logger.debug("Employee record found");
				emp = new Employee();
				emp.setEid(eid);
				emp.setFname(fname);
				emp.setLname(lname);
				emp.setDob(dob);
				emp.setEmail(email);
				emp.setDeptID(deptID);
				emp.setDept(dept);
				res.add(emp);
			} else {
				logger.debug("Employee record not found");
			}
			return emp;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static List<Employee> getallEmployees() {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			ArrayList<Employee> res = new ArrayList<Employee>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from employees");

			while (rs.next()) {
				int eid = rs.getInt(1);
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				Date dob = rs.getDate(4);
				String email = rs.getString(5);
				int deptID = rs.getInt(6);
				String dept = DepartmentDao.getDeptName(deptID);

				Employee emp = new Employee();
				emp.setEid(eid);
				emp.setFname(fname);
				emp.setLname(lname);
				emp.setDob(dob);
				emp.setEmail(email);
				emp.setDept(dept);
				res.add(emp);
			}
			con.close();
			return res;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<Employee>();
		}
	}

	public static boolean updateEmployee(int eid, Employee emp) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			PreparedStatement stmt = con.prepareStatement("update employees set "
					+ "fname = ?, lname = ?, dob = ?, email = ?, dept_id = ? " + "where emp_id = ?");
			stmt.setString(1, emp.getFname());
			stmt.setString(2, emp.getLname());
			stmt.setString(3, emp.getDob());
			stmt.setString(4, emp.getEmail());
			stmt.setInt(5, emp.getDeptID());
			stmt.setInt(6, eid);
			int rowsUpdated = stmt.executeUpdate();
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("Employee record updated");
				return true;
			}
			logger.debug("Couldn't update employee record");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public static boolean deleteEmployee(int eid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			int rowsUpdated = stmt.executeUpdate("delete from employees where emp_id = " + eid);
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("Employee record deleted");
				return true;
			}
			logger.debug("Couldn't delete employee record");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
}
