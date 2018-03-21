package com.lightfight.akka.demo1.player;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.routing.SmallestMailboxPool;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 玩家管理类</BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public final class PlayerManager {

    private PlayerManager(){}

    private static ActorRef PLAYER_HUB;

    /**
     * [玩家ID,玩家角色]
     * 使用统一的cache管理器(JBoss)来管理,这样写只是为了方便测试
     */
    private static final Map<String, PlayerConnector> CONNECTORS = new HashMap<>();

    /**
     * 初始化
     * @param system
     */
    public static void init(ActorSystem system){
        PLAYER_HUB = system.actorOf(new SmallestMailboxPool(32).props(Props.create(PlayerHub.class)), "player_hub") ;
    }

    /**
     * 创建玩家bus
     * @param pid
     * @return
     */
    public static final ActorRef createBus(String pid){
        final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        try {
            final Future<Object> future = Patterns.ask(PLAYER_HUB, new PlayerHubMessage.Create(pid), timeout);
            ActorRef bus = (ActorRef) Await.result(future, timeout.duration());
            return bus ;
        } catch (Exception e) {
            destroyBus(pid);
        }
        return null ;
    }

    /**
     * 销毁玩家
     * @param pid
     */
    public static void destroyBus(String pid){
        CONNECTORS.remove(pid);
        PLAYER_HUB.tell(new PlayerHubMessage.Stop(pid), ActorRef.noSender());
    }

    /**
     * 添加进去
     * @param connector
     */
    public static void putConnector(PlayerConnector connector){
        CONNECTORS.put(connector.pid, connector);
    }

    public static PlayerConnector getConnector(String pid){
        return CONNECTORS.get(pid);
    }

    public static Collection<PlayerConnector> getConnectors(){
        return CONNECTORS.values();
    }

    /**
     * 监控
     */
    public static void tellMonitor(){
        PLAYER_HUB.tell(new PlayerHubMessage.Monitor(), ActorRef.noSender());
    }

}
