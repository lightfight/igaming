package com.lightfight.game.algorithm;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.Random;

/**
 * <pre>
 *
 * https://blog.csdn.net/simonchi/article/details/56276878
 *
 * ## snowflake算法
 *
 * 这是twitter的一个id生成算法
 *
 * Twitter-Snowflake算法产生的背景相当简单，为了满足Twitter每秒上万条消息的请求，每条消息都必须分配一条唯一的id，
 * 这些id还需要一些大致的顺序（方便客户端排序），并且在分布式系统中不同机器产生的id必须不同。
 *
 * 首先我们需要一个long类型的变量来保存这个生成的id，第一位固定为0，因为id都是正数嘛，还剩63位，
 * 用x位表示毫秒时间戳，用y位表示进程id，用z位表示同一个时间戳下的序列号，x+y+z=63。我们直接看代码
 *
 * ## SnowFlake算法 64位Long类型生成唯一ID
 *
 * 第一位0，表明正数
 *
 * 2-42，41位，表示毫秒时间戳差值，起始值自定义
 *
 * 43-52，10位，机器编号，5位数据中心编号，5位进程编号
 *
 * 53-64，12位，毫秒内计数器 本机内存生成，性能高
 *
 * 主要就是三部分： 时间戳，进程id，序列号 时间戳41，id10位，序列号12位
 *
 * ## 运用
 *
 * 1. 使用SnowFlake算法得到一个唯一的id
 * 2. 对这个唯一的ID进行Hash算法得到一个字符串s，此处使用guava提供的hash算法
 * 3. 将s转换为一个int值
 *
 * </pre>
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/29/0029 14:20
 */
public class IdGenerator {

    /**
     * 随机数
     */
    private static final Random RANDOM = new Random();

    /**
     * 基础数据
     */
    private final static int[] BASIC_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    private final static long BEGIN_TS = 1483200000000L;

    private long lastTs = 0L;

    private long processId;

    /**
     * 进程ID
     */
    private static final int PROCESS_ID_BITS = 10;

    private static final int SEQUENCE_BITS = 12;

    private long sequence = 0L;

    // 10位进程ID标识
    public IdGenerator(long processId) {
        if (processId > ((1 << PROCESS_ID_BITS) - 1)) {
            throw new RuntimeException("进程ID超出范围，设置位数" + PROCESS_ID_BITS + "，最大"
                    + ((1 << PROCESS_ID_BITS) - 1));
        }
        this.processId = processId;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long ts = timeGen();
        if (ts < lastTs) {// 刚刚生成的时间戳比上次的时间戳还小，出错
            throw new RuntimeException("时间戳顺序错误");
        }
        if (ts == lastTs) {// 刚刚生成的时间戳跟上次的时间戳一样，则需要生成一个sequence序列号
            // sequence循环自增
            sequence = (sequence + 1) & ((1 << SEQUENCE_BITS) - 1);
            // 如果sequence=0则需要重新生成时间戳
            if (sequence == 0) {
                // 且必须保证时间戳序列往后
                ts = nextTs(lastTs);
            }
        } else {// 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0
            sequence = 0L;
        }
        lastTs = ts;// 更新lastTs时间戳
        return ((ts - BEGIN_TS) << (PROCESS_ID_BITS + SEQUENCE_BITS)) | (processId << SEQUENCE_BITS) | sequence;
    }

    private long nextTs(long lastTs) {
        long ts = timeGen();
        while (ts <= lastTs) {
            ts = timeGen();
        }
        return ts;
    }

    /**
     * 生成下一个
     * <p>
     * <pre>
     *
     * 将生成的ID转换为字节数组再转换为数字字符串,因为ID是唯一的所以最后得到的数字字符串也是唯一的
     *
     * ## 数据表现方式
     *
     * id = 164054232880640000
     * hash = 15788272acd18469
     * bytes = [21, 120, -126, 114, -84, -47, -124, 105]
     * fullBytes = 00010101,01111000,10000010,01110010,10101100,11010001,10000100,01101001
     * fullBytes = 0001010101111000100000100111001010101100110100011000010001101001
     * v = 180109625
     * v = 724853018
     * len = 19, builder = 1801096257248530181
     * len = 21, builder = 180109625724853018125
     * 180109625724853018125
     *
     * ## 字节数组转换为int值的字符串
     *
     * 1. 避免负数的出现，所以31位31位的取，取整次，得到字符串s1
     * 2. 将1中剩余的二进制位转换为一个int值，得到字符串s2
     * 3. 如果s1+s2的字符串长度小于最大字符串长度补随机数凑足最大字符串长度，得到字符串s3，该s3就是需要获取的固定长度的唯一的数字字符串。
     *
     *
     * </pre>
     *
     * @return 21位的数字字符串
     */
    public String next() {
        HashFunction hf = Hashing.sipHash24();

        HashCode hash = hf.newHasher().putLong(nextId()).hash();

        String fullBytes = BytesOperation.fullBytes(hash.asBytes(), false);

        return toIntString(fullBytes);
    }

    /**
     * 转换为数字的字符串
     *
     * @param fullBytes 0和1构成的64位长的字符串 {@linkplain Hashing#sipHash24()}
     * @return 21位的数字字符串
     */
    private String toIntString(String fullBytes) {
        StringBuilder builder = new StringBuilder();

        int step = 31; // 每31个取一次

        // ## 取整的部分
        int count = fullBytes.length() / step; // 可以取多少个step

        for (int i = 0; i < count; i++) {
            int v = Integer.parseInt(fullBytes.substring(step * i, step * (i + 1)), 2); // 将二进制的字符串转换为int值
            builder.append(v);
        }

        // ## 取整剩余的部分
        String last = fullBytes.substring(step * count, fullBytes.length());
        int v = Integer.parseInt(last, 2);
        builder.append(v);

        // ## 补足数据的长度为21，用随机数补充
        int emptyLen = 21 - builder.length();
        for (int i = 0; i < emptyLen; i++) {
            builder.append(BASIC_NUMBERS[RANDOM.nextInt(BASIC_NUMBERS.length)]);
        }

        return builder.toString();
    }
}
