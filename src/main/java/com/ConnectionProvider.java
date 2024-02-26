package com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String URL = "jdbc:mysql://localhost:3306/managepassworddb";
			String USER = "root";
			String PASS = "root";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			if(conn!=null) {
				System.out.println("Connected");
				return conn;
			}else {
				System.out.println("Something went wrong...");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}

}
