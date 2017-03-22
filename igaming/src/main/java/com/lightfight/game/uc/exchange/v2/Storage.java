package com.lightfight.game.uc.exchange.v2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage<E> {

	final Lock lock = new ReentrantLock(); // 一个锁创建了两个条件
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	/**
	 * 当[LEN = 1]时生产者放一个东西进去,然后消费者拿一个东西,从而实现两者的交替执行<BR>
	 * 当[LEN > 1]时根据
	 */
	final static int LEN = 5;
	int count = 0;

	/**
	 * Head of linked list. Invariant: head.item == null
	 */
	private transient Node<E> head;

	/**
	 * Tail of linked list. Invariant: last.next == null
	 */
	private transient Node<E> last;

	public Storage() {
		last = head = new Node<E>(null);
	}

	static class Node<E> {
		E item;
		Node<E> next;

		Node(E x) {
			item = x;
		}
	}

	private void enqueue(Node<E> node) {
		last = last.next = node;
	}

	private E dequeue() {
		Node<E> h = head;
		Node<E> first = h.next;
		h.next = h; // help GC
		head = first;
		E x = first.item;
		first.item = null;
		return x;
	}

	public void put(E e) throws InterruptedException {
		Node<E> node = new Node<>(e);
		lock.lock();
		try {
			while (count == LEN) {
				notFull.await(); // 阻塞生产者
			}
			enqueue(node);
			
			count++;
			System.out.println("put....count = " + count + ", value = " + node.item);
			
			notEmpty.signal(); // 唤醒消费者
		} finally {
			lock.unlock();
		}
	}

	public E take() throws InterruptedException {
		E x;
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await(); // 阻塞消费者
			}
			x = dequeue();
			count--;
			System.out.println("take...count = " + count + ", value = " + x);
			notFull.signal(); // 唤醒生产者
		} finally {
			lock.unlock();
		}
		
		return x;
	}
	

}
