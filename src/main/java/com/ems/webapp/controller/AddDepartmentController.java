package com.ems.webapp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.DepartmentDao;
import com.ems.webapp.entity.Department;

public class AddDepartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AddDepartmentController.class.getName()); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		if (uid != "admin") {
			logger.debug("Cannot provide admin access");
			response.sendRedirect("login.jsp");
			return;
		}

		Department dept = new Department();
		dept.setName(request.getParameter("name"));

		// Department added directly.
		// No duplication warning raised
		if (DepartmentDao.addDepartment(dept))
			request.setAttribute("success", "yes");
		else
			request.setAttribute("error", "yes");
		
		request.getRequestDispatcher("addDept.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}