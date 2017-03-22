package com.lightfight.game.uc.exchange.v4;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author lightfight
 *
 */
public class ExchangeTest {

	public static void main(String[] args) {

		int capacity = 5;
		final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(capacity);
		final AtomicInteger num = new AtomicInteger(0);

		Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

		// 执行put
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(1); // put前需要的处理时间
						int e = num.incrementAndGet();
						System.out.println("put....." + e);
						queue.put(e);
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
						Integer e = queue.take();
						System.out.println("take............" + e);
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
	put.....1
	take............1
	put.....2
	put.....3
	put.....4
	take............2
	put.....5
	put.....6
	put.....7
	take............3
	put.....8
	put.....9
	take............4
	put.....10
	take............5
	put.....11
	take............6
	put.....12
	take............7
	put.....13
	 * </code>
	 */

}
