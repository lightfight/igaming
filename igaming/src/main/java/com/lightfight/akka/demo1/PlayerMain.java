package com.lightfight.akka.demo1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lightfight.Logback;
import com.lightfight.akka.demo1.player.PlayerBusMessage;
import com.lightfight.akka.demo1.player.PlayerConnector;
import com.lightfight.akka.demo1.player.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 描述: 玩家测试类</BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public class PlayerMain {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerMain.class);


    public static void main(String[] args) {

        Logback.init();

        final ActorSystem system = ActorSystem.create("logic");

        // ## 初始化
        PlayerManager.init(system);

        // ## 创建子actor
        for (int i = 0; i < 10; i++) {
            String pid = "pid_" + i;
            ActorRef ref = PlayerManager.createBus(pid);

            PlayerConnector connector = new PlayerConnector(pid, ref);
            PlayerManager.putConnector(connector);
        }

        // ## 监控
        PlayerManager.tellMonitor();

        // ## 给所有的actor发送消息
        Collection<PlayerConnector> connectors = PlayerManager.getConnectors();
        for (PlayerConnector connector : connectors) {
            connector.tell(new PlayerBusMessage.Intranet(100, null, 0L));
        }

        // ## 给单个的actor发送消息,检查消息发送是否有序,测试结论[有序],应该单个actor有自己的信箱MailBox
        String pid = "pid_1";
        PlayerConnector connector = PlayerManager.getConnector(pid);
        for (int i = 0; i < 100; i++) {
            connector.tell(new PlayerBusMessage.Intranet(i, null, 0L));
        }
        LOG.info("给玩家{}发送消息完毕", pid);

    }
}
