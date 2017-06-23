package com.lightfight.akka.demo1.player;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/6/15/0015
 */
public class PlayerBus extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerBus.class);

    private final String pid;

    public PlayerBus(String pid) {
        this.pid = pid;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        try {
            handle(message);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        /*

        关于这里为什么需要进行try-catch捕获最顶层的异常Exception
        1.akka是树形结构的,出现异常会上抛
        2.某个节点捕获了异常后,会kill掉子节点,然后再创建子节点(有点类似养女不读书不如养头猪),这会导致问题:
            2.1.丢失队列数据：子actor中未处理完的消息会丢失,造成业务逻辑不完整
            2.2.丢失类属性数据：子actor初始化的时候设置的属性值也会被丢失

         */

    }

    /**
     * 独立出来,让上面的try-catch看起来简洁些
     * @param message
     */
    private void handle(Object message){
        //        TimeUnit.SECONDS.sleep(1); // 让玩家处理消息有1秒的延迟,检查是否有序

        if (message instanceof PlayerBusMessage.Intranet) {
            PlayerBusMessage.Intranet m = (PlayerBusMessage.Intranet)message;

            // ## 调用业务逻辑处理类
            if (LOG.isInfoEnabled()) {
                LOG.info("pid = {}, key = {}", pid, m.key);
            }

        } else if (message instanceof PlayerBusMessage.Extranet) {
            PlayerBusMessage.Extranet m = (PlayerBusMessage.Extranet)message;

            // ## 调用业务逻辑处理类
            if (LOG.isInfoEnabled()) {
                LOG.info("pid = {}, key = {}", pid, m.key);
            }

        } else { // 不处理
            unhandled(message);
        }
    }
}
