package com.lightfight.consistenthash.v1;

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

        list.add(new ShardInfo("redis_server_1", "192.168.2.64",6379));
        list.add(new ShardInfo("redis_server_2", "192.168.2.65",6379));

        Shard<ShardInfo> shard = new Shard<>(list);

        for (int i = 0; i < 10; i++) {
            String key = "key:" + i;
            ShardInfo s = shard.getShardInfo(key);
            System.out.println(key + " = " + s);
        }

    }
}
