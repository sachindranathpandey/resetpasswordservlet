package com;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
	
	private String token=null;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 token = request.getParameter("token");
		System.out.println("token from request ");
		System.out.println("reset password servlet doGet Called");
		System.out.println("isLInkActive  "+checkLinkExpiration());
		// Here we need to write code to validate the token & expiration time

		if(!checkLinkExpiration()) {
			response.getWriter().println("Link has been expired!");
			return;
		}
		if (isValidToken(token)) {
			// Forward to password reset form with token
			request.setAttribute("token", token);
			RequestDispatcher dispatcher = request.getRequestDispatcher("reset_password.jsp");
			dispatcher.forward(request, response);
		} else {
			// Invalid token, display an error message
			response.getWriter().println("Invalid or expired token");
		}
	}

	private static boolean isValidToken(String token) {
		// Check if the token exists in the database and is still valid
		// You may need to set an expiration time for the token

		boolean flag = false;
		List<String> list = EmailDao.getEmailAndTokenToValidateUser(token);
		String dbToken = list.get(0);
		System.out.println("db token =" + dbToken);
		if (dbToken.equals(token)) {
			flag = true;
		}
		return flag; // Example implementation
	}

	public boolean checkLinkExpiration() {
		
		List<String> list = EmailDao.getEmailAndTokenToValidateUser(token);

		token = list.get(0);
		String strTime = list.get(1);
		System.out.println("strTime " + strTime);
		String newStr = strTime.substring(0, strTime.length() - 2);
		System.out.println("newStr===" + newStr);
		LocalDateTime dateTime = LocalDateTime.parse(newStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		boolean linkActive = EmailDao.isLinkActive(dateTime);
		
		return linkActive;
	}

}
