package com;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtility {

    public static boolean sendPasswordResetEmail(String recipientEmail, String token) {
        // Email configuration
    	boolean isEmailSent=false;
        String host = "smtp.gmail.com"; // Your SMTP host
        String port = "587"; // Your SMTP port
        String username = "devappsachindra@gmail.com"; // Your email address
        String password = "dmua fkgh lcfn dtkq"; // Your email password
        
        // To Generate App password, use below link 
        //https://myaccount.google.com/apppasswords?utm_source=google-account&utm_medium=myaccountsecurity&utm_campaign=tsv-settings&rapt=AEjHL4NgQgJpPvRWKcCRvIeYl4Xww93m7nC-MvCwYbP9-a2XVK0nmHkmtLx8WFLAh-wckRx8JnEH-fEDRHBXUlazCp_3LebayzFjTo0233GwdynRs1YlqjM

        // Sender's email address
       // String from = "sachindrapandey200@gmail.com";

        // Email subject and content
        String subject = "Password Reset";
        String content = "Dear User,\n\n"
                        + "To reset your password, please click on the following link:\n\n"
                        + "http://localhost:8080/ResetPassword/resetPassword?token=" + token + "\n\n"
                        + "If you did not request a password reset, please ignore this email.\n\n"
                        + "Best regards,\n"
                        + "Your Website Team";

        // Set properties for SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
           // message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(content);

            // Send message
            Transport.send(message);
            isEmailSent=true;
           System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		return isEmailSent;
    }
    
}
