package com.lightfight.game.uc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReadGreenLightTest implements Runnable {

    private int tnum = 1;// 线程编号,Thread Number

    private ReentrantLock lock = new ReentrantLock();

    private Condition redCon = lock.newCondition();
    private Condition greenCon = lock.newCondition();

    public static void main(String[] args) {
        new ReadGreenLightTest().run();
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
        	System.out.println(name);
            while (true) {
                try {
                	System.out.println(name + " pre lock");
                    lock.lock();
                    System.out.println(name + " locked");
                    while (tnum != 1) {// 判断是否该自己执行了[采用while不是if是为了防止死锁]
                        System.out.println(name + " pre await");
                        redCon.await();
                        System.out.println(name + " awaited");
                    }
                    System.out.println(name + " is flashing...");

                    TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看

                    tnum = 2;
                    greenCon.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(name + " unlocked");
                }
            }
        }
    }

    class GreenThread implements Runnable {
    	
    	@Override
    	public void run() {
        	String name = Thread.currentThread().getName();
        	System.out.println(name);
            while (true) {
                try {
                	System.out.println(name + " pre lock");
                    lock.lock();
                    System.out.println(name + " locked");
                    while (tnum != 2) {// 判断是否该自己执行了[采用while不是if是为了防止死锁]
                        System.out.println(name + " pre await");
                        greenCon.await();
                        System.out.println(name + " awaited");
                    }
                    System.out.println(name + " is flashing...");

                    TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看

                    tnum = 1;
                    redCon.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(name + " unlocked");
                }
            }
        }
    }

}
