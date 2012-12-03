package com.zxf.test;

import org.junit.Test;

import com.check.util.Getpic;

public class TestGetpic {

	@Test
	public void testCheckImg() {
		String flag=Getpic.saveUrlAs("https://www.vjia.com/login/process/VerifyCode.ashx?t=0.5030940684072299", "temp");
		System.out.println(flag);
	}
}
