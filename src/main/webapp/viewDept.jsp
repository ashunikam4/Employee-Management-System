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
			<% response.sendRedirect("login.jsp"); %>
		</c:when>
	</c:choose>

	<div class="topnav">
		<a class="active" href="/webapp/adminHome.jsp">Home</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<h1>List of Departments</h1>
			<table>
				<tr>
					<th>Department ID</th>
					<th>Department Name</th>
				</tr>
				<c:forEach items="${requestScope.deptList}" var="dept">
					<tr>
						<td>${dept.did}</td>
						<td>${dept.name}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>