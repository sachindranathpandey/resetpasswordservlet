package com;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        
        // Generate unique token and associate with user's account
        String token = generateUniqueToken();
        
        System.out.println(token);
        // Store the token in the database with user's email
        boolean isSaved = EmailDao.insertEmailWithTokenInDb(email, token);
        if (isSaved) {
			System.out.println("Inserted Successfully");
		}
        // Send password reset email with link containing the token
        
        boolean isEmailSent = EmailUtility.sendPasswordResetEmail(email, token);
        
        if (isEmailSent) {
        	 //sendPasswordResetEmail(email, token);
            
            // Forward to a page informing the user to check their email
        	 RequestDispatcher dispatcher = request.getRequestDispatcher("password_reset_info.jsp");
             dispatcher.forward(request, response);
		}
       
       
    }
    
    private String generateUniqueToken() {
        // Generate a unique token (e.g., UUID)S
        return UUID.randomUUID().toString();
    }
}
