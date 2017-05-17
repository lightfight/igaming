package com.lightfight.jedis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public final class Redis {

    private Redis(){}

    private static JedisPool pool = null ;
    private static Map<Integer , JedisPool> Pools = new HashMap<>() ;

    public static void init(){
        initPool("192.168.2.154", 6379, "lixin", 0);
    }

    public static void initPool(String host , int port , String auth , int db){
        boolean TEST_ON_BORROW = true;
        JedisPoolConfig config = new JedisPoolConfig() ;
        config.setTestOnBorrow(TEST_ON_BORROW);
        pool = new JedisPool(config , host , port , 2000 , auth  , db) ;
        Pools.put(db , pool) ;
        System.out.println("redis 启动成功.....");
    }

    public static Jedis get(int db){
        if(Pools.containsKey(db)){
            return Pools.get(db).getResource() ;
        }
        return null ;
    }
}
