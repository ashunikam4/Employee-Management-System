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

import com.ems.webapp.dao.DepartmentDao;
import com.ems.webapp.dao.RLDao;
import com.ems.webapp.entity.RL;

public class AddRLController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AddRLController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		if (uid != "admin") {
			logger.debug("Cannot provide admin access");
			response.sendRedirect("login.jsp");
			return;
		}

		// Fetch form input data
		String type = (String) request.getParameter("type");
		String details = (String) request.getParameter("details");
		String dept = (String) request.getParameter("dept");
		Date createDate = new Date();

		// Create entity
		RL rl = new RL();
		rl.setType(type);
		rl.setDetails(details);
		rl.setCreateDate(createDate);

		// Check if Entered department name is valid
		int deptID = DepartmentDao.getDeptID(dept);
		if (deptID >= 0)
			rl.setDeptID(deptID);
		else {
			logger.debug("Department name is not valid");
			request.setAttribute("invalidDept", "yes");
			request.getRequestDispatcher("addRL.jsp").forward(request, response);
			return;
		}

		if (RLDao.addRL(rl))
			request.setAttribute("success", "yes");
		else
			request.setAttribute("error", "yes");
		request.getRequestDispatcher("addRL.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
