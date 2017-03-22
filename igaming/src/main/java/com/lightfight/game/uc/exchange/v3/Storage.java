package com.lightfight.game.uc.exchange.v3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @see java.util.concurrent.LinkedBlockingQueue
 * 
 * @author lightfight
 *
 * @param <E>
 */
public class Storage<E> {

	/** Lock held by take, poll, etc */
	private final ReentrantLock takeLock = new ReentrantLock();

	/** Wait queue for waiting takes */
	private final Condition notEmpty = takeLock.newCondition();

	/** Lock held by put, offer, etc */
	private final ReentrantLock putLock = new ReentrantLock();

	/** Wait queue for waiting puts */
	private final Condition notFull = putLock.newCondition();

	static int capacity = 5;
	AtomicInteger count = new AtomicInteger();

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
		if (e == null)
			throw new NullPointerException();
		// Note: convention in all put/take/etc is to preset local var
		// holding count negative to indicate failure unless set.
		int c = -1;
		Node<E> node = new Node<>(e);
		final ReentrantLock putLock = this.putLock;
		final AtomicInteger count = this.count;
		putLock.lockInterruptibly();
		try {
			/*
			 * Note that count is used in wait guard even though it is not
			 * protected by lock. This works because count can only decrease at
			 * this point (all other puts are shut out by lock), and we (or some
			 * other waiting put) are signalled if it ever changes from
			 * capacity. Similarly for all other uses of count in other wait
			 * guards.
			 */
			while (count.get() == capacity) { // put检查put,如果已经full就await
				notFull.await();
			}
			enqueue(node);
			c = count.getAndIncrement();
			System.out.println("put....count = " + c + ", value = " + node.item);
			if (c + 1 < capacity) {
				notFull.signal();
			}
		} finally {
			putLock.unlock();
		}
		if (c == 0) { // put检查take,如果已经empty空了就signal唤醒,因为take可能不停的take让自己await了
			signalNotEmpty();
		}
	}

	public E take() throws InterruptedException {
		E x;
		int c = -1;
		final AtomicInteger count = this.count;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) { // take检查take,如果已经empty就await
				notEmpty.await();
			}
			x = dequeue();
			c = count.getAndDecrement();
			System.out.println("-------------------take...count = " + c + ", value = " + x);
			if (c > 1){
				notEmpty.signal();
			}
			
		} finally {
			takeLock.unlock();
		}
		if (c == capacity){ // take检查put,如果已经满了就唤醒,因为put可能不停的put让自己await了
			signalNotFull();
		}
		return x;
	}

	/**
	 * Signals a waiting take. Called only from put/offer (which do not
	 * otherwise ordinarily lock takeLock.)
	 */
	private void signalNotEmpty() {
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			notEmpty.signal();
		} finally {
			takeLock.unlock();
		}
	}

	/**
	 * Signals a waiting put. Called only from take/poll.
	 */
	private void signalNotFull() {
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			notFull.signal();
		} finally {
			putLock.unlock();
		}
	}

}
