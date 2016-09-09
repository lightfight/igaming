package com.lightfight.game.lang;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	static int count = 0;

//	@Test
//	public void main() {
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println(count++);
//			}
//		}, 1000);
//	}
	
	public static void main(String[] args) {

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(count++);
			}
		}, 1000, 1000);
	
	}
}
