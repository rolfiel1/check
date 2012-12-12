package com.check.util; 

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @version 1.0 
 * 
 * @author 作者姓名 
 * 
 * 创建时间：2012-5-9 上午08:07:30 
 * 
 * 类说明 MD5加密
 */
public class MD5Util {
	public static String toMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;

				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
}
 
