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

        latch.await();
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
