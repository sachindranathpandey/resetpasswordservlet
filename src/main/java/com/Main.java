package com;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.plaf.multi.MultiSliderUI;

public class Main {

	public static void main(String[] args) {
		
		//EmailUtility.sendPasswordResetEmail("sachinji.pandey@gmail.com", "232323");
		
//		Connection connection = ConnectionProvider.getConnection();
//		System.out.println(connection);
//	
		List<String> list = EmailDao.getEmailAndTokenToValidateUser("1919a587-b203-41ce-8155-8fb41c014c5f");
		
		String token = list.get(0);
		String strTime = list.get(1);    
		System.out.println("strTime "+strTime);
		String newStr=strTime.substring(0, strTime.length()-2);
		System.out.println("newStr==="+newStr);
        LocalDateTime dateTime = LocalDateTime.parse(newStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		boolean linkActive = EmailDao.isLinkActive(dateTime);
		System.out.println(linkActive);
//		System.out.println(linkActive);
		//EmailDao.updateUserPassword("74b782a7-7cb7-48fa-a3d6-ea952b063a9d", "sachindra N");
	}
}
