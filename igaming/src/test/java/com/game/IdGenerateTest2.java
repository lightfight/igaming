package com.game;

import com.lightfight.game.algorithm.IdGenerator;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre></pre>
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/29/0029 16:38
 */
public class IdGenerateTest2 {


    /**
     * 测试对这个对象的调用
     */
    @Test
    public void testIntStrId(){
        IdGenerator generator = new IdGenerator(1023);

        long stt = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(generator.next());
//            generator.next(); // 没有控制台输出,测试的时间更快更准
        }

        System.out.println((System.currentTimeMillis() - stt) + " ms");
    }

    /**
     * 测试是否包含重复的值
     */
    @Test
    public void testContains(){
        IdGenerator generator = new IdGenerator(569);

        int count = 999999;
        Set<String> set = new HashSet<>(count);
        for (int i = 0; i < count; i++) {
            String id = generator.next();
            if (set.contains(id)) {
                System.out.println("重复 id = " + id);
            } else {
                set.add(id);
            }
        }

        System.out.println("没有重复的值");
    }
}
