package com.lightfight.game.uc.exchange.v3;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 两个线程交替执行
 * 
 * @author lightfight
 *
 */
public class ExchangeTest {

	public static void main(String[] args) {

		final Storage<Integer> storage = new Storage<>();
		final AtomicInteger num = new AtomicInteger(0);

		Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

		// 执行put
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(1); // put前需要的处理时间
						storage.put(num.incrementAndGet());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// 执行take
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						storage.take();
						TimeUnit.SECONDS.sleep(3); // take后需要的处理时间
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 输出结果： <BR>
	 * <code>
	put....count = 1, value = 1
	take...count = 0, value = 1
	put....count = 1, value = 2
	put....count = 2, value = 3
	take...count = 1, value = 2
	put....count = 2, value = 4
	put....count = 3, value = 5
	put....count = 4, value = 6
	take...count = 3, value = 3
	put....count = 4, value = 7
	put....count = 5, value = 8
	take...count = 4, value = 4
	put....count = 5, value = 9
	take...count = 4, value = 5
	put....count = 5, value = 10
	take...count = 4, value = 6
	put....count = 5, value = 11
	take...count = 4, value = 7
	put....count = 5, value = 12
	 * </code>
	 */

}
