package com.zxf.test;

import org.junit.Test;

import com.check.util.Getpic;
import com.check.util.MD5Util;

public class TestMD5 {

	@Test
	public void testCheckImg() {
		System.out.println(MD5Util.toMD5("admin"));
	}
}
