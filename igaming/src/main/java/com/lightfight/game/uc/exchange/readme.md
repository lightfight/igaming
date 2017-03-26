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
> java.util.concurrent.LinkedBlockingQueue

## v4
直接使用LinkedBlockingQueue
一个生产者,一个仓库,一个消费者

## v5
多个生产者,一个仓库,多个消费者

## 测试表格
DruidDataSource支持哪些数据库？
理论上说，支持所有有jdbc驱动的数据库。实际测试过的有
<table>
    <tr>
        <td>数据库</td>
        <td>支持状态</td>
    </tr>
    <tr>
        <td>mysql</td>
        <td>支持，大规模使用</td>
    </tr>
    <tr>
        <td>oracle</td>
        <td>支持，大规模使用</td>
    </tr>
    <tr>
        <td>sqlserver</td>
        <td>支持</td>
    </tr>
    <tr>
        <td>postgres</td>
        <td>支持</td>
    </tr>
    <tr>
        <td>db2</td>
        <td>支持</td>
    </tr>
    <tr>    
        <td>h2</td>
        <td>支持</td>
    </tr>
    <tr>
        <td>derby</td>
        <td>支持</td>
    </tr>
    <tr>
        <td>sqlite</td>
        <td>支持</td>
    </tr>
    <tr>
        <td>sybase</td>
        <td>支持</td>
    </tr>
</table>

<table>
<tr>
  <td>姓名</td>
  <td>Bill Gates</td>
</tr>
<tr>
  <td rowspan="2">电话</td>
  <td>555 77 854</td>
</tr>
<tr>
  <td>555 77 855</td>
</tr>
</table>

## 有多个分类的情况,主要使用**rowspan**
感觉还是把标签一行一行的写可读性更高
<table>
    <tr><td>部位</td><td>属性类型</td><td>属性值</td></tr>
    <tr><td rowspan="2">1</td><td>1</td><td>10</td></tr>
    <tr><td>2</td><td>20</td></tr>
    <tr><td rowspan="2">2</td><td>1</td><td>30</td></tr>
    <tr><td>3</td><td>40</td></tr>
    <tr><td rowspan="2">3</td><td>1</td><td>50</td></tr>
    <tr><td>4</td><td>60</td></tr>
</table>

