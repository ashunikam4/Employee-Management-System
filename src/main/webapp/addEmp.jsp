<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="styles.css">
	<script src= 'scripts.js' type='text/javascript'></script>
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
	%>

	<div class="topnav">
		<a class="active" href="/webapp/adminHome.jsp">Home</a> <a
			href="/webapp/viewEmp">View all</a> <a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">
				<h1>Add New Employee</h1>

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
							<c:out value="Error encountered while adding employee" />
						</p>
					</c:when>
					<c:when test="${requestScope.success != null}">
						<p>
							<c:out value="Employee added successfully" />
						</p>
					</c:when>
				</c:choose>

				<form method="post" action="addEmp">
					<div class="container">
						<input type="text" name="fname" placeholder="Enter first name" required /><br /> 
						<input type="text" name="lname" placeholder="Enter last name" required /><br /> 
						<input type="text" name="dob" placeholder="Enter DOB" onfocus="(this.type='date')" required /> <br /> 
						<input type="text" name="email" placeholder="Enter email address" /><br />
						<input type="text" name="dept" placeholder="Enter department name" required /><br /> 
						<input type="number" name="eid" placeholder="Choose user Id" required><br /> 
						<input type="password" name="pwd" id="pwd" placeholder="Choose password" required /><br /> 
						<input type="password" id="password2" oninput="checkPwd(this)" placeholder="Confirm Password" required /><br />
						<button type="submit">Add</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>