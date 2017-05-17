package com.lightfight.jedis.pubsub;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/4/28/0028
 */
public class TestPublish {

    @Before
    public void connect(){
        Redis.init();
    }

    @Test
    public void testPublish() throws Exception{
        Jedis jedis = Redis.get(0);
        jedis.publish("redisChatTest", "我是天才");
        Thread.sleep(1000);
        jedis.publish("redisChatTest", "我牛逼");
        Thread.sleep(1000);
        jedis.publish("redisChatTest", "哈哈");
    }
}
