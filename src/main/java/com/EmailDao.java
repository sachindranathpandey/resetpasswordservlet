package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {

	public static boolean insertEmailWithTokenInDb(String email, String token) {
		boolean isSaved = false;

		Connection con = ConnectionProvider.getConnection();
		Timestamp expiration_datetime = Timestamp.valueOf(LocalDateTime.now().plus(1, ChronoUnit.MINUTES));

		// String query = "insert into passwordreset(email,token,expiration_datetime)
		// values(?,?,?)";
		// String query="INSERT INTO passwordreset (email, token,
		// expiration_datetime)VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE token =
		// VALUES(token), expiration_datetime = VALUES(expiration_datetime)";

		try {
			String query = "SELECT COUNT(*) FROM passwordreset WHERE email = ?";
			PreparedStatement checkStmt = con.prepareStatement(query);
			checkStmt.setString(1, email);
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			if (count == 0) {
				String query_ins = "INSERT INTO passwordreset(email, token, expiration_datetime) VALUES (?, ?, ?)";
				PreparedStatement insertStmt = con.prepareStatement(query_ins);
				insertStmt.setString(1, email);
				insertStmt.setString(2, token);
				insertStmt.setTimestamp(3, expiration_datetime);
				insertStmt.executeUpdate();
				System.out.println("New row inserted for email: " + email);

			} else {
				// Email exists, perform UPDATE
				PreparedStatement updateStmt = con.prepareStatement(
						"UPDATE passwordreset SET token = ?, expiration_datetime = ? WHERE email = ?");
				updateStmt.setString(1, token);
				updateStmt.setTimestamp(2, expiration_datetime);
				updateStmt.setString(3, email);
				updateStmt.executeUpdate();
				System.out.println("Updated token and date for email: " + email);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return isSaved;
	}

	public static List<String> getEmailAndTokenToValidateUser(String  token) {
		Connection con = ConnectionProvider.getConnection();
		List<String> list = new ArrayList<>();

		try {

			String query = "SELECT token, expiration_datetime FROM passwordreset WHERE token = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, token);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				String user_token = rs.getString("token");
				Timestamp timestamp = rs.getTimestamp("expiration_datetime");
				System.out.println("Token: " + user_token + ", timestamp: " + timestamp);
				list.add(user_token);
				list.add(String.valueOf(timestamp));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	public static boolean isLinkActive(LocalDateTime linkGenerationTime) {

		LocalDateTime currentTime = LocalDateTime.now();
		// Calculate the difference between the current time and link generation time
		long minutesDifference = ChronoUnit.MINUTES.between(linkGenerationTime, currentTime);
		System.out.println("diff= " + minutesDifference);
		return minutesDifference <= 2;

	}

	public static boolean updateUserPassword(String token, String newPassword) {
		boolean flag = false;
		Connection con = ConnectionProvider.getConnection();
		try {

			String query = "update passwordreset set password=? where token=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, token);
			int executeUpdate = pstmt.executeUpdate();
			System.out.println("Number of rows affected "+executeUpdate );
			

			// Check if any rows were updated
			if (executeUpdate > 0) {
				flag = true;
				System.out.println("Password updated successfully for email: " + token);
			} else {
				System.out.println("No password updated for token: " + token);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

}
