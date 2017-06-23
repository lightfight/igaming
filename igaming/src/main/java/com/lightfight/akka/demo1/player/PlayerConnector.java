package com.lightfight.akka.demo1.player;

import akka.actor.ActorRef;

/**
 * 玩家连接器,链接socket和akka</BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public class PlayerConnector {

    public final String pid;

    private final ActorRef ref;

    public PlayerConnector(String pid, ActorRef ref) {
        this.pid = pid;
        this.ref = ref;
    }

    /**
     * 发送消息
     * @param message
     */
    public void tell(Object message){
        ref.tell(message, ActorRef.noSender());
    }
}
