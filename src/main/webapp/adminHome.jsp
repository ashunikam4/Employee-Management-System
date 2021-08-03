<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="styles.css">
</head>
<body>

	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
	%>

	<c:choose>
		<c:when test="${sessionScope.uid != 'admin'}">
			<%
				response.sendRedirect("login.jsp");
			%>
		</c:when>
	</c:choose>

	<div class="topnav">
		<a class="active" href="/webapp/adminHome.jsp">Home</a> <a
			href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">
				<h1>Manage Employees</h1>
				<ul>
					<li><a href="/webapp/addEmp.jsp">Add New Employee</a></li>
					<li><a href="/webapp/viewEmp">View/Edit Existing Employees</a></li>
				</ul>
				<br />
				<h1>Manage Departments</h1>
				<ul>
					<li><a href="/webapp/addDept.jsp">Add New Department</a></li>
					<li><a href="/webapp/viewDept">View Existing Departments</a></li>
				</ul>
				<br />
				<h1>Manage R/Ls</h1>
				<ul>
					<li><a href="/webapp/addRL.jsp">Create new R/L</a></li>
					<li><a href="/webapp/viewRL">View Existing R/Ls</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>