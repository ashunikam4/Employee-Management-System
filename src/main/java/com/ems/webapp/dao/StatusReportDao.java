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

import com.ems.webapp.entity.RL;
import com.ems.webapp.entity.StatusReport;

public class StatusReportDao {
	private static final Logger logger = LogManager.getLogger(StatusReportDao.class.getName());

	public static boolean reportExists(int eid, int rid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			PreparedStatement stmt = con
					.prepareStatement("select * from statusreport where emp_id = ? and " + "compl_id = ?");
			stmt.setInt(1, eid);
			stmt.setInt(2, rid);
			ResultSet rs = stmt.executeQuery();
			boolean exists = false;
			if (rs.next()) {
				logger.debug("Report record found");
				exists = true;
			} else {
				logger.debug("Report record not found");
			}
			con.close();
			return exists;
		} catch (Exception e) {
			logger.error(e);
			return true; // pessimistic response
		}
	}

	public static boolean addReport(StatusReport sr) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			PreparedStatement stmt = con.prepareStatement("insert into statusreport (compl_id, "
					+ "emp_id, comments, create_date, dept_id) values (?,?,?,?,?)");
			stmt.setInt(1, sr.getRid());
			stmt.setInt(2, sr.getEid());
			stmt.setString(3, sr.getComments());
			stmt.setString(4, sr.getCreateDate());
			stmt.setInt(5, sr.getRl().getDeptID());
			int rowsUpdated = stmt.executeUpdate();
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("Report Record added");
				return true;
			}
			logger.debug("Cannot add report record");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public static StatusReport getReport(int sid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from statusreport where rpt_id = " + sid);

			StatusReport sr = null;
			if (rs.next()) {
				int rid = rs.getInt(1);
				RL rl = RLDao.getRL(rid);
				int eid = rs.getInt(3);
				String comments = rs.getString(4);
				Date createDate = rs.getDate(5);

				logger.debug("Report record found");
				sr = new StatusReport();
				sr.setSid(sid);
				sr.setRid(rid);
				sr.setRl(rl);
				sr.setEid(eid);
				sr.setComments(comments);
				sr.setCreateDate(createDate);
			} else {
				logger.debug("Report record not found");
			}
			con.close();
			return sr;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static List<StatusReport> getAllStatusReports(int eid) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from statusreport where emp_id = " + eid);

			ArrayList<StatusReport> res = new ArrayList<StatusReport>();
			while (rs.next()) {
				int rid = rs.getInt(1);
				int sid = rs.getInt(2);
				RL rl = RLDao.getRL(rid);
				String comments = rs.getString(4);
				Date createDate = rs.getDate(5);

				StatusReport sr = new StatusReport();
				sr.setSid(sid);
				sr.setRid(rid);
				sr.setRl(rl);
				sr.setComments(comments);
				sr.setCreateDate(createDate);
				res.add(sr);
			}
			con.close();
			return res;
		} catch (Exception e) {
			logger.error(e);
			return new ArrayList<StatusReport>();
		}
	}

	public static boolean updateReport(StatusReport sr) {
		Connection con = null;
		try {
			con = BaseDao.getConnection();
			PreparedStatement stmt = con.prepareStatement("update statusreport set comments = ? " + "where rpt_id = ?");
			stmt.setString(1, sr.getComments());
			stmt.setInt(2, sr.getSid());
			int rowsUpdated = stmt.executeUpdate();
			con.close();

			if (rowsUpdated == 1) {
				logger.debug("Report record updated");
				return true;
			}
			logger.debug("Cannot update report record");
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
}
