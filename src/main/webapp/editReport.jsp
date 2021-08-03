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
		<a href="/webapp/viewOldReports">View all</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">

				<h1>Edit Status Report</h1>

				<c:choose>
					<c:when
						test="${sessionScope.uid == null || sessionScope.uid == 'admin'}">
						<%
							response.sendRedirect("login.jsp");
						%>
					</c:when>
					<c:when test="${requestScope.error != null}">
						<p>
							<c:out value="Error encountered while updating report" />
						</p>
					</c:when>
					<c:when test="${requestScope.success != null}">
						<p>
							<c:out value="Status Report updated successfully" />
						</p>
					</c:when>
				</c:choose>

				<form method="post" action="updateReport">
					<div class="container">
						Report ID <input type="text" name="sid" value="${sr.sid}" readonly /><br />
						R/L ID <input type="text" name="rid" value="${sr.rl.rid}" readonly /><br />
						R/L Type <input type="text" name="rl_type" value="${sr.rl.type}" readonly /><br /> 
						R/L Details <input type="text" name="rl_details" value="${sr.rl.details}" readonly /><br />
						Comments <textarea name="comments" rows="8" cols="41">${sr.comments}</textarea>
						<button type="submit">Update</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>