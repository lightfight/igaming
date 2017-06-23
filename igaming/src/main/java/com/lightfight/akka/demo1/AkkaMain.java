package com.lightfight.akka.demo1;

import akka.actor.*;
import com.lightfight.akka.demo1.actor.GreetPrinter;
import com.lightfight.akka.demo1.actor.Greeter;
import com.lightfight.akka.demo1.model.Greet;
import com.lightfight.akka.demo1.model.Greeting;
import com.lightfight.akka.demo1.model.WhoToGreet;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 *
 * http://sunxiang0918.cn/2016/01/10/Akka-in-JAVA-1/
 *
 * @author SUN
 * @version 1.0
 * @Date 16/1/6 21:39
 */
public class AkkaMain {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("helloakka");

        // 创建一个到greeter Actor的管道
        final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");

        System.out.println("greeter.path() = " + greeter.path());
        
        final ActorSelection selection = system.actorSelection("akka://helloakka/user/greeter");
        selection.tell(new WhoToGreet("akka"), ActorRef.noSender());

        // 创建邮箱
        final Inbox inbox = Inbox.create(system);

        // 先发第一个消息,消息类型为WhoToGreet
//        greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

        // 真正的发送消息,消息体为Greet
        inbox.send(greeter, new Greet());

        // 等待5秒尝试接收Greeter返回的消息
        Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("greeting1: " + greeting1.message);

        // 发送第三个消息,修改名字
        greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());
        // 发送第四个消息
        inbox.send(greeter, new Greet());
        
        // 等待5秒尝试接收Greeter返回的消息
        Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("greeting2: " + greeting2.message);

        // 新创建一个Actor的管道
        ActorRef greetPrinter = system.actorOf(Props.create(GreetPrinter.class), "greetPrinter");
        System.out.println("greetPrinter.path() = " + greetPrinter.path());
        
        //使用schedule 每一秒发送一个Greet消息给 greeterActor,然后把greeterActor的消息返回给greetPrinterActor
        /*
         * 只有最后一个代码稍微有点不一样system.scheduler().schedule(Duration.Zero(),
         * Duration.create(1, TimeUnit.SECONDS),
         * greeter,
         * new Greet(),
         * system.dispatcher(),
         * greetPrinter);
         * 这个使用了ActorSystem中的调度功能.每一秒钟给greeter这个Actor发送一个Greet消息,
         * 并指定消息的发送者是greetPrinter.这样每隔一秒钟,greeter就会收到Greet消息,
         * 然后构造成Greeting消息,又返回给GreetPrinter这个Actor,这个Actor接收到消息后,打印出来.形成一个环流.
         */
        system.scheduler().schedule(
                Duration.Zero(),
                Duration.create(1, TimeUnit.SECONDS),
                greeter,
                new Greet(),
                system.dispatcher(),
                greetPrinter);
        //system.shutdown();


        // 寻找这个actor的所有孩子
        final ActorSelection allSelection = system.actorSelection("akka://helloakka/user/*");
        System.out.println(" allSelection = "+  allSelection.toString());
//        IndexedSeq<SelectionPathElement> pathes = allSelection.path();
//        List<SelectionPathElement> list = pathes.toList();
//        for (SelectionPathElement item : list.s) {
//
//        }
//        system.provider().guardian().actorContext().children().i
        System.out.println("allSelection.length() = " + allSelection.path().length());
    }
}
