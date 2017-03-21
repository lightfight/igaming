package com.lightfight.game.lang;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;

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

	@Test
	public void testIntArrToString(){
		int[] arr = {1,2,3,};
		System.out.println(arr.toString()); //
		System.out.println(Arrays.toString(arr));
		/**
		 * output<BR/>
		 * [I@5ec60ee2
		 * [1, 2, 3]
		 */
	}

}
