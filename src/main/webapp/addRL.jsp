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
		<a href="/webapp/viewRL">View all</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">

				<h1>Create New R/L</h1>

				<c:choose>
					<c:when test="${sessionScope.uid != 'admin'}">
						<%
							response.sendRedirect("login.jsp");
						%>
					</c:when>
					<c:when test="${requestScope.invalidDept != null}">
						<p>
							<c:out value="Please enter a valid department name" />
						</p>
					</c:when>
					<c:when test="${requestScope.error != null}">
						<p>
							<c:out value="Error encountered while creating R/L" />
						</p>
					</c:when>
					<c:when test="${requestScope.success != null}">
						<p>
							<c:out value="R/L created successfully" />
						</p>
					</c:when>
				</c:choose>


				<form method="post" action="addRL">
					<div class="container">
						<input type="text" placeholder="Enter R/L Type" name="type" /><br />
						<input type="text" placeholder="Enter Department Name" name="dept" required /><br />
						<textarea placeholder="Enter R/L Details" name="details" rows="8" cols="41"></textarea>
						<button type="submit">Create</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>