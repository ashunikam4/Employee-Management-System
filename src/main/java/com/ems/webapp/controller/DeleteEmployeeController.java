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
import com.ems.webapp.dao.LoginDao;
import com.ems.webapp.entity.LoginDetails;

public class DeleteEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(DeleteEmployeeController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		if (uid != "admin") {
			logger.debug("Cannot provide admin access");
			response.sendRedirect("login.jsp");
			return;
		}

		// Employee id to delete is embedded in get request
		int eid = 0;
		try {
			eid = Integer.parseInt(request.getParameter("eid"));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("delError", "exists");
			request.getRequestDispatcher("viewEmp").forward(request, response);
			return;
		}
		
		LoginDetails login = LoginDao.getAccessLevel(eid); // get user permissions
		if(login == null) {
			request.setAttribute("delError", "exists");
			request.getRequestDispatcher("viewEmp").forward(request, response);
			return;
		} else if(login.getAdminAccess()) {
			logger.error("Cannot delete Admin user");
			request.setAttribute("adminError", "exists");
			request.getRequestDispatcher("viewEmp").forward(request, response);
			return;
		}
		
		if (EmployeeDao.deleteEmployee(eid))
			request.setAttribute("success", "exists");
		else
			request.setAttribute("delError", "exists");

		request.getRequestDispatcher("viewEmp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
