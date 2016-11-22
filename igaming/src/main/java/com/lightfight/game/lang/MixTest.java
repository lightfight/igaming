package com.lightfight.game.lang;

import java.util.Calendar;

import org.junit.Test;

public class MixTest {
	
	
	/**
	 * 获取一个整型的日期
	 * @return
	 */
	@Test
	public void getIntDate(){
		
		int addDays = -1;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		System.out.println(year + "-" + month + "-" + day);
		
		int intDate = year * 10000 + month * 100 + day;
		
		System.out.println(intDate);
	}

}
