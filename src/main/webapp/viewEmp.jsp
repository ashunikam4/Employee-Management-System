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
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">

			<h1>List of Employees</h1>

			<c:choose>
				<c:when test="${sessionScope.uid != 'admin'}">
					<%
						response.sendRedirect("login.jsp");
					%>
				</c:when>
				<c:when test="${requestScope.editError != null}">
					<p>
						<c:out value="Error encountered while loading the employee details" />
					</p>
				</c:when>
				<c:when test="${requestScope.delError != null}">
					<p>
						<c:out value="Error encountered while deleting the employee" />
					</p>
				</c:when>
				<c:when test="${requestScope.adminError != null}">
					<p>
						<c:out value="Cannot delete a admin user" />
					</p>
				</c:when>
				<c:when test="${requestScope.success != null}">
					<p>
						<c:out value="Employee deleted successfully" />
					</p>
				</c:when>
			</c:choose>


			<table>
				<tr>
					<th>User ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>DOB</th>
					<th>Email</th>
					<th>Department</th>
					<th></th>
					<th></th>
				</tr>
				<c:forEach items="${requestScope.empList}" var="emp">
					<tr>
						<td>${emp.eid}</td>
						<td>${emp.fname}</td>
						<td>${emp.lname}</td>
						<td>${emp.dob}</td>
						<td>${emp.email}</td>
						<td>${emp.dept}</td>
						<td class="edit"><a href="/webapp/editEmp?eid=${emp.eid}">Edit</a></td>
						<td class="edit"><a href="/webapp/delEmp?eid=${emp.eid}">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>