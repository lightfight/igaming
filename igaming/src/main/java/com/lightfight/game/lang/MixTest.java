package com.lightfight.game.lang;

import com.lightfight.game.lang.vo.TipExp;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;

public class MixTest {

	@Test
	public void testException(){
		try {
			int rewards = getTaskRewards(false);
			System.out.println(rewards);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 尽管方法上抛出的是NullPointerException但是</BR>
	 * @param isFinish
	 * @return
	 * @author caidl
	 * @date 2017/4/9/0009 19:47
	 */
	private int getTaskRewards(boolean isFinish) throws TipExp {

		if (isFinish) {

		} else {
			throw new NullPointerException();
		}

		return 0;
	}

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
