package com.lightfight.game.uc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReadGreenLightTest2 implements Runnable {

	private LightType lightType = LightType.DEFAULT;// 线程编号,Thread Number

	private ReentrantLock lock = new ReentrantLock();

	private Condition redCon = lock.newCondition();
	private Condition greenCon = lock.newCondition();

	public static void main(String[] args) {
		new ReadGreenLightTest2().run();
	}

	@Override
	public void run() {
		new Thread(new RedThread(), "red").start();
		new Thread(new GreenThread(), "green").start();
	}

	class RedThread implements Runnable {

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			while (true) {
				try {

					lock.lock();
					System.out.println(name);

					if (lightType.equals(LightType.GREEN)) {
						redCon.await();
					}

					System.out.println(name + " is flashing...");
					lightType = LightType.GREEN; // 红灯亮了之后就该绿灯了,所以将灯光的类型设置为绿灯

					TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看

					greenCon.signal();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
					System.out.println(name + " nulock");
				}
			}
		}
	}

	class GreenThread implements Runnable {

		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			while (true) {
				try {

					lock.lock();
					System.out.println(name);

					if (lightType.equals(LightType.RED)) {
						greenCon.await();
					}

					System.out.println(name + " is flashing...");
					lightType = LightType.RED; // 绿灯亮了之后就该红灯了,所以将灯光的类型设置为红灯

					TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看

					redCon.signal();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
					System.out.println(name + " nulock");
				}
			}
		}
	}

	enum LightType {

		DEFAULT(0), RED(1), GREEN(2);

		public final int type;

		private LightType(int type) {
			this.type = type;
		}
	}

}
