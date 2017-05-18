package com.lightfight.consistenthash.v2;

import java.util.List;
import java.util.TreeMap;

/**
 *
 * 我的JAVA日记（十）关于TreeMap和一致性hash
 * ref：https://zhuanlan.zhihu.com/p/20270435
 *
 *
 */
class Shard<S> { // S类封装了机器节点的信息 ，如name、password、ip、port等

    private TreeMap<Integer, S> virtualNodes = new TreeMap<>(); // 虚拟节点
    private List<S> shards; // 真实机器节点
    private final int NODE_NUM = 10; // 每个机器节点关联的虚拟节点个数

    public Shard(List<S> shards) {
        super();
        this.shards = shards;
        init();
    }

    private void init() { // 初始化一致性hash环
        for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final S shardInfo = shards.get(i);
            ShardInfo realNode = (ShardInfo)shardInfo; // 强转为可以读取的对象,做测试
            for (int n = 0; n < NODE_NUM; n++){ // 一个真实机器节点关联NODE_NUM个虚拟节点

                // 虚拟节点上放的是真实的节点,虚拟节点只是为了分布更为均匀
                int virtualNodeKey = realNode.id * 1000 + n * 100; // 构建可人为计算的key,方便测试
                virtualNodes.put(virtualNodeKey, shardInfo);

                System.out.printf("i = %s, key = %s , info = %s\n", i + 1, virtualNodeKey, realNode);
            }
        }
    }

    public S getShardInfo(int key) {
        Integer nodeKey = virtualNodes.higherKey(key);
        if (nodeKey == null) { // 如果没有比这个更大的了,那么就返回第一个
            nodeKey = virtualNodes.firstKey();
        }

        return virtualNodes.get(nodeKey);
    }

    // TODO 增加真实节点方法

    // TODO 删除真实节点方法

}
