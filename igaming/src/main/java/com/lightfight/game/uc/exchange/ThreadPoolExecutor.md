
目录
--
- [参考文章](#参考文章)
- [execute](#execute)
- [添加工人](#添加工人)
- [Worker](#Worker)
- [runWorker](#runWorker)
- [移除工人](#移除工人)
- [拒绝策略](#拒绝策略)
- [比较offer和put](#比较offer和put)
- [用子类重写offer方法](#用子类重写offer方法)
- [在队列为空的情况下,ThreadPool会不会清除核心线程?](#在队列为空的情况下,ThreadPool会不会清除核心线程?)

参考文章
----

百度搜索：ThreadPoolExecutor源码解析

1. [Java并发包源码学习之线程池（一）ThreadPoolExecutor源码分析](http://www.cnblogs.com/zhanjindong/p/java-concurrent-package-ThreadPoolExecutor.html)
2. [浅谈java线程池](https://my.oschina.net/xionghui/blog/494004)
3. [ThreadPoolExecutor源码详解 ](https://my.oschina.net/xionghui/blog/494698)
4. [让ThreadPoolExecutor的workQueue占满时自动阻塞](http://www.aichengxu.com/other/6569886.htm)

本文是对上面文章的一个整合以及自己的理解和扩展，_主要偏基础_

execute
-------

execute是一个开始,接收任务,可能做的事情有3个

1. 创建worker
2. 将任务添加到任务队列中
3. 如果以上两个都未执行,那么会拒绝接受这个任务

```java
public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        // 如果正在运行,而且可以加入到任务队列中
        if (isRunning(c) && workQueue.offer(command)) { // 任务队列中增加任务
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }
```
添加工人
--------
worker本身是一个Runnable,会对参数任务设置为第一个任务,添加成功一个worker就会调用worker的run方法,
而run方法会调用runWorker


```java
 private boolean addWorker(Runnable firstTask, boolean core) {
         retry:
         for (;;) {
             int c = ctl.get();
             int rs = runStateOf(c);
 
             // Check if queue empty only if necessary.
             if (rs >= SHUTDOWN &&
                 ! (rs == SHUTDOWN &&
                    firstTask == null &&
                    ! workQueue.isEmpty()))
                 return false;
 
             for (;;) {
                 int wc = workerCountOf(c);
                 if (wc >= CAPACITY ||
                     wc >= (core ? corePoolSize : maximumPoolSize))
                     return false; // 如果已经拥有的工人数大于等于核心数或最大数,那么不创建工人
                 if (compareAndIncrementWorkerCount(c))
                     break retry; // 如果数量没有超过,那么就增加1个工人的数量,结束for循环
                 c = ctl.get();  // Re-read ctl
                 if (runStateOf(c) != rs)
                     continue retry;
                 // else CAS failed due to workerCount change; retry inner loop
             }
         }
 
         boolean workerStarted = false;
         boolean workerAdded = false;
         Worker w = null;
         try {
             final ReentrantLock mainLock = this.mainLock;
             w = new Worker(firstTask);
             final Thread t = w.thread;
             if (t != null) {
                 mainLock.lock();
                 try {
                     // Recheck while holding lock.
                     // Back out on ThreadFactory failure or if
                     // shut down before lock acquired.
                     int c = ctl.get();
                     int rs = runStateOf(c);
 
                     if (rs < SHUTDOWN ||
                         (rs == SHUTDOWN && firstTask == null)) {
                         if (t.isAlive()) // precheck that t is startable
                             throw new IllegalThreadStateException();
                         workers.add(w);
                         int s = workers.size();
                         if (s > largestPoolSize)
                             largestPoolSize = s;
                         workerAdded = true;
                     }
                 } finally {
                     mainLock.unlock();
                 }
                 if (workerAdded) {
                     t.start(); // 添加工人成功后就启动线程,然后run方法里是一个while,不停的从任务队列中获取任务
                     workerStarted = true;
                 }
             }
         } finally {
             if (! workerStarted)
                 addWorkerFailed(w);
         }
         return workerStarted;
     }
```

Worker
-----

```java
/**
 * Creates with given first task and thread from ThreadFactory.
 * @param firstTask the first task (null if none)
 */
Worker(Runnable firstTask) {
    setState(-1); // inhibit interrupts until runWorker
    this.firstTask = firstTask;
    this.thread = getThreadFactory().newThread(this);
}

/** Delegates main run loop to outer runWorker  */
public void run() {
    runWorker(this); // worker的run方法调用runWorker方法
}
```

runWorker
---------

正如该方法的注释所说
>工人的循环方法,会重复的从任务队列中获取任务并执行它们

```java
/**
     * Main worker run loop.  Repeatedly gets tasks from queue and
     * executes them, while coping with a number of issues:
     *
     * 1. We may start out with an initial task, in which case we
     * don't need to get the first one. Otherwise, as long as pool is
     * running, we get tasks from getTask. If it returns null then the
     * worker exits due to changed pool state or configuration
     * parameters.  Other exits result from exception throws in
     * external code, in which case completedAbruptly holds, which
     * usually leads processWorkerExit to replace this thread.
     *
     * 2. Before running any task, the lock is acquired to prevent
     * other pool interrupts while the task is executing, and
     * clearInterruptsForTaskRun called to ensure that unless pool is
     * stopping, this thread does not have its interrupt set.
     *
     * 3. Each task run is preceded by a call to beforeExecute, which
     * might throw an exception, in which case we cause thread to die
     * (breaking loop with completedAbruptly true) without processing
     * the task.
     *
     * 4. Assuming beforeExecute completes normally, we run the task,
     * gathering any of its thrown exceptions to send to
     * afterExecute. We separately handle RuntimeException, Error
     * (both of which the specs guarantee that we trap) and arbitrary
     * Throwables.  Because we cannot rethrow Throwables within
     * Runnable.run, we wrap them within Errors on the way out (to the
     * thread's UncaughtExceptionHandler).  Any thrown exception also
     * conservatively causes thread to die.
     *
     * 5. After task.run completes, we call afterExecute, which may
     * also throw an exception, which will also cause thread to
     * die. According to JLS Sec 14.20, this exception is the one that
     * will be in effect even if task.run throws.
     *
     * The net effect of the exception mechanics is that afterExecute
     * and the thread's UncaughtExceptionHandler have as accurate
     * information as we can provide about any problems encountered by
     * user code.
     *
     * @param w the worker
     */
    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if ((runStateAtLeast(ctl.get(), STOP) ||
                     (Thread.interrupted() &&
                      runStateAtLeast(ctl.get(), STOP))) &&
                    !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x; throw x;
                    } catch (Error x) {
                        thrown = x; throw x;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
```

移除工人
------
代码搜索：`workers.remove`

- 添加失败要回滚
```java
/**
     * Rolls back the worker thread creation.
     * - removes worker from workers, if present
     * - decrements worker count
     * - rechecks for termination, in case the existence of this
     *   worker was holding up termination
     */
    private void addWorkerFailed(Worker w) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (w != null)
                workers.remove(w);
            decrementWorkerCount();
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }
```

- 工人执行runWork时不能从任务队列中获取任务或者执行任务被中断
```java
/**
     * Performs cleanup and bookkeeping for a dying worker. Called
     * only from worker threads. Unless completedAbruptly is set,
     * assumes that workerCount has already been adjusted to account
     * for exit.  This method removes thread from worker set, and
     * possibly terminates the pool or replaces the worker if either
     * it exited due to user task exception or if fewer than
     * corePoolSize workers are running or queue is non-empty but
     * there are no workers.
     *
     * @param w the worker
     * @param completedAbruptly if the worker died due to user exception
     */
    private void processWorkerExit(Worker w, boolean completedAbruptly) {
        if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
            decrementWorkerCount();

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += w.completedTasks;
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }

        tryTerminate();

        int c = ctl.get();
        // (lessThan STOP)其实就是如果还处于运行状态,就可能需要创建新的工人,那么进行下一步的逻辑判断
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) { // 如果是正常结束的,
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                if (min == 0 && ! workQueue.isEmpty())
                    min = 1;
                // 如果还有不止1个工人(允许工作线程超时)或者大于等于核心数(不允许工作线程超时),那么就不需要创建工人了
                if (workerCountOf(c) >= min) 
                    return; // replacement not needed
            }
            addWorker(null, false);
        }
    }
```

拒绝策略
-----
参看文章：[让ThreadPoolExecutor的workQueue占满时自动阻塞](http://www.aichengxu.com/other/6569886.htm)
>如何在ThreadPoolExecutor的workQueue全满的情况下，
使得execute()方法能block在那里，一直等到有资源了，再继续提交task?

让ThreadPoolExecutor使用自己实现的RejectedExecutionHandler，在其中阻塞式地将task放到workQueue中

```java
BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);

int nThreads = Runtime.getRuntime().availableProcessors();
ThreadPoolExecutor service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
service.setRejectedExecutionHandler(new RejectedExecutionHandler() {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            try {
                executor.getQueue().put(r); // 重写拒绝策略,默认使用的offer(队列满时非阻塞的),所以要改为put(队列满时阻塞的)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
});
```

比较offer和put
-------------

1. 在阻塞`队列未满`的情况下,offer和put没有区别,都会直接`insert`
2. 在阻塞`队列满`的情况下,offer会直接返回false,put会进入等待状态

<table>
    <tr><td>阻塞队列状态</td><td>offer</td><td>put</td></tr>
    <tr><td>未满</td><td>直接插入队列</td><td>直接插入队列</td></tr>
    <tr><td>满</td><td>(非阻塞)直接返回false</td><td>(阻塞)进入await状态</td></tr>
</table>

- ArrayBlockingQueue

```java
/**
 * Inserts the specified element at the tail of this queue if it is
 * possible to do so immediately without exceeding the queue's capacity,
 * returning {@code true} upon success and {@code false} if this queue
 * is full.  This method is generally preferable to method {@link #add},
 * which can fail to insert an element only by throwing an exception.
 *
 * @throws NullPointerException if the specified element is null
 */
public boolean offer(E e) {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        if (count == items.length)
            return false; // 满了后会直接返回
        else {
            insert(e);
            return true;
        }
    } finally {
        lock.unlock();
    }
}
```
```java
/**
 * Inserts the specified element at the tail of this queue, waiting
 * for space to become available if the queue is full.
 *
 * @throws InterruptedException {@inheritDoc}
 * @throws NullPointerException {@inheritDoc}
 */
public void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
        while (count == items.length)
            notFull.await();
        insert(e);
    } finally {
        lock.unlock();
    }
}
```

- LinkedBlockingQueue

```java
/**
 * Inserts the specified element at the tail of this queue if it is
 * possible to do so immediately without exceeding the queue's capacity,
 * returning {@code true} upon success and {@code false} if this queue
 * is full.
 * When using a capacity-restricted queue, this method is generally
 * preferable to method {@link BlockingQueue#add add}, which can fail to
 * insert an element only by throwing an exception.
 *
 * @throws NullPointerException if the specified element is null
 */
public boolean offer(E e) {
    if (e == null) throw new NullPointerException();
    final AtomicInteger count = this.count;
    if (count.get() == capacity)
        return false; // 满了后会直接返回
    int c = -1;
    Node<E> node = new Node(e);
    final ReentrantLock putLock = this.putLock;
    putLock.lock();
    try {
        if (count.get() < capacity) {
            enqueue(node);
            c = count.getAndIncrement();
            if (c + 1 < capacity)
                notFull.signal();
        }
    } finally {
        putLock.unlock();
    }
    if (c == 0)
        signalNotEmpty();
    return c >= 0;
}
```

```java
/**
 * Inserts the specified element at the tail of this queue, waiting if
 * necessary for space to become available.
 *
 * @throws InterruptedException {@inheritDoc}
 * @throws NullPointerException {@inheritDoc}
 */
public void put(E e) throws InterruptedException {
    if (e == null) throw new NullPointerException();
    // Note: convention in all put/take/etc is to preset local var
    // holding count negative to indicate failure unless set.
    int c = -1;
    Node<E> node = new Node(e);
    final ReentrantLock putLock = this.putLock;
    final AtomicInteger count = this.count;
    putLock.lockInterruptibly();
    try {
        /*
         * Note that count is used in wait guard even though it is
         * not protected by lock. This works because count can
         * only decrease at this point (all other puts are shut
         * out by lock), and we (or some other waiting put) are
         * signalled if it ever changes from capacity. Similarly
         * for all other uses of count in other wait guards.
         */
        while (count.get() == capacity) {
            notFull.await();
        }
        enqueue(node);
        c = count.getAndIncrement();
        if (c + 1 < capacity)
            notFull.signal();
    } finally {
        putLock.unlock();
    }
    if (c == 0)
        signalNotEmpty();
}
```

用子类重写offer方法
--
`getQueue()`带来的风险
- ThreadPoolExecutor的API不建议这样做(比如在拒绝策略中获取workQueue向其中put)
- 可能导致死锁

```java
/**
 * Returns the task queue used by this executor. Access to the
 * task queue is intended primarily for debugging and monitoring.
 * This queue may be in active use.  Retrieving the task queue
 * does not prevent queued tasks from executing.
 *
 * @return the task queue
 */
public BlockingQueue<Runnable> getQueue() {
    return workQueue;
}
```
可见，API已经说明了：getQueue()主要是用于调试和监控

**让offer方法变成阻塞的,直接在offer方法中调用put**

>由于submit()是调用workQueue的offer()方法来添加task的，而offer()是非阻塞的，
所以，如果我们自己实现一个BlockingQueue，其offer()方法是阻塞的，
那么，就可以用它和ThreadPoolExecutor配合，来实现submit()方法在workQueue满时的阻塞效果了

```java
public class LimitedQueue<E> extends LinkedBlockingQueue<E> {
  public LimitedQueue(int maxSize) {
    super(maxSize);
  }

  @Override
  public boolean offer(E e) {
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
```

在队列为空的情况下,ThreadPool会不会清除核心线程?
----
要回答这个问题，就要看runWorker方法和getTask方法，以及从队列中获取元素是否允许超时

- runWorker 

runWorker在while循环的条件中调用getTask

```java
final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) { // 在while循环的条件中调用getTask
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if ((runStateAtLeast(ctl.get(), STOP) ||
                     (Thread.interrupted() &&
                      runStateAtLeast(ctl.get(), STOP))) &&
                    !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x; throw x;
                    } catch (Error x) {
                        thrown = x; throw x;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
```

- getTask 

`getTask`中如果不是超时的,那么从队列中获取任务使用的就是`workQueue.take()`,
而take这个方法是阻塞的(当队列为空的时候就会await),会造成while中的等待

```java
 private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?

        retry:
        for (;;) {
            int c = ctl.get();
            int rs = runStateOf(c);

            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            boolean timed;      // Are workers subject to culling?

            for (;;) {
                int wc = workerCountOf(c);
                timed = allowCoreThreadTimeOut || wc > corePoolSize;

                if (wc <= maximumPoolSize && ! (timedOut && timed))
                    break;
                // 如果上面的if没有break就会执行这个if,再返回null,
                // 在runWorker的while循环中getTask一个null,就会结束一个worker
                if (compareAndDecrementWorkerCount(c)) 
                    return null;
                c = ctl.get();  // Re-read ctl
                if (runStateOf(c) != rs)
                    continue retry;
                // else CAS failed due to workerCount change; retry inner loop
            }

            try {
                Runnable r = timed ? // 如果允许核心线程超时
                    workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                    workQueue.take(); // 如果不是时间控制的,从队列中获取任务使用的就是take,而take这个方法是阻塞的
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }
```

- ArrayBlockingQueue.take()

```java
public E poll(long timeout, TimeUnit unit) throws InterruptedException {
    E x = null;
    int c = -1;
    long nanos = unit.toNanos(timeout);
    final AtomicInteger count = this.count;
    final ReentrantLock takeLock = this.takeLock;
    takeLock.lockInterruptibly();
    try {
        while (count.get() == 0) {
            if (nanos <= 0)
                return null;
            nanos = notEmpty.awaitNanos(nanos);
        }
        x = dequeue();
        c = count.getAndDecrement();
        if (c > 1)
            notEmpty.signal();
    } finally {
        takeLock.unlock();
    }
    if (c == capacity)
        signalNotFull();
    return x;
}
    
 public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) { // 当队列为空的时候就会await
                notEmpty.await();
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }
```

- 回答

主要看是否允许核心线程超时

1. 如果允许超时`allowCoreThreadTimeOut`,就会调用`poll(long timeout, TimeUnit unit)`,
但是队列一直为空,就最多等待`keepAliveTime`这么长的时间,
如果等待完所有等待时间还是没有数据就会返回null，然而在`runWorker`的while循环中`getTask`一个`null`,
就会结束一个worker

```java
public E poll(long timeout, TimeUnit unit) throws InterruptedException {
    while (count.get() == 0) { // 为空就需要倒计时
        if (nanos <= 0) // 倒计时时间到了,就返回null
            return null;
        nanos = notEmpty.awaitNanos(nanos); // 倒计时
    }
}
```

如果在等待的过程中有数据了,必然`count.get()>0`,上面的while结束,从而获取数据

2. 如果是默认的(不允许超时)，就会调用`take`，而take是阻塞的，会一直等待直到有数据

3. 附上是否超时以及超时时间的说明

```java
/**
* If false (default), core threads stay alive even when idle.
* If true, core threads use keepAliveTime to time out waiting
* for work.
*/
private volatile boolean allowCoreThreadTimeOut;

/**
* Timeout in nanoseconds for idle threads waiting for work.
* Threads use this timeout when there are more than corePoolSize
* present or if allowCoreThreadTimeOut. Otherwise they wait
* forever for new work.
*/
private volatile long keepAliveTime;
```























