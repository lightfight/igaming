package com.lightfight.consistenthash.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/5/17/0017
 */
class ShardClient {

    public static void main(String[] args) {
        List<ShardInfo> list = new ArrayList<>();

        list.add(new ShardInfo(1, "redis_server_1", "192.168.2.1",6379));
        list.add(new ShardInfo(2, "redis_server_2", "192.168.2.2",6379));
        list.add(new ShardInfo(3, "redis_server_3", "192.168.2.3",6379));

        Shard<ShardInfo> shard = new Shard<>(list);

        int key = 2902;
        ShardInfo s = shard.getShardInfo(key);
        System.out.println(key + " = " + s);
    }

    /**

     ## 基础数据

     i = 1, key = 1000 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1100 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1200 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1300 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1400 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1500 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1600 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1700 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1800 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 1, key = 1900 , info = ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}
     i = 2, key = 2000 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2100 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2200 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2300 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2400 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2500 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2600 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2700 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2800 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 2, key = 2900 , info = ShardInfo{id='2', name='redis_server_2', ip='192.168.2.2', port=6379}
     i = 3, key = 3000 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3100 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3200 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3300 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3400 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3500 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3600 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3700 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3800 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}
     i = 3, key = 3900 , info = ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}


     ## 测试

     测试1：key = 2902, 介于2900和3000之间,由于取的是higher,所以取下一个3000,获取的信息为：
     ShardInfo{id='3', name='redis_server_3', ip='192.168.2.3', port=6379}

     测试2：key = 3902, 超过最后一个node的key,取第一个key
     3902超过了最后一个3900,然后取的第一个1000,所以取出来的信息为：
     ShardInfo{id='1', name='redis_server_1', ip='192.168.2.1', port=6379}

     */
}
