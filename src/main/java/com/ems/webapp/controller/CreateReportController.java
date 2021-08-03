package com.ems.webapp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.EmployeeDao;
import com.ems.webapp.dao.RLDao;
import com.ems.webapp.entity.Employee;
import com.ems.webapp.entity.RL;

public class CreateReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CreateReportController.class.getName());

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
			request.getRequestDispatcher("checkNewRL").forward(request, response);
			return;
		}

		// Fetch employee and RL details from db
		Employee emp = EmployeeDao.getEmployee(eid);
		RL rl = RLDao.getRL(rid);

		if (rl == null || emp == null) {
			logger.debug("Unable to fetch RL and employee details");
			request.setAttribute("error", "exists");
			request.getRequestDispatcher("checkNewRL").forward(request, response);
			return;
		}

		// Check if the employee is accessing RL from his dept
		if (emp.getDeptID() != rl.getDeptID()) {
			logger.debug("User Logged out for invalid access");
			response.sendRedirect("login.jsp");
			return;
		}

		// load the RL details onto the report creation page
		request.setAttribute("rl", rl);
		request.getRequestDispatcher("submitReport.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
