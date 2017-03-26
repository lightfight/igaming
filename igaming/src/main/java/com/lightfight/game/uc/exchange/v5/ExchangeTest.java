package com.lightfight.game.uc.exchange.v5;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author lightfight
 *
 */
public class ExchangeTest {

	public static void main(String[] args) {

		int capacity = 5;
		final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(capacity);
		final AtomicInteger num = new AtomicInteger(0);

		/**
		 * 核心数
		 */
		int coreCount = Runtime.getRuntime().availableProcessors() + 1;

		/**
		 * 生产者
		 */
		int consumerWorkerCount = coreCount; // 生产者线程数
		Executor consumerExecutor = Executors.newFixedThreadPool(coreCount); // 生产者线程池设置为核心数

		// 执行put
		for (int i = 0; i < consumerWorkerCount; i ++) {
			consumerExecutor.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							TimeUnit.SECONDS.sleep(1); // put前需要的处理时间
							int e = num.incrementAndGet();
							System.out.println(Thread.currentThread().getName() + " put....." + e);
							queue.put(e);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

		/**
		 * 消费者
		 */
		int producerWorkerCount = coreCount; // 消费者线程数
		Executor producerExecutor = Executors.newFixedThreadPool(coreCount); // 消费者线程池设置为核心数

		// 执行take
		for (int i = 0; i < producerWorkerCount; i++) {
			producerExecutor.execute(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Integer e = queue.take();
							System.out.println(Thread.currentThread().getName() + " take............" + e);
							TimeUnit.SECONDS.sleep(3); // take后需要的处理时间
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

	}

	/**
	 * 输出结果： <BR>
	 * <code>
	 pool-1-thread-3 put.....1
	 pool-2-thread-1 take............1
	 pool-1-thread-4 put.....2
	 pool-2-thread-3 take............2
	 pool-1-thread-2 put.....3
	 pool-2-thread-2 take............3
	 pool-1-thread-1 put.....4
	 pool-2-thread-5 take............4
	 pool-1-thread-5 put.....5
	 pool-2-thread-4 take............5
	 pool-1-thread-3 put.....6
	 pool-1-thread-4 put.....7
	 pool-1-thread-2 put.....8
	 pool-1-thread-1 put.....9
	 pool-1-thread-5 put.....10
	 pool-1-thread-3 put.....11
	 pool-1-thread-4 put.....12
	 pool-1-thread-2 put.....13
	 pool-1-thread-1 put.....14
	 pool-1-thread-5 put.....15
	 pool-2-thread-1 take............6
	 pool-2-thread-3 take............7
	 pool-2-thread-2 take............8
	 pool-2-thread-5 take............9
	 pool-2-thread-4 take............10
	 pool-1-thread-3 put.....16
	 pool-1-thread-4 put.....17
	 pool-1-thread-2 put.....18
	 pool-1-thread-1 put.....19
	 pool-1-thread-5 put.....20
	 pool-2-thread-1 take............11
	 pool-2-thread-3 take............12
	 pool-2-thread-2 take............13
	 pool-2-thread-5 take............14
	 pool-2-thread-4 take............15
	 pool-1-thread-3 put.....21
	 pool-1-thread-4 put.....22
	 pool-1-thread-2 put.....23
	 pool-1-thread-1 put.....24
	 pool-1-thread-5 put.....25
	 pool-2-thread-3 take............16
	 pool-2-thread-1 take............17
	 pool-2-thread-2 take............18
	 pool-2-thread-5 take............19
	 pool-2-thread-4 take............20
	 * </code>
	 */

}
