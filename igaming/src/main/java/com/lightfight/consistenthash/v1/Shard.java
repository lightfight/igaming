package com.lightfight.consistenthash.v1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * 我的JAVA日记（十）关于TreeMap和一致性hash
 * ref：https://zhuanlan.zhihu.com/p/20270435
 *
 *
 */
class Shard<S> { // S类封装了机器节点的信息 ，如name、password、ip、port等

    private TreeMap<Long, S> nodes = new TreeMap<>(); // 虚拟节点
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
            for (int n = 0; n < NODE_NUM; n++){ // 一个真实机器节点关联NODE_NUM个虚拟节点
                long key = hash("SHARD-" + i + "-NODE-" + n);
                System.out.printf("i = %s, key = %s \n", i, key);
                nodes.put(key, shardInfo);
            }
        }
    }

    public S getShardInfo(String key) {

//        SortedMap<Long, S> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
//        if (tail.size() == 0) {
//            return nodes.get(nodes.firstKey());
//        }
//        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息

        // 以下是dirk的修改
        //返回一个键-值映射关系，它与严格大于给定键的最小键关联；如果不存在这样的键，则返回 null
        Long nodeKey = nodes.higherKey(hash(key));
        if (nodeKey == null) { // 如果没有比这个更大的了,那么就返回第一个
            nodeKey = nodes.firstKey();
        }

        return nodes.get(nodeKey);
    }

    /**
     *  MurMurHash算法，是非加密HASH算法，性能很高，
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     *  http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

}
