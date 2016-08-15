package com.lightfight.game.uc.reentrant;

import java.util.concurrent.TimeUnit;

public class PutThread implements Runnable {
	
	private int num;
	private BoundedBuffer bb;

	public PutThread(BoundedBuffer bb, int num) {
		this.num = num;
		this.bb = bb;
	}

	public void run() {
		try {
			TimeUnit.SECONDS.sleep(1); // 线程休眠1s
			bb.put(num); // 向BoundedBuffer中写入数据
		} catch (InterruptedException e) {
		}
	}
}
