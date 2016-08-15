package com.lightfight.game.uc.reentrant;

import java.util.concurrent.TimeUnit;

public class TakeThread implements Runnable {

	private BoundedBuffer bb;

	public TakeThread(BoundedBuffer bb) {
		this.bb = bb;
	}

	public void run() {
		try {
			TimeUnit.SECONDS.sleep(10); // 线程休眠1s
			bb.take(); // 向BoundedBuffer中读取数据
		} catch (InterruptedException e) {
		}
	}
}
