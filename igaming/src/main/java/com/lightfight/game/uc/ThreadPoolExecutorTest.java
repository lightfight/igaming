package com.lightfight.game.uc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: </BR>
 * ref:http://blog.csdn.net/wenhuayuzhihui/article/details/51377174
 * <p>
 * Created by caidl on 2017/3/14/0014
 */
public class ThreadPoolExecutorTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    /**
     * 其中COUNT_BITS是 int 位数
     private static final int COUNT_BITS = Integer.SIZE - 3;  //Integer.SIZE=32
     所以实际 COUNT_BITS = 29，
     用上面的5个常量表示线程池的状态，实际上是使用32位中的高3位表示；后面还会讲到这些常量；
     高3位：
     RUNNING=111
     SHUTDOWN=000
     STOP=001
     TIDYING=010
     TERMINATED=110
     */

    @Test
    public void testRunState(){
        System.out.println(RUNNING);
        int value = ctlOf(RUNNING, 0);
        System.out.println(value);
    }

    // Packing and unpacking ctl
    int runStateOf(int c)     { return c & ~CAPACITY; }
    int workerCountOf(int c)  { return c & CAPACITY; }
    int ctlOf(int rs, int wc) { return rs | wc; }
}
