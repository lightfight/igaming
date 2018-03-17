package com.game.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 读写锁的互斥性测试

 <pre>

 [java并发编程系列之ReadWriteLock读写锁的使用](http://blog.csdn.net/liuchuanhong1/article/details/53539341)

 ## 结论

 1. 读与读不互斥
 2. 读与写互斥
 3. 写与写互斥

 ## 测试1：读与读不互斥

 ### 结论

 日志输出表现：就是读线程1在读的时候读线程2也在读，两个线程在同时读

 ### 代码

 {@code

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });
 }

 ### 控制台输出

 当前为读锁！
 当前为读锁！
 pool-1-thread-2:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-2:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-2:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-2:正在进行读操作……
 pool-1-thread-2:正在进行读操作……
 pool-1-thread-2:读操作完毕！
 释放读锁！
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:读操作完毕！
 释放读锁！

 ## 测试2：读与写互斥

 ### 结论

 日志输出表现：就是读线程在读的时候没有写，在写线程写的时候没有读

 ### 代码

 {@code

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
        // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });
 }

 ### 控制台输出

 当前为读锁！
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:正在进行读操作……
 pool-1-thread-1:读操作完毕！
 释放读锁！
 当前为写锁！
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:写操作完毕！
 释放写锁！

 ## 测试3：写与写互斥

 ### 结论

 日志输出表现：就是写线程1在写的时候，写线程2在等待

 ### 代码

 {@code

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
        // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });

 // 读写组合
 service.execute(new Runnable() {
     @Override
     public void run() {
         // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });
 }

 ### 控制台输出

 当前为写锁！
 pool-1-thread-1:正在进行写操作……
 pool-1-thread-1:正在进行写操作……
 pool-1-thread-1:正在进行写操作……
 pool-1-thread-1:正在进行写操作……
 pool-1-thread-1:正在进行写操作……
 pool-1-thread-1:写操作完毕！
 释放写锁！
 当前为写锁！
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:正在进行写操作……
 pool-1-thread-2:写操作完毕！
 释放写锁！

 </pre>

 * @author ursobeautiful@qq.com
 * @since 2018/3/17/0017 17:03
 */
public class ReadWriteLockTest {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        // 读写组合
        service.execute(new Runnable() {
            @Override
            public void run() {
//                readFile(Thread.currentThread());
                writeFile(Thread.currentThread());
            }
        });

        // 读写组合
        service.execute(new Runnable() {
            @Override
            public void run() {
//                readFile(Thread.currentThread());
                writeFile(Thread.currentThread());
            }
        });

        service.shutdown();
    }

    // 读操作
    private static void readFile(Thread thread) {
        lock.readLock().lock();
        if (!lock.isWriteLocked()) {
            System.out.println("当前为读锁！");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":正在进行读操作……");
            }
            System.out.println(thread.getName() + ":读操作完毕！");
        } finally {
            System.out.println("释放读锁！");
            lock.readLock().unlock();
        }
    }

    // 写操作
    private static void writeFile(Thread thread) {
        lock.writeLock().lock();
        if (lock.isWriteLocked()) {
            System.out.println("当前为写锁！");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":正在进行写操作……");
            }
            System.out.println(thread.getName() + ":写操作完毕！");
        } finally {
            System.out.println("释放写锁！");
            lock.writeLock().unlock();
        }
    }
}
