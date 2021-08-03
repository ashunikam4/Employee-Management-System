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
import com.ems.webapp.entity.Employee;

public class EditEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(EditEmployeeController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		// If session is not admin session, logout the user
		if (uid != "admin") {
			logger.debug("Cannot provide admin access");
			response.sendRedirect("login.jsp");
			return;
		}

		// emp Id is embedded in get request
		int eid = 0;
		try {
			eid = Integer.parseInt(request.getParameter("eid"));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("editError", "yes");
			request.getRequestDispatcher("viewEmp").forward(request, response);
		}

		// Get existing employee details to edit on
		Employee emp = EmployeeDao.getEmployee(eid);

		if (emp == null) {
			logger.debug("Required Employee record not found");
			request.setAttribute("editError", "yes");
			request.getRequestDispatcher("viewEmp").forward(request, response);
		}

		request.setAttribute("emp", emp);
		request.getRequestDispatcher("editEmp.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
