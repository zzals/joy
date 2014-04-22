package com.se.joy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	
	public static String encrypt(String passwd){
		//스트링을 바이트로
		byte[] byteArray = passwd.getBytes();
		
		MessageDigest md = null;
		try{
			//암호화 방법선택
			md = MessageDigest.getInstance("SHA1");			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		if(md == null){
			return null;
		}
		
		md.reset();
		md.update(byteArray);
		//암호화
		byte digest[] = md.digest();
		
		StringBuffer buffer = new StringBuffer();
		//헥사 값으로 저장
		for(int i = 0; i < digest.length; i++){
			buffer.append(Integer.toHexString(0xFF & digest[i]));
		}
		return buffer.toString();
	}
}	
