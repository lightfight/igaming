package com.lightfight.jedis.pubsub;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/4/28/0028
 */
public class TestSubscribe {

    @Before
    public void connect(){
        Redis.init();
    }

    @Test
    public void testSubscribe() throws Exception{
        Jedis jedis = Redis.get(0);
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel:" + channel + "receives message :" + message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("channel:" + channel + "is been subscribed:" + subscribedChannels);
            }
        }, "redisChatTest");
    }
}
