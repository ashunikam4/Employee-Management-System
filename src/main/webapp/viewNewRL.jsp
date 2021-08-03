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
		<a class="active" href="/webapp/home.jsp">Home</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<h1>List of Outstanding R/Ls</h1>
			<c:choose>
				<c:when
					test="${sessionScope.uid == null || sessionScope.uid == 'admin'}">
					<%
						response.sendRedirect("login.jsp");
					%>
				</c:when>
				<c:when test="${requestScope.error != null}">
					<p>
						<c:out value="Error encountered creating report" />
					</p>
				</c:when>
				<c:when test="${requestScope.success != null}">
					<p>
						<c:out value="Status Report submitted successfully" />
					</p>
				</c:when>
			</c:choose>

			<table>
				<tr>
					<th>R/L ID</th>
					<th>R/L Type</th>
					<th>Details</th>
					<th>R/L Creation Date</th>
					<th></th>
				</tr>
				<c:forEach items="${requestScope.RLList}" var="rl">
					<tr>
						<td>${rl.rid}</td>
						<td>${rl.type}</td>
						<td>${rl.details}</td>
						<td>${rl.createDate}</td>
						<td class="edit"><a href="/webapp/createReport?rid=${rl.rid}">Create Report</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>