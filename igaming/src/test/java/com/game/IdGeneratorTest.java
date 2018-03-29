package com.game;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.lightfight.game.algorithm.BytesOperation;
import com.lightfight.game.algorithm.IdGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * <pre></pre>
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/29/0029 14:44
 */
public class IdGeneratorTest {

    private static final Random RANDOM = new Random();
    private final static int[] BASIC_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * 仅仅测试生成的ID
     */
    @Test
    public void idGenerate() {
        IdGenerator generator = new IdGenerator(1023);
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.nextId());
        }
    }

    /**
     * 将生成的ID进行多种形式的呈现
     */
    @Test
    public void hashID() {

        IdGenerator generator = new IdGenerator(1023);
        HashFunction hf = Hashing.md5();

        System.out.println("id = " + generator.nextId());

        HashCode hashCode = hf.newHasher().putLong(generator.nextId()).hash();

        System.out.println("str = " + hashCode.toString());
        System.out.println("int = " + hashCode.asInt());
        System.out.println("long = " + hashCode.asLong());
        System.out.println("bytes = " + Arrays.toString(hashCode.asBytes()));
    }

    /**
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
     */
    @Test
    public void id2Bytes2NumberStr() {

        IdGenerator generator = new IdGenerator(1023);
//        HashFunction hf = Hashing.md5();
        HashFunction hf = Hashing.sipHash24();

        System.out.println("id = " + generator.nextId());

        HashCode hashCode = hf.newHasher().putLong(generator.nextId()).hash();

        System.out.println("hash = " + hashCode.toString());

        byte[] bytes = hashCode.asBytes();
        System.out.println("bytes = " + Arrays.toString(bytes));
        System.out.println("fullBytes = " + BytesOperation.fullBytes(bytes));

        String fullBytes = BytesOperation.fullBytes(bytes, false);
        System.out.println("fullBytes = " + fullBytes);

        System.out.println(toIntString(fullBytes));

        /*

        id = 164044283731505152
        hash = fef39e6b675d6b83
        bytes = [-2, -13, -98, 107, 103, 93, 107, -125]
        fullBytes = 11111110,11110011,10011110,01101011,01100111,01011101,01101011,10000011
        fullBytes = 1111111011110011100111100110101101100111010111010110101110000011
        21386893331507285728

        */

    }

    /**
     * 转换为数字的字符串
     *
     * @param fullBytes
     * @return
     */
    private String toIntString(String fullBytes) {
        StringBuilder builder = new StringBuilder();

        int step = 31; // 每31个取一次

        // ## 取整的部分
        int count = fullBytes.length() / step; // 可以取多少个step

        for (int i = 0; i < count; i++) {
            int v = Integer.parseInt(fullBytes.substring(step * i, step * (i + 1)), 2); // 将二进制的字符串转换为int值

            System.out.println("v = " + v);

            builder.append(v);
        }

        // ## 取整剩余的部分
        String last = fullBytes.substring(step * count, fullBytes.length());
        int v = Integer.parseInt(last, 2);
        builder.append(v);

        System.out.println("len = " + builder.length() + ", builder = " + builder.toString());

        // ## 补足数据的长度，用随机数补充
        int emptyLen = 21 - builder.length();
        for (int i = 0; i < emptyLen; i++) {
            builder.append(BASIC_NUMBERS[RANDOM.nextInt(BASIC_NUMBERS.length)]);
        }

        System.out.println("len = " + builder.length() + ", builder = " + builder.toString());

        return builder.toString();
    }

    @Test
    public void testStr2Int() {

        String str = "01011110000010000111101100000111";
        System.out.println(Integer.parseInt(str, 2));
    }

    @Test
    public void testMaxIntString() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            builder.append(1);
        }

        String str = toIntString(builder.toString());
        System.out.println(str.length());
        System.out.println(str);

        /*

        最大21位长，刚刚满足生活中的需要

        21
        214748364721474836473

        */
    }
}
