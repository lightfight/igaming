package com.lightfight.game.uc.threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/4/1/0001
 */
public class CachedThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {

        long keepAliveTime = 2L; // 保持线程存活的时间,超过这个时间,创建的worker就会从workers中被remove掉
        long threadSpaceTime = 3L; // 调用生产任务的间隔时间

        final AtomicInteger count = new AtomicInteger();

//        ExecutorService executor = Executors.newCachedThreadPool();
        // Executors.newCachedThreadPool()的内部取出来,修改里面的keepAliveTime时间,方便测试

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                keepAliveTime, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()); // 注意这里是一个SynchronousQueue阻塞队列

        for (int i = 0; i < 5; i++) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " = " + count.incrementAndGet());
                }
            });

            System.out.println("before workers.size() = " + executor.getPoolSize());
            TimeUnit.SECONDS.sleep(threadSpaceTime);
            System.out.println("after workers.size() = " + executor.getPoolSize());

        }
    }

    /**


     前提假设
     ----
     为了简化分析,假设Runnable.run的执行时间为0(本测试中只是一个System.out.println),
     如果执行时间需要很长的时间,可能每次也需要创建worker

     1.threadSpaceTime > keepAliveTime

     - 数据

     before workers.size() = 1
     pool-1-thread-1 = 1
     after workers.size() = 0
     before workers.size() = 1
     pool-1-thread-2 = 2
     after workers.size() = 0
     before workers.size() = 1
     pool-1-thread-3 = 3
     after workers.size() = 0
     before workers.size() = 1
     pool-1-thread-4 = 4
     after workers.size() = 0
     before workers.size() = 1
     pool-1-thread-5 = 5

     - 分析

     因为[生产任务的间隔时间]大于[保持线程存活的时间],所以每次提交任务的时候,worker活动线程已经被remove掉了,所以会新创建一个worker
     所以pool-1-thread-${N}中的N在依次递增
     ${before}为1
     ${after}为0

     2.threadSpaceTime < keepAliveTime

     - 数据

     before workers.size() = 1
     pool-1-thread-1 = 1
     after workers.size() = 1
     before workers.size() = 1
     pool-1-thread-1 = 2
     after workers.size() = 1
     before workers.size() = 1
     pool-1-thread-1 = 3
     after workers.size() = 1
     before workers.size() = 1
     pool-1-thread-1 = 4
     after workers.size() = 1
     before workers.size() = 1
     pool-1-thread-1 = 5
     after workers.size() = 1

     - 分析

     因为[生产任务的间隔时间]小于[保持线程存活的时间],所以每次提交任务的时候,worker活动线程还存在,会被复用,不会新创建worker线程
     所以pool-1-thread-${N}中的N保持不变为1
     ${before}为1
     ${after}为1

     3.透过现象看本质

     原因在于ThreadPoolExecutor.getTask

     调用过程:execute-->addWorker-->new Worker-->t.start()-->run()
     -->runWorker-->while-->getTask-->processWorkerExit

     <code>

     for (;;) {
     int wc = workerCountOf(c);
     timed = allowCoreThreadTimeOut || wc > corePoolSize;
     // newCachedThreadPool中corePoolSize=0,有一个活动线程timed=true

     if (wc <= maximumPoolSize && ! (timedOut && timed))
     break;
     if (compareAndDecrementWorkerCount(c))
     return null;
     c = ctl.get();  // Re-read ctl
     if (runStateOf(c) != rs)
     continue retry;
     // else CAS failed due to workerCount change; retry inner loop
     }

     try {
     // 因为timed为true,必然执行workQueue.poll,这就是[保持线程存活的时间]
     Runnable r = timed ?
     workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
     workQueue.take();
     if (r != null)
     return r;
     timedOut = true;
     } catch (InterruptedException retry) {
     timedOut = false;
     }

     </code>

     */
}
