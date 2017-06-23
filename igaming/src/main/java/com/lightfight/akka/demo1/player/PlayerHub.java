package com.lightfight.akka.demo1.player;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * 描述: 玩家集线器,根据玩家pid,用来创建/停止actor </BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public class PlayerHub extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerHub.class);

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof PlayerHubMessage.Create) { // 如果是创建子actor

            PlayerHubMessage.Create create = (PlayerHubMessage.Create) message;

            if (LOG.isInfoEnabled()) {
                LOG.info("create.pid = " + create.pid);
            }

            // ## 检查是否已经存在了该actor, 如果已经存在那么进行stop
            ActorRef ref = getContext().getChild(create.pid);
            if (null != ref) {
                getContext().stop(ref);
            }

            // ## 创建子actor
            Props props = Props.create(PlayerBus.class, create.pid);
            ActorRef bus = context().actorOf(props, create.pid);
            sender().tell(bus, getSelf());

        } else if (message instanceof PlayerHubMessage.Stop) { // 如果是停止子actor

            PlayerHubMessage.Stop stop = (PlayerHubMessage.Stop) message;

            LOG.info("stop.pid = " + stop.pid);

            // ## 停止子actor
            ActorRef ref = getContext().getChild(stop.pid);
            if (null != ref) {
                getContext().stop(ref);
            }
        } else if (message instanceof PlayerHubMessage.Monitor) { // 监控

            if (LOG.isInfoEnabled()) {
                // 显示所有的子actor路径
                Iterator<ActorRef> iterator = getContext().getChildren().iterator();
                while (iterator.hasNext()) {
                    LOG.info("child.path = " + iterator.next().path());
                }
                LOG.info("children.size = " + getContext().children().size());
            }

        } else { // 不处理
            unhandled(message);
        }
    }
}
