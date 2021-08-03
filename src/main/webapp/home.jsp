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
		<c:when
			test="${sessionScope.uid == null || sessionScope.uid == 'admin'}">
			<%
				response.sendRedirect("login.jsp");
			%>
		</c:when>
	</c:choose>

	<div class="topnav">
		<a class="active" href="/webapp/home.jsp">Home</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">
				<h1>Manage your Status reports</h1>
				<ul>
					<li><a href="/webapp/checkNewRL">View all Outstanding R/Ls</a></li>
					<li><a href="/webapp/viewOldReports">View/Edit Existing Status Reports</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>