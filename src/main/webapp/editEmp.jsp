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

	<div class="topnav">
		<a class="active" href="/webapp/adminHome.jsp">Home</a> 
		<a href="/webapp/viewEmp">View all</a> 
		<a href="/webapp/logout">Logout</a>
	</div>



	<div class="outer">
		<div class="middle">
			<div class="inner">
				<h1>Edit Employee</h1>

				<c:choose>
					<c:when test="${sessionScope.uid != 'admin'}">
						<%
							response.sendRedirect("login.jsp");
						%>
					</c:when>
					<c:when test="${requestScope.invalidDept != null}">
						<p>
							<c:out value="Please Enter Valid Department Name" />
						</p>
					</c:when>
					<c:when test="${requestScope.invalidDOB != null}">
						<p>
							<c:out value="Employee Age must be greater than 24 years" />
						</p>
					</c:when>
					<c:when test="${requestScope.error != null}">
						<p>
							<c:out value="Error encountered while updating employee details" />
						</p>
					</c:when>
					<c:when test="${requestScope.success != null}">
						<p>
							<c:out value="Employee details updated successfully" />
						</p>
					</c:when>
				</c:choose>
				<form method="post" action="updateEmp?eid=${requestScope.emp.eid}">
					<div class="container">
						First Name <input type="text" name="fname" value="${requestScope.emp.fname}" required /><br /> 
						Last Name <input type="text" name="lname" value="${requestScope.emp.lname}" required /><br />
						DOB <input type="date" name="dob" value="${requestScope.emp.dob}" required /> <br /> 
						Email <input type="text" name="email" value="${requestScope.emp.email}" /><br />
						Department <input type="text" name="dept" value="${requestScope.emp.dept}" required /><br />
						<button type="submit">Update</button>
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>