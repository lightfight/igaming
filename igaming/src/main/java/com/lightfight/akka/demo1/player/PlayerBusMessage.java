package com.lightfight.akka.demo1.player;

import java.util.Map;

/**
 * 描述: PlayerBus处理的消息</BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public class PlayerBusMessage {

    /**
     * 内网消息,传输的map
     */
    public static class Intranet{
        public final int key ;
        public final Map<String, Object> map ;
        public final long startTime ;

        public Intranet(int key, Map<String, Object> map, long startTime) {
            this.key = key;
            this.map = map;
            this.startTime = startTime;
        }
    }

    /**
     * 外网消息,传输的byte[]
     */
    public static class Extranet{
        public final int key ;
        public final byte[] bytes ;
        public final long startTime ;

        public Extranet(int key, byte[] bytes, long startTime) {
            this.key = key;
            this.bytes = bytes;
            this.startTime = startTime;
        }
    }
}
