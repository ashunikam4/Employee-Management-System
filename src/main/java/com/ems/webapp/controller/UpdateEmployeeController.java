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
import com.ems.webapp.utility.EmployeeUtil;

public class UpdateEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UpdateEmployeeController.class.getName());

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

		// Fetch the entered details
		int eid = 0;
		try {
			eid = Integer.parseInt(request.getParameter("eid"));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("eid", eid);
			request.getRequestDispatcher("editEmp").forward(request, response);
		}

		String fname = (String) request.getParameter("fname");
		String lname = (String) request.getParameter("lname");
		String strdob = (String) request.getParameter("dob");
		String email = (String) request.getParameter("email");
		String dept = (String) request.getParameter("dept");

		// Create the entity
		Employee emp = new Employee();
		emp.setEid(eid);
		emp.setFname(fname);
		emp.setLname(lname);
		emp.setEmail(email);

		// Parse the DOB input
		try {
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(strdob);
			if (EmployeeUtil.isOld(dob))
				emp.setDob(dob);
			else {
				logger.debug("Entered age is less than 24 yrs");
				request.setAttribute("invalidDOB", "yes");
				emp.setDept(dept);
				request.setAttribute("emp", emp);
				request.getRequestDispatcher("editEmp.jsp").forward(request, response);
				return;
			}
		} catch (ParseException e) {
			logger.error(e);
			request.setAttribute("error", "yes");
			emp.setDept(dept);
			request.setAttribute("emp", emp);
			request.getRequestDispatcher("editEmp.jsp").forward(request, response);
			return;
		}

		// check if Entered department name exists
		int deptID = DepartmentDao.getDeptID(dept);
		if (deptID >= 0)
			emp.setDeptID(deptID);
		else {
			logger.debug("Department Name is invalid");
			request.setAttribute("invalidDept", "yes");
			request.setAttribute("emp", emp);
			request.getRequestDispatcher("editEmp.jsp").forward(request, response);
			return;
		}

		// update the 'eid' record of employee
		if (EmployeeDao.updateEmployee(eid, emp))
			request.setAttribute("success", "yes");
		else
			request.setAttribute("error", "yes");

		// Display the details on same page, for more editing
		request.setAttribute("emp", emp);
		request.getRequestDispatcher("editEmp.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
