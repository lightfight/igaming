package com.lightfight.game.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assume;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringTest {

	/**
	 * 
	 */
	@Test
	public void testToken() {
		// String exToken = "abc";
		String exToken = "abc-1001"; // 扩展的token
		String[] splitedExToken = exToken.split("-");

//		System.out.println(splitedExToken.length);
//		for (String item : splitedExToken) {
//			System.out.println(item);
//		}
		
		Assume.assumeTrue(splitedExToken[0].equals("abc"));
		Assume.assumeTrue(splitedExToken[1].equals("1001"));
		Assume.assumeFalse(splitedExToken[1].equals("1001"));
		
		assertTrue("GOOD",false);
	}

	@Test
	public void subString() {
		String logfileName = "loginfo-2016-08-08.log";
		System.out.println(logfileName.substring(logfileName.indexOf("-") + 1, logfileName.indexOf(".")));
	}

	@Test
	public void testCombine() {
		
		Map<String, String> data = new HashMap<>();
		data.put("sign", "abc");
		data.put("appid", "google");
		
		StringBuilder builder = new StringBuilder();

		int index = 0;
		int maxIndex = data.size() - 1;
		for (Entry<String, String> item : data.entrySet()) {
			builder.append(item.getKey()).append("=").append(item.getValue());
			if (index++ < maxIndex) {
				builder.append("&");
			}
		}
		
		System.out.println(builder.toString());
	}

}
