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

			<h1>List of Submitted Status Reports</h1>

			<c:choose>
				<c:when
					test="${sessionScope.uid == null || sessionScope.uid == 'admin'}">
					<%
						response.sendRedirect("login.jsp");
					%>
				</c:when>

				<c:when test="${requestScope.error != null}">
					<p>
						<c:out value="Error encountered updating report" />
					</p>
				</c:when>
			</c:choose>

			<table>
				<tr>
					<th>Report ID</th>
					<th>R/L ID</th>
					<th>R/L Type</th>
					<th>Details</th>
					<th>Last updated</th>
					<th>Your Comments</th>
					<th></th>
				</tr>
				<c:forEach items="${requestScope.SRList}" var="sr">
					<tr>
						<td>${sr.sid}</td>
						<td>${sr.rid}</td>
						<td>${sr.rl.type}</td>
						<td>${sr.rl.details}</td>
						<td>${sr.createDate}</td>
						<td>${sr.comments}</td>
						<td class="edit"><a href="/webapp/editReport?sid=${sr.sid}">Edit</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>