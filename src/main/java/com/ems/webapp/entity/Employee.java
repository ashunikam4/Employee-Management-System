package com.ems.webapp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Employee {
	private int eid;
	private String fname;
	private String lname;
	private Date dob;
	private int deptID;
	private String dept;
	private String email;
	private static final Logger logger = LogManager.getLogger(Employee.class.getName());

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(dob);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}
