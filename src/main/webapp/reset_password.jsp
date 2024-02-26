<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="updatePassword" method="post">
		New Password: <input type="password" name="password"> Confirm
		Password: <input type="password" name="confirmPassword">
		 <input type="hidden" name="token" value="${token}">
			 <input type="submit" value="Reset Password">
	</form>
	
	<%
	String token=request.getParameter("token");
	String email=request.getParameter("email");
	%>
	<%= token %>
	<%= email %>
</body>'
</html>