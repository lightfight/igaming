package com.lightfight.game.uc.reentrant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * <code>
 * 
 * 公平[fair = true]
 * 任务调度保证每个线程等待的时间较均衡,但是线程多了后会非常慢
 * 
 * start... 0,1,2,5,6,7,8,9,4,10,3,11,13,12,15,16,17,18,14,19,
 * 
 * start... 0,1,2,4,5,7,6,8,9,3,13,12,14,10,11,15,16,17,18,19,
 * 
 * start... 0,1,2,3,4,6,8,7,9,5,11,10,12,14,15,16,17,13,19,18,
 * 
 * start... 0,1,2,3,4,5,7,6,9,8,10,11,13,12,14,15,16,18,17,19,
 * 
 * start... 0,1,2,3,4,5,6,7,8,9,10,12,13,11,14,15,17,16,18,19,
 * 
 * start... 0,1,2,4,3,5,6,8,9,13,10,12,14,7,11,17,16,19,15,18,
 * 
 * 
 * 不公平[fair = false]
 * 
 * start... 0,1,4,5,2,19,3,6,7,9,8,13,12,14,15,10,16,17,11,18,
 * 
 * start... 0,1,2,4,5,3,7,6,8,9,10,11,13,12,16,15,17,14,18,19,
 * 
 * start... 0,1,4,5,3,6,2,7,8,9,10,11,12,13,14,17,15,16,18,19,
 * 
 * start... 0,2,1,19,4,5,6,3,7,8,9,10,12,11,13,14,15,16,17,18,
 * 
 * </code>
 * 
 * @author deliang
 *
 */
public class FairVsUnfair {

	static boolean fair = false; // true

	final static Lock lock = new ReentrantLock(fair); //

	final CountDownLatch latch = new CountDownLatch(1); // 控制所有的线程创建完成获得lock后才进行开始

	public static void main(String[] args) throws InterruptedException {
		FairVsUnfair t = new FairVsUnfair();
		t.start();
	}

	void start() throws InterruptedException {
		for (int i = 0; i < 20; i++) {
			new Thread(new Worker(i)).start();
		}
		System.out.println("start...");
		latch.countDown();
	}

	class Worker implements Runnable {

		int num;

		public Worker(int num) {
			this.num = num;
		}

		@Override
		public void run() {
			lock.lock();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {

				System.out.print(num + ",");
			} finally {
				lock.unlock();
			}

		}
	}

}
