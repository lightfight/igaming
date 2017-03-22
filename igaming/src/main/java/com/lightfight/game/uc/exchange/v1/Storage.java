package com.lightfight.game.uc.exchange.v1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {

	final Lock lock = new ReentrantLock(); // 一个锁创建了两个条件
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	/**
	 * 当[LEN = 1]时生产者放一个东西进去,然后消费者拿一个东西,从而实现两者的交替执行<BR>
	 * 当[LEN > 1]时根据
	 */
	final static int LEN = 5;
	int count = 0;


	public void put() throws InterruptedException {
		lock.lock();
		try {
			while (count == LEN) {
				notFull.await(); // 阻塞生产者
			}
			count++;
			System.out.println("put....count = " + count);
			
			notEmpty.signal(); // 唤醒消费者
		} finally {
			lock.unlock();
		}
	}

	public void take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await(); // 阻塞消费者
			}
			count--;
			System.out.println("take...count = " + count);
			notFull.signal(); // 唤醒生产者
		} finally {
			lock.unlock();
		}
	}
	

}
