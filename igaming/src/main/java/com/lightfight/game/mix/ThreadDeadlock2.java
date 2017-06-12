package com.lightfight.game.mix;

/**
 * 描述: </BR>
 * Java 程序死锁问题原理及解决方案
 * https://www.ibm.com/developerworks/cn/java/j-lo-deadlock/
 *
 * <p>
 * Created by caidl on 2017/5/24/0024
 */
class ThreadDeadlock2 {

    public static void main(String[] args) throws InterruptedException {
        String A = "A";
        String B = "B";

        // 1,构建两个线程t1,t2
        // 2,t1锁住A,再去获取B
        // 3,t2锁住B,再去获取A
        // 4,对于[2,3]只需要公用一个线程,传入两个参数,t1为AB,t2为BA

        Thread t1 = new Thread(new SyncThread(A, B), "t1");
        Thread t2 = new Thread(new SyncThread(B, A), "t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();

    }

    private static class SyncThread implements Runnable {
        private String A;
        private String B;

        public SyncThread(String A, String B) {
            this.A = A;
            this.B = B;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " 1想要获取 " + A);
            synchronized (A) {
                System.out.println(name + " 1已经获取 " + A);
                work(); // 让每个线程都已经锁住自己想要锁住的对象
                System.out.println(name + " 2想要获取 " + B);
                synchronized (B) {
                    System.out.println(name + " 2已经获取 " + B);
                    work();
                }
                System.out.println(name + " 1释放 " + B);
            }
            System.out.println(name + " 2释放 " + A);
            System.out.println(name + " 执行结束");
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

