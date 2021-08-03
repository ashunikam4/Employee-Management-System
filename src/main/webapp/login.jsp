<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<c:when test="${sessionScope.uid != null}">
			<%
				response.sendRedirect("logout");
			%>
		</c:when>
	</c:choose>

	<div class="outer">
		<div class="middle">
			<div class="inner">

				<h1>Welcome to EMS</h1>

				<c:if test="${requestScope.loginError != null}">
					<p>
						<c:out value="Invalid login details" />
					</p>
				</c:if>

				<form method="post" action="login">
					<div class="container">
						<input type="number" placeholder="Enter user Id" name="uid" required /><br /> 
						<input type="password" placeholder="Enter Password" name="pwd" required /><br />
						<button type="submit">Login</button>
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>
