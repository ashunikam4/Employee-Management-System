package com.ems.webapp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.StatusReportDao;
import com.ems.webapp.entity.StatusReport;

public class EditReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EditReportController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		int sid = 0;
		try {
			sid = Integer.parseInt(request.getParameter("sid"));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("error", "exists");
			request.getRequestDispatcher("viewOldReports").forward(request, response);
			return;
		}

		// Get Status report to load on the page
		StatusReport sr = StatusReportDao.getReport(sid);
		if (sr == null) {
			logger.debug("Cannot fetch report from DB");
			request.setAttribute("error", "exists");
			request.getRequestDispatcher("viewOldReports").forward(request, response);
			return;
		}

		// If Status report does not belong to this employee
		if (eid != sr.getEid()) {
			logger.debug("User logged out for invalid access");
			response.sendRedirect("login.jsp");
			return;
		}

		request.setAttribute("sr", sr);
		request.getRequestDispatcher("editReport.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
