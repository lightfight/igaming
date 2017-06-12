package com.lightfight.game.mix;

/**
 * 描述: </BR>
 * Java 程序死锁问题原理及解决方案
 * https://www.ibm.com/developerworks/cn/java/j-lo-deadlock/
 *
 * <p>
 * Created by caidl on 2017/5/24/0024
 */
class ThreadDeadlock {

    public static void main(String[] args) throws InterruptedException {
        String car = "A";
        String desk = "B";
        String bike = "C";

        Thread t1 = new Thread(new SyncThread(car, desk), "t1");
        Thread t2 = new Thread(new SyncThread(desk, bike), "t2");
        Thread t3 = new Thread(new SyncThread(bike, car), "t3");

        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();

    }

    private static class SyncThread implements Runnable {
        private String obj1;
        private String obj2;

        public SyncThread(String o1, String o2) {
            this.obj1 = o1;
            this.obj2 = o2;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " 1acquiring lock on " + obj1);
            synchronized (obj1) {
                System.out.println(name + " 2acquired lock on " + obj1);
                work(); // 让每个线程都已经锁住自己想要锁住的对象
                System.out.println(name + " 3acquiring lock on " + obj2);
                synchronized (obj2) {
                    System.out.println(name + " 4acquired lock on " + obj2);
                    work();
                }
                System.out.println(name + " 1released lock on " + obj2);
            }
            System.out.println(name + " 2released lock on " + obj1);
            System.out.println(name + " finished execution.");
        }

        private void work() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


