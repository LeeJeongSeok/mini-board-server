package com.jeongseok.miniboardserver.domain.post.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class SimpleEncrypt {

	public boolean isMatch(String password, String encryptedPassword) {
		String encryptPassword = encryptPassword(password);
		return encryptPassword.equals(encryptedPassword);
	}


	public String encryptPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
			for (byte b : encodedHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("암호화 알고리즘을 찾을 수 없습니다.", e);
		}
	}

}
