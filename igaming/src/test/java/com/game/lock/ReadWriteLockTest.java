package com.game.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 ��д���Ļ����Բ���

 <pre>

 [java�������ϵ��֮ReadWriteLock��д����ʹ��](http://blog.csdn.net/liuchuanhong1/article/details/53539341)

 ## ����

 1. �����������
 2. ����д����
 3. д��д����

 ## ����1�������������

 ### ����

 ��־������֣����Ƕ��߳�1�ڶ���ʱ����߳�2Ҳ�ڶ��������߳���ͬʱ��

 ### ����

 {@code

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });
 }

 ### ����̨���

 ��ǰΪ������
 ��ǰΪ������
 pool-1-thread-2:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-2:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-2:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-2:���ڽ��ж���������
 pool-1-thread-2:���ڽ��ж���������
 pool-1-thread-2:��������ϣ�
 �ͷŶ�����
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:��������ϣ�
 �ͷŶ�����

 ## ����2������д����

 ### ����

 ��־������֣����Ƕ��߳��ڶ���ʱ��û��д����д�߳�д��ʱ��û�ж�

 ### ����

 {@code

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
        readFile(Thread.currentThread());
        // writeFile(Thread.currentThread());
     }
 });

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
        // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });
 }

 ### ����̨���

 ��ǰΪ������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:���ڽ��ж���������
 pool-1-thread-1:��������ϣ�
 �ͷŶ�����
 ��ǰΪд����
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:д������ϣ�
 �ͷ�д����

 ## ����3��д��д����

 ### ����

 ��־������֣�����д�߳�1��д��ʱ��д�߳�2�ڵȴ�

 ### ����

 {@code

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
        // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });

 // ��д���
 service.execute(new Runnable() {
     @Override
     public void run() {
         // readFile(Thread.currentThread());
        writeFile(Thread.currentThread());
     }
 });
 }

 ### ����̨���

 ��ǰΪд����
 pool-1-thread-1:���ڽ���д��������
 pool-1-thread-1:���ڽ���д��������
 pool-1-thread-1:���ڽ���д��������
 pool-1-thread-1:���ڽ���д��������
 pool-1-thread-1:���ڽ���д��������
 pool-1-thread-1:д������ϣ�
 �ͷ�д����
 ��ǰΪд����
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:���ڽ���д��������
 pool-1-thread-2:д������ϣ�
 �ͷ�д����

 </pre>

 * @author ursobeautiful@qq.com
 * @since 2018/3/17/0017 17:03
 */
public class ReadWriteLockTest {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        // ��д���
        service.execute(new Runnable() {
            @Override
            public void run() {
//                readFile(Thread.currentThread());
                writeFile(Thread.currentThread());
            }
        });

        // ��д���
        service.execute(new Runnable() {
            @Override
            public void run() {
//                readFile(Thread.currentThread());
                writeFile(Thread.currentThread());
            }
        });

        service.shutdown();
    }

    // ������
    private static void readFile(Thread thread) {
        lock.readLock().lock();
        if (!lock.isWriteLocked()) {
            System.out.println("��ǰΪ������");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":���ڽ��ж���������");
            }
            System.out.println(thread.getName() + ":��������ϣ�");
        } finally {
            System.out.println("�ͷŶ�����");
            lock.readLock().unlock();
        }
    }

    // д����
    private static void writeFile(Thread thread) {
        lock.writeLock().lock();
        if (lock.isWriteLocked()) {
            System.out.println("��ǰΪд����");
        }
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":���ڽ���д��������");
            }
            System.out.println(thread.getName() + ":д������ϣ�");
        } finally {
            System.out.println("�ͷ�д����");
            lock.writeLock().unlock();
        }
    }
}
