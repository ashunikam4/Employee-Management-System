package com.ems.webapp.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUtil {
	public static String encryptMD5(String pwd) {
		/*
		 * Given password, encrypts it using MD5 hashing
		 */
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes());
			byte[] bytes = md.digest();
			
			// convert the 128 bit hash to hex string
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++)
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
