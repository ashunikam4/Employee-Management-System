package com.ems.webapp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RL {
	private int rid;
	private String type;
	private String details;
	private Date createDate;
	private int deptID;
	private String dept;
	private boolean status;
	private static final Logger logger = LogManager.getLogger(RL.class.getName());

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCreateDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

}
