package com.lightfight.game.uc.exchange.v6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/28/0028
 */
public class ExchangeTest {

    public static void main(String[] args) throws InterruptedException {

        List<String> keys = keys();

        final CountDownLatch latch = new CountDownLatch(keys.size());
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);

        int nThreads = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
        service.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (!executor.isShutdown()) {
                    try {
                        /**
                         * Returns the task queue used by this executor. Access to the
                         * task queue is intended primarily for debugging and monitoring.
                         * This queue may be in active use.  Retrieving the task queue
                         * does not prevent queued tasks from executing.
                         *
                         * 在拒绝策略中把元素通过阻塞的方式放入进去,但是不建议这样做
                         * 在这个方法中的注释中已经说明了"for debugging and monitoring"打算用来调试和监控
                         *
                         */
                        executor.getQueue().put(r); // 重写拒绝策略,默认使用的offer(非阻塞的),所以要改为put(阻塞的)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        for (final String key : keys) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    refreshProperty(key);
                    latch.countDown();
                }
            });
        }

        latch.await(); // 拦住等待执行结束
        service.shutdown();
        System.out.println("finished...");
    }


    private static List<String> keys(){
        List<String> keys = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            keys.add(String.valueOf(i));
        }

        return keys;
    }
    private static void refreshProperty(String pid){
        System.out.println("pid = " + pid);
    }
}
