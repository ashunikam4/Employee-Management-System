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
		<a href="/webapp/checkNewRL">View all</a> 
		<a href="/webapp/logout">Logout</a>
	</div>

	<div class="outer">
		<div class="middle">
			<div class="inner">

				<h1>Create Status Report</h1>

				<c:choose>
					<c:when
						test="${sessionScope.uid == null || sessionScope.uid == 'admin'}">
						<%
							response.sendRedirect("login.jsp");
						%>
					</c:when>
					<c:when test="${requestScope.duplError != null}">
						<p>
							<c:out value="Cannot create duplicate report" />
						</p>
					</c:when>
					<c:when test="${requestScope.error != null}">
						<p>
							<c:out value="Error encountered creating report" />
						</p>
					</c:when>
				</c:choose>

				<form method="post" action="submitReport">
					<div class="container">
						RL ID <input readonly type="text" name="rid" value="${requestScope.rl.rid}" /><br /> 
						RL Type <input type="text" name="rl_type" value="${requestScope.rl.type}" readonly /><br /> 
						RL Details <input type="text" name="rl_details" value="${requestScope.rl.details}" readonly /><br />
						<textarea placeholder="Enter your comments" name="comments"rows="8" cols="41"></textarea>
						<button type="submit">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>