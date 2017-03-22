package com.lightfight.game.lang;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;

public class MixTest {

	@Test
	public void testBitCalu(){
		int src = 2;
		System.out.println(~src); // ~取反运算,这个涉及到[整数--转(绝对值取反加1)-->二进制]和[二进制--转(减1取反)-->整数]

		/**
		 * output:
		 * -3,2
		 * 2,-3
		 *
		 */
	}
	
	
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
