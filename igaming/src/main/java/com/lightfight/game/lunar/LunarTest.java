package com.lightfight.game.lunar;

import org.junit.Test;

public class LunarTest {

	@Test
	public void toLunar() {

		Gregorian gregorian = new Gregorian(2017, 1, 6); // 阳历日期
		Lunar lunar = LunaCalendar.toLunar(gregorian); // 阳历转农历
		System.out.println(lunar); // 农历结果
	}

}
