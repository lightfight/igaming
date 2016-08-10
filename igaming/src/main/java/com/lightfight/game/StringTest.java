package com.lightfight.game;

import org.junit.Test;

public class StringTest {
	
	/**
	 * 
	 */
	@Test
	public void testToken(){
//		String exToken = "abc";
		String exToken = "abc-1001"; // 扩展的token
		String[] splitedExToken = exToken.split("-");
		
		System.out.println(splitedExToken.length);
		for (String item : splitedExToken) {
			System.out.println(item);
		}
	}

}
