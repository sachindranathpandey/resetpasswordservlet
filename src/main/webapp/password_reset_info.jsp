<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	String token=request.getParameter("token");
	%>
	<%= token %>
	<h1>An email has sent successfully. Please check youe email & reset the pasword</h1>
</body>
</html>