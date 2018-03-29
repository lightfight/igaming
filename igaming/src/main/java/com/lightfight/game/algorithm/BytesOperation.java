package com.lightfight.game.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * <pre></pre>
 *
 * 字节数组的操作类
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/20/0020 16:54
 */
public abstract class BytesOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(BytesOperation.class);

    /**
     * [循环seed的每一个字节]对[data的每一个字节]进行异或运算
     * <p>
     * <pre>
     *
     * data1 = 00000011,00001110,00001001,01111111,11111101,11100111
     * data1 = [3, 14, 9, 127, -3, -25]
     *
     * seed = 2004318079
     * seed = 01110111,01110111,01110111,01111111
     * seed = [119, 119, 119, 127]
     *
     * data2 = 01110100,01111001,01111110,00000000,10001010,10010000
     * data2 = [116, 121, 126, 0, -118, -112]
     *
     * data1 = 00000011,00001110,00001001,01111111,11111101,11100111
     * data1 = [3, 14, 9, 127, -3, -25]
     *
     * </pre>
     *
     * @param data
     * @param seed
     * @return
     */
    public static byte[] xor(byte[] data, byte[] seed) {

        // 创建一个新的对象，避免在服务器广播的时候，引用同一份变量
        byte[] d = new byte[data.length];

        // 对byte数组的每个byte进行异或运算
        for (int i = 0; i < data.length; i++) {
            // 3 = 0011 = seed.length - 1，让每一个与其&的数保留二进制的最后两位
            d[i] = (byte) (data[i] ^ seed[i & 0x3] & 0xff);
        }

        return d;
    }

    /**
     * <pre>
     * 获取seed
     * 将每一个char向左移动0,8,16,24位再进行累计或运算,让int数的每个字节都可能分布到0和1
     * </pre>
     *
     * @param key 唯一的KEY
     * @return seed
     */
    public static byte[] seed(String key) {
        char[] chars = key.toCharArray();
        int seed = 1;
        for (int i = 0; i < chars.length; i++) {
            seed |= chars[i] << ((i & 3) * 8);
        }

        return int2bytes(seed);
    }

    /**
     * 高位在前，低位在后
     *
     * @param num
     * @return
     */
    public static byte[] int2bytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);//说明一
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    /**
     * 高位在前，低位在后
     *
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 24;//说明二
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    /**
     * 调试数据
     *
     * @param data 数据
     * @param seed seed
     */
    public static String debug(byte[] data, byte[] seed) {

        StringBuilder log = new StringBuilder();

        log.append("\n");
        log.append("data1 = ").append(fullBytes(data)).append("\n");
        log.append("data1 = ").append(Arrays.toString(data)).append("\n\n");

        log.append(" seed = ").append(bytes2int(seed)).append("\n");
        log.append(" seed = ").append(fullInt(bytes2int(seed))).append("\n");
        log.append(" seed = ").append(Arrays.toString(seed)).append("\n\n");

        // ## encode
        data = xor(data, seed);

        log.append("data2 = ").append(fullBytes(data)).append("\n");
        log.append("data2 = ").append(Arrays.toString(data)).append("\n\n");

        // ## decode
        data = xor(data, seed);

        log.append("data1 = ").append(fullBytes(data)).append("\n");
        log.append("data1 = ").append(Arrays.toString(data)).append("\n");

        return log.toString();
    }

    /**
     * 用0充满每个字节
     *
     * @param bytes 字节数组
     * @return 字节数组的二进制字符串
     */
    public static String fullBytes(byte[] bytes) {
        return fullBytes(bytes, true);
    }

    /**
     * 是否需要分隔
     * @param bytes
     * @param split
     * @return
     */
    public static String fullBytes(byte[] bytes, boolean split) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {

            b.append(fullByte(bytes[i]));
            if (split && i < bytes.length - 1) {
                b.append(",");
            }
        }
        return b.toString();
    }

    /**
     * 将一个int类型的数表示为一个32位的二进制数字符串
     *
     * @param v int类型的值
     * @return 32位的二进制数字符串
     */
    public static String fullInt(int v) {
        StringBuilder b = new StringBuilder();

        String str = Integer.toBinaryString(v);
        int count = 32 - str.length();
        for (int i = 0; i < count; i++) {
            b.append("0");
        }
        b.append(str);

        // 每8位加1个逗号
        for (int i = 0; i < 3; i++) {
            b.insert((i + 1) * 8 + i, ",");
        }
        return b.toString();
    }

    /**
     * 将一个byte类型的数表示为一个8位的二进制数字符串
     *
     * @param v byte类型的值
     * @return 8位的二进制数字符串
     */
    public static String fullByte(byte v) {
        StringBuilder b = new StringBuilder();

        String str = Integer.toBinaryString(v & 0xff);
        int count = 8 - str.length();
        for (int i = 0; i < count; i++) {
            b.append("0");
        }
        b.append(str);

        return b.toString();
    }
}
