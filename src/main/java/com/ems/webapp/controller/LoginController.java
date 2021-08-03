package com.ems.webapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.LoginDao;
import com.ems.webapp.entity.LoginDetails;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");

		// If session already exists, logout the user
		if (uid != null) {
			logger.debug("Logging the user out for another login");
			request.getRequestDispatcher("logout").forward(request, response);
			return;
		}

		// If session does not exist
		uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		LoginDetails login = LoginDao.validate(uid, pwd); // get user permissions

		if (login == null) { // Invalid login
			logger.debug("Login details are invalid");
			request.setAttribute("loginError", "exists");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if (login.getAdminAccess()) { // Admin access
			// Since view to all the admin pages is similar,
			// session.uid = 'admin'
			logger.debug("User logged in with admin access");
			session.setAttribute("uid", "admin");
			response.sendRedirect("adminHome.jsp");
		} else { // User access
			logger.debug("User logged in with normal access");
			session.setAttribute("uid", uid);
			response.sendRedirect("home.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
