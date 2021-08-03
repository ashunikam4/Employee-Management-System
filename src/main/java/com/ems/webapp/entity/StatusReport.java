package com.ems.webapp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StatusReport {
	private int sid;
	private int rid;
	private int eid;
	private RL rl;
	private String comments;
	private Date createDate;
	private static final Logger logger = LogManager.getLogger(StatusReport.class.getName());

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public RL getRl() {
		return rl;
	}

	public void setRl(RL rl) {
		this.rl = rl;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
}
