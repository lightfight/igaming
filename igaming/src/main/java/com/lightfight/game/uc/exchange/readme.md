# 版本说明

通过[生产者-队列-消费者]模型来实现两个线程交替的执行

## v0

当[Storage.LEN = 1]时生产者放一个东西进去,然后消费者拿一个东西,从而实现两者的交替执行

可以修改LEN的值来观察效果

## v1
将TimeUnit.SECONDS.sleep放在put和take外面,和v0进行对比

## v2
加入具体的数据到queue中,可以更好的观察先进先出

**以上是"一个lock产生两个condition"对一个队列进行lock，然后通过该lock产生的两个condition[notFull,notEmpty]进行协作**

## v3
两个lock[putLock,takeLock]，每个lock产生一个condition[putLock对应notFull,takeLock对应notEmpty]
>java.util.concurrent.LinkedBlockingQueue

## v4
直接使用LinkedBlockingQueue
一个生产者,一个仓库,一个消费者

## v5
多个生产者,一个仓库,多个消费者

