package com.lightfight.game.uc.qian;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/22/0022
 */
public class RefreshMongodb {

    /**
     * 测试阻塞队列的容量
     */
    @Test
    public void testQueueCapacity() throws InterruptedException {

        final AtomicInteger counter = new AtomicInteger();
        int nThreads = Runtime.getRuntime().availableProcessors();

        /**rejected from java.util.concurrent.ThreadPoolExecutor@5a9e260c
         * [Running, pool size = 4, active threads = 4, queued tasks = 10, completed tasks = 0]
         */
         // BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10); // 设置队列长度为10

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(); //

        ExecutorService executor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, queue);

        for (int i = 0; i < 100; i++) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " = " + counter.incrementAndGet());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.awaitTermination(300, TimeUnit.SECONDS);
    }
}
