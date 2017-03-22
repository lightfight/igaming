package com.lightfight.game.uc.exchange.v0;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 
 * 两个线程交替执行
 * 
 * @author lightfight
 *
 */
public class ExchangeTest {

	public static void main(String[] args) {

		final Storage storage = new Storage();
		Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

		// 执行put
		executor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						storage.put();
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
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * <code>
	put....count = 1
	put....count = 2
	put....count = 3
	put....count = 4
	put....count = 5
	take...count = 4
	take...count = 3
	take...count = 2
	take...count = 1
	take...count = 0
	put....count = 1
	put....count = 2
	put....count = 3
	put....count = 4
	
	 * </code>
	 * 
	 */
}
