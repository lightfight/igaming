package com.lightfight.game.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class UtilTest {
	
	@Test
	public void testStringCompareTo(){
		String preVersion = "1.1.2.3";
		String currVersion = "1.10";
		
		System.out.println(preVersion.compareTo(currVersion));
	}
	
	@Test
	public void testTransport(){
		
		Map<Integer, Object> map = new HashMap<>();
		
		String value = (String)map.get("key");
		
		System.out.println("value = " + value);
	}
	
	@Test
	public void testDateLong() throws ParseException, InterruptedException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = format.parse("2016-10-02 23:59:59");
		System.out.println(date.getTime());
		
		SimpleDateFormat date2StrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = date2StrFormat.format(new Date(System.currentTimeMillis()));
		System.out.println(str);
	}

}
