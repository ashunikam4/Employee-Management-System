package com.ems.webapp.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.EmployeeDao;
import com.ems.webapp.dao.RLDao;
import com.ems.webapp.dao.StatusReportDao;
import com.ems.webapp.entity.Employee;
import com.ems.webapp.entity.RL;
import com.ems.webapp.entity.StatusReport;

public class SubmitReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SubmitReportController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		if (uid == null || uid == "admin") {
			logger.debug("Cannot provide user access");
			response.sendRedirect("login.jsp");
			return;
		}

		int eid = 0;
		try {
			eid = Integer.parseInt(uid);
		} catch (Exception e) { // If uid is not integer, user must not be valid
			logger.error(e);
			response.sendRedirect("login.jsp");
			return;
		}

		int rid = 0;
		try {
			rid = Integer.parseInt(request.getParameter("rid"));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("error", "exists");
			request.setAttribute("rid", rid);
			request.getRequestDispatcher("createReport").forward(request, response);
			return;
		}

		// Fetch the employee and RL details
		Employee emp = EmployeeDao.getEmployee(eid);
		RL rl = RLDao.getRL(rid);

		// Check again for department validity
		if (rl == null || emp == null || emp.getDeptID() != rl.getDeptID()) {
			logger.debug("Error fetching data OR department validation failed");
			request.setAttribute("error", "exists");
			request.setAttribute("rid", rid);
			request.getRequestDispatcher("createReport").forward(request, response);
			return;
			// Check if report is not duplicated, (1 emp, 1 RL) -> 1 report
		} else if (StatusReportDao.reportExists(eid, rid)) {
			logger.debug("Cannot file duplicate reports");
			request.setAttribute("duplError", "exists");
			request.setAttribute("rid", rid);
			request.getRequestDispatcher("createReport").forward(request, response);
			return;
		}
		String comments = request.getParameter("comments");

		// Create the entity
		StatusReport sr = new StatusReport();
		sr.setRl(rl);
		sr.setRid(rid);
		sr.setCreateDate(new Date());
		sr.setEid(eid);
		sr.setComments(comments);

		// Add the report to db
		if (StatusReportDao.addReport(sr)) {
			request.setAttribute("success", "yes");
			request.getRequestDispatcher("checkNewRL").forward(request, response);
		} else {
			request.setAttribute("error", "yes");
			request.setAttribute("rid", rid);
			request.getRequestDispatcher("createReport").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
