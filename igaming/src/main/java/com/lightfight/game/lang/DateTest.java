package com.lightfight.game.lang;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class DateTest {

    /**
     * 毫秒转换为字符串的日期
     */
    @Test
    public void milliToDateStr(){

//        long milli = System.currentTimeMillis();
        long milli = 1488930319487L;

        Date d = new Date(milli);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
    }
}
