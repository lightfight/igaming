package com.lightfight.akka.demo1.player;

/**
 * 描述: 传递给{@link com.lightfight.akka.demo1.player.PlayerHub}的消息</BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public final class PlayerHubMessage {

    private PlayerHubMessage(){}

    public static class Create{
        public final String pid;
        public Create(String pid){
            this.pid = pid;
        }
    }

    public static class Stop {
        public final String pid ;
        public Stop(String pid){
            this.pid = pid ;
        }
    }

    public static class Monitor{

    }

}
