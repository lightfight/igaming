package com.lightfight.game.uc.exchange.v7;

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

        /**
         *
         * 1.可以设置totalWorks[<,=,>]workQueueCapacity这3种情况
         * 2.可以设置workQueue = ArrayBlockingQueue与workQueue = LimitedQueue这2种情况
         *
         * 综上看看这6中情况
         *
         * 结论：totalWorks[>]workQueueCapacity时只能选择LimitedQueue
         *
         */

        int totalWorks = 20; // 任务总数
        int workQueueCapacity = 5; // 任务队列容量

        List<String> keys = keys(totalWorks);

        final CountDownLatch latch = new CountDownLatch(keys.size());

//        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(workQueueCapacity); // 当队列满的时候会拒绝,会抛出异常
        BlockingQueue<Runnable> workQueue = new LimitedQueue<>(workQueueCapacity); // 重写offer方法

        int nThreads = Runtime.getRuntime().availableProcessors() + 1; // 本电脑为4核
        ThreadPoolExecutor service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);

        for (String pid : keys) {
            service.execute(new RefreshWork(pid, latch));
//            TimeUnit.SECONDS.sleep(1);
            System.out.println("--------------workQueue.size = " + service.getQueue().size());
        }

        latch.await(); // 拦住等待执行结束
        service.shutdown();
        System.out.println("finished...");
    }

    /**
     <code>


     1.使用BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(workQueueCapacity)
     注意:线程池的容量大于等于任务的总数

     int totalWorks = 20; // 任务总数
     int workQueueCapacity = 20; // 任务队列容量

     1.1.可以看到前面5(4+1=5)个打印[workQueue.size]都为0,是因为核心线程数为5,
     当前运行线程数小于核心线程池数就会直接添加worker,并将execute传递过的任务作为第一个任务让worker执行而不是加入到workQueue中
     所以"workQueue.size = 0"

     1.2.除了前5个任务,后续提交的任务全部压入到workQueue中
     1.3.workQueue容量的大小
     1.3.1.指定大小,数组的必须指定大小,linkedQueue可以指定大小,默认为Integer.MAM_VALUE
     1.3.2.默认大小
     这两种都可能导致生产者产生的任务大于队列容量,所以需要使用阻塞的方法放入到队列,所以选择使用put重写offer

     1.4.实验数据

     --------------workQueue.size = 0
     --------------workQueue.size = 0
     pool-1-thread-1, pid = 0
     --------------workQueue.size = 0
     pool-1-thread-2, pid = 1
     pool-1-thread-3, pid = 2
     --------------workQueue.size = 0
     --------------workQueue.size = 0
     --------------workQueue.size = 1
     --------------workQueue.size = 2
     --------------workQueue.size = 3
     --------------workQueue.size = 4
     --------------workQueue.size = 5
     --------------workQueue.size = 6
     --------------workQueue.size = 7
     --------------workQueue.size = 8
     pool-1-thread-4, pid = 3
     --------------workQueue.size = 9
     --------------workQueue.size = 10
     --------------workQueue.size = 11
     --------------workQueue.size = 12
     --------------workQueue.size = 13
     --------------workQueue.size = 14
     --------------workQueue.size = 15
     pool-1-thread-5, pid = 4
     pool-1-thread-2, pid = 5
     pool-1-thread-1, pid = 6
     pool-1-thread-3, pid = 7
     pool-1-thread-4, pid = 8
     pool-1-thread-5, pid = 9
     pool-1-thread-2, pid = 10
     pool-1-thread-1, pid = 11
     pool-1-thread-3, pid = 12
     pool-1-thread-4, pid = 13
     pool-1-thread-5, pid = 14
     pool-1-thread-2, pid = 15
     pool-1-thread-1, pid = 16
     pool-1-thread-3, pid = 17
     pool-1-thread-4, pid = 18
     pool-1-thread-5, pid = 19

     2.使用BlockingQueue<Runnable> workQueue = new LimitedQueue<>(workQueueCapacity)

     int totalWorks = 20; // 任务总数
     int workQueueCapacity = 5; // 任务队列容量

     2.1.workQueue.size最大为workQueueCapacity，而且重要的是允许totalWorks > workQueueCapacity

     2.2.实验数据
     --------------workQueue.size = 0
     --------------workQueue.size = 0
     pool-1-thread-1, pid = 0
     --------------workQueue.size = 0
     --------------workQueue.size = 0
     --------------workQueue.size = 0
     --------------workQueue.size = 1
     --------------workQueue.size = 2
     --------------workQueue.size = 3
     --------------workQueue.size = 4
     --------------workQueue.size = 5
     pool-1-thread-5, pid = 4
     pool-1-thread-3, pid = 2
     pool-1-thread-4, pid = 3
     pool-1-thread-2, pid = 1
     --------------workQueue.size = 5
     pool-1-thread-1, pid = 5
     pool-1-thread-5, pid = 6
     --------------workQueue.size = 5
     pool-1-thread-3, pid = 7
     --------------workQueue.size = 5
     pool-1-thread-2, pid = 8
     pool-1-thread-4, pid = 9
     --------------workQueue.size = 5
     --------------workQueue.size = 5
     --------------workQueue.size = 5
     pool-1-thread-1, pid = 10
     pool-1-thread-5, pid = 11
     --------------workQueue.size = 5
     --------------workQueue.size = 5
     pool-1-thread-3, pid = 12
     --------------workQueue.size = 5
     pool-1-thread-4, pid = 13
     pool-1-thread-2, pid = 14
     --------------workQueue.size = 5
     pool-1-thread-1, pid = 15
     pool-1-thread-5, pid = 16
     pool-1-thread-3, pid = 17
     pool-1-thread-4, pid = 18
     pool-1-thread-2, pid = 19

     <code/>
     *
     */


    private static List<String> keys(int totalWorks){
        List<String> keys = new ArrayList<>();

        for (int i = 0; i < totalWorks; i++) {
            keys.add(String.valueOf(i));
        }

        return keys;
    }

    private static void refreshProperty(String pid){
        System.out.println(Thread.currentThread().getName() + ", pid = " + pid);
    }

    /**
     *
     * 刷新的工作
     *
     */
    static class RefreshWork implements Runnable{

        private final String pid;
        private final CountDownLatch latch;

        public RefreshWork(String pid, CountDownLatch latch){
            this.pid = pid;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                refreshProperty(pid);
                TimeUnit.SECONDS.sleep(1); // 模拟执行需要的时间
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // 无论何种情况,最后都减少一个计数
            }

        }
    }

    /**
     *
     * 将offer方法变成阻塞式的
     *
     */
    static class LimitedQueue<E> extends LinkedBlockingQueue<E> {
        public LimitedQueue(int maxSize) {
            super(maxSize);
        }

        @Override
        public boolean offer(E e) {
            // http://www.aichengxu.com/other/6569886.htm
            // turn offer() and add() into a blocking calls (unless interrupted)
            try {
                put(e);
                return true;
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }
}
