package com.lightfight.game.uc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 10个线程来计算1到1000,最后将计算结果合并 <code>
 * 
 * 开始计算...
	cal start = 1
	cal start = 201
	cal start = 101
	cal start = 301
	cal start = 501
	cal start = 601
	cal start = 701
	cal start = 401
	cal start = 801
	cal start = 901
	计算结束
	total = 500500
 * 
 * </code>
 * 
 * @author deliang
 *
 */
public class CountDownLatchTest {

	int tcount = 10; // ThreadCount 线程数量
	CountDownLatch start = new CountDownLatch(1); // 控制开始,数量为1
	CountDownLatch end = new CountDownLatch(tcount); // 控制结束,数量为N

	AtomicInteger total = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		CountDownLatchTest t = new CountDownLatchTest();
		t.cal();
	}

	private void cal() throws InterruptedException {
		for (int i = 0; i < tcount; i++) {
			new Thread(new Worker(i * 100 + 1)).start();
		}

		System.out.println("开始计算...");
		start.countDown();

		end.await(); // 等待全部线程运行结束

		System.out.println("计算结束");
		System.out.println("total = " + total.get());
	}

	class Worker implements Runnable {

		int from;

		public Worker(int from) {
			this.from = from;
		}

		@Override
		public void run() {
			try {
				start.await(); // 等待计算开始命令
				System.out.println("cal start = " + from);
				int sum = 0;
				for (int i = from; i < from + 100; i++) {
					sum += i;
				}
				total.addAndGet(sum);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				end.countDown();
			}
		}

	}

}
