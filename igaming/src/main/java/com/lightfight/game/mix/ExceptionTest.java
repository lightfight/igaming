package com.lightfight.game.mix;


import org.junit.Test;

/**
 * @author caidl
 * @date 2017/7/12/0012
 */
public class ExceptionTest {


    @Test
    public void testException(){

        try {
            hand();
        } catch (Exception e) {
            System.out.println("catch");
            e.printStackTrace();
        }
    }

    private void hand(){

        Integer value = null;
        try {
            System.out.println(value.intValue());
        }finally { // 如果这儿已经是
            System.out.println("finally");
        }
    }
}
