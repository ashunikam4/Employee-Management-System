package com.ems.webapp.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ems.webapp.dao.DepartmentDao;
import com.ems.webapp.dao.EmployeeDao;
import com.ems.webapp.entity.Employee;
import com.ems.webapp.entity.LoginDetails;
import com.ems.webapp.utility.EmployeeUtil;

public class AddEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AddEmployeeController.class.getName()); 

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
		
		// Fetch the form input
		int eid = 0;
		try {
			eid = Integer.parseInt(request.getParameter("eid"));
		} catch(Exception e){
			logger.error(e);
			request.setAttribute("error", "yes");
			request.getRequestDispatcher("addEmp.jsp").forward(request, response);
			return;
		}
		
		String fname = (String) request.getParameter("fname");
		String lname = (String) request.getParameter("lname");
		String strdob = (String) request.getParameter("dob");
		String email = (String) request.getParameter("email");
		String dept = (String) request.getParameter("dept");
		String pwd = (String) request.getParameter("pwd");

		// Create the employee entity
		Employee emp = new Employee();
		emp.setEid(eid);
		emp.setFname(fname);
		emp.setLname(lname);
		
		// Parsing the DOB input
		try {
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(strdob);
			if (EmployeeUtil.isOld(dob))
				emp.setDob(dob);
			else {
				logger.debug("Entered age is less than 24 yrs");
				request.setAttribute("invalidDOB", "yes");
				request.getRequestDispatcher("addEmp.jsp").forward(request, response);
				return;
			}
		} catch (ParseException e) {
			logger.error(e);
			request.setAttribute("error", "yes");
			request.getRequestDispatcher("addEmp.jsp").forward(request, response);
			return;
		}

		emp.setEmail(email);

		// Check if entered department exists
		int deptID = DepartmentDao.getDeptID(dept);
		if (deptID >= 0)
			emp.setDeptID(deptID);
		else {
			logger.debug("Entered department name is not valid");
			request.setAttribute("invalidDept", "yes");
			request.getRequestDispatcher("addEmp.jsp").forward(request, response);
			return;
		}

		// Create the logging details entity
		LoginDetails login = new LoginDetails();
		login.setUid(eid);
		login.setPwd(pwd);
		login.setAdminAccess(false);

		// Add the new employee to db
		if (EmployeeDao.addEmployee(emp, login))
			request.setAttribute("success", "yes");
		else
			request.setAttribute("error", "yes");

		request.getRequestDispatcher("addEmp.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
