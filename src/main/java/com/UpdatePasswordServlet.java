package com;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String token=request.getParameter("token");
      
        System.out.println("token from url" + token);
        // check token & validity of reset link for 2 minutes
       
        	
       
        
        if (password.equals(confirmPassword)) {
            // Update password in the database for the user associated with the token
            updatePasswordForTokenUser(token, password);
            // Redirect to login page or inform the user that the password has been reset
            response.sendRedirect("index.jsp");
        } else {
            // Passwords don't match, display an error message
            response.getWriter().println("Passwords don't match");
        }
    }
    
    private void updatePasswordForTokenUser(String token, String newPassword) {
        // Update the password in the database for the user associated with the token
    	
    	EmailDao.updateUserPassword(token, newPassword);
    }
}

