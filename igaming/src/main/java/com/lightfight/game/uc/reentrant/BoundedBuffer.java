package com.lightfight.game.uc.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JDK
 * @see java.util.concurrent.locks.Condition
 * @author deliang
 *
 */
public class BoundedBuffer {

	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Integer[] items = new Integer[5];
	
	int putptr, takeptr, count;

	public void put(Integer x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				notFull.await(); // 没有满等待,就是满了
			}
			items[putptr] = x;
			if (++putptr == items.length) {
				putptr = 0;
			}
			++count;
			notEmpty.signal();
			
			System.out.println(Thread.currentThread().getName() + " put "+ x);
		} finally {
			lock.unlock();
		}
	}

	public Integer take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0){
				notEmpty.await(); // 没有空等待,就是空了
			}
			Integer x = items[takeptr];
			if (++takeptr == items.length){
				takeptr = 0;
			}
			--count;
			notFull.signal();
			 // 打印取出的数据
			System.out.println(Thread.currentThread().getName() + " take "+ x);
			return x;
		} finally {
			lock.unlock();
		}
	}

}
