package com.game.bytes;

import org.junit.Test;

import java.util.Arrays;

/**
 * <pre>
 *
 *
 * int转换为bytes
 *
 *
 * </pre>
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/20/0020 11:37
 */
public class IntBytesTest {

    @Test
    public void testIntToBytes() {
        int command = 109384;
        System.out.println(command + "=" + fullInt(command));
        byte[] bytes = int2bytes(command);
        for (int i = 0; i < 4; i++) {
            System.out.println(bytes[i]);
        }
        command = bytes2int(bytes);

        System.out.println(command);
    }

    /**
     * 测试异或运算
     */
    @Test
    public void testBitCal() {

        int data1 = 1001;
        System.out.println(" data1 = " + fullInt(data1));

        int random = 109384;
        System.out.println("random = " + fullInt(random));

        int data2 = data1 ^ random;
        System.out.println(" data2 = " + fullInt(data2));

        data1 = data2 ^ random;
        System.out.println(" data1 = " + fullInt(data1));

        /*

        测试数据输出

         data1 = 00000000,00000000,00000011,11101001
        random = 00000000,00000001,10101011,01001000
         data2 = 00000000,00000001,10101000,10100001
         data1 = 00000000,00000000,00000011,11101001

        */
    }

    /**
     * 测试字节数组异或运算
     */
    @Test
    public void testByteArray() {

        byte[] data1 = int2bytes(1001);
        encode(data1, 109384);

        /*
         data1 = 00000000,00000000,00000011,11101001
         data1 = [0, 0, 3, -23]
        random = 109384
        random = 00000000,00000001,10101011,01001000
        random = [0, 1, -85, 72]
         data2 = 01001000,01001000,01001011,10100001
         data2 = [72, 72, 75, -95]
         data1 = 00000000,00000000,00000011,11101001
         data1 = [0, 0, 3, -23]

        */
    }

    @Test
    public void testByteArray3() {

        byte[] data1 = int2bytes(8);
        encode(data1, 3183);
    }

    @Test
    public void testByteArray2() {

        byte[] data1 = {3,14,9,127,-3,-25};
        encode(data1, 109384);

        /*

         data1 = 00000011,00001110,00001001,01111111,11111101,11100111
         data1 = [3, 14, 9, 127, -3, -25]
        random = 109384
        random = 00000000,00000001,10101011,01001000
        random = [0, 1, -85, 72]
         data2 = 01001011,01000110,01000001,00110111,10110101,10101111
         data2 = [75, 70, 65, 55, -75, -81]
         data1 = 00000011,00001110,00001001,01111111,11111101,11100111
         data1 = [3, 14, 9, 127, -3, -25]

        */
    }

    private void encode(byte[] data1, int seed){

        System.out.println(" data1 = " + fullBytes(data1));
        System.out.println(" data1 = " + Arrays.toString(data1));

        System.out.println("random = " + seed);
        System.out.println("random = " + fullInt(seed));
        System.out.println("random = " + Arrays.toString(int2bytes(seed)));

        byte[] data2 = calBytes(data1, seed);
        System.out.println(" data2 = " + fullBytes(data2));
        System.out.println(" data2 = " + Arrays.toString(data2));

        data1 = calBytes(data2, seed);
        System.out.println(" data1 = " + fullBytes(data1));
        System.out.println(" data1 = " + Arrays.toString(data1));
    }

    private void encode(byte[] data1, byte[] seed){

        System.out.println(" data1 = " + fullBytes(data1));
        System.out.println(" data1 = " + Arrays.toString(data1));

        System.out.println("random = " + bytes2int(seed));
        System.out.println("random = " + fullInt(bytes2int(seed)));
        System.out.println("random = " + Arrays.toString(seed));

        byte[] data2 = calBytes(data1, seed);
        System.out.println(" data2 = " + fullBytes(data2));
        System.out.println(" data2 = " + Arrays.toString(data2));

        data1 = calBytes(data2, seed);
        System.out.println(" data1 = " + fullBytes(data1));
        System.out.println(" data1 = " + Arrays.toString(data1));

        /*

         data1 = 00000011,00001110,00001001,01111111,11111101,11100111
         data1 = [3, 14, 9, 127, -3, -25]
        random = 109384
        random = 00000000,00000001,10101011,01001000
        random = [0, 1, -85, 72]
         data2 = 00000011,00001111,10100010,00110111,11111101,11100110
         data2 = [3, 15, -94, 55, -3, -26]
         data1 = 00000011,00001110,00001001,01111111,11111101,11100111
         data1 = [3, 14, 9, 127, -3, -25]

        */
    }

    @Test
    public void testByteArrayByteSeed() {

        byte[] data1 = {3,14,9,127,-3,-25};
        byte[] seed = int2bytes(109384);
        encode(data1, seed);
    }

    /**
     * 使用seed的最后8位
     * @param data
     * @param seed
     * @return
     */
    private byte[] calBytes(byte[] data, int seed){

        // 对byte数组的每个byte进行异或运算
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(data[i] ^ seed & 0xff);
        }

        return data;
    }

    /**
     * [循环seed的每一个字节]对[data的每一个字节]进行异或运算
     *
     * <pre>
     *
     *    data1 = 00000011,00001110,00001001,01111111,11111101,11100111
          data1 = [3, 14, 9, 127, -3, -25]

         random = 109384
         random = 00000000,00000001,10101011,01001000
         random = [0, 1, -85, 72]

          data2 = 00000011,00001111,10100010,00110111,11111101,11100110
          data2 = [3, 15, -94, 55, -3, -26]

          data1 = 00000011,00001110,00001001,01111111,11111101,11100111
          data1 = [3, 14, 9, 127, -3, -25]
     *
     * </pre>
     *
     * @param data
     * @param seed
     * @return
     */
    private byte[] calBytes(byte[] data, byte[] seed){

        // 对byte数组的每个byte进行异或运算
        for (int i = 0; i < data.length; i++) {
            // 0x3 = 0011 = seed.length - 1，让每一个与其&的数保留二进制的最后两位
            data[i] = (byte)(data[i] ^ seed[i & 0x3] & 0xff);
        }

        return data;
    }

    /**
     * 将一个int类型的数表示为一个32位的二进制数字符串
     *
     * @param v int类型的值
     * @return 32位的二进制数字符串
     */
    private String fullInt(int v) {
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
     *
     * @param bytes
     * @return
     */
    private String fullBytes(byte[] bytes){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {

            b.append(fullByte(bytes[i]));
            if (i < bytes.length - 1) {
                b.append(",");
            }
        }
        return b.toString();
    }

    /**
     * 将一个byte类型的数表示为一个8位的二进制数字符串
     * @param v byte类型的值
     * @return 8位的二进制数字符串
     */
    private String fullByte(byte v){
        StringBuilder b = new StringBuilder();

        String str = Integer.toBinaryString(v & 0xff);
        int count = 8 - str.length();
        for (int i = 0; i < count; i++) {
            b.append("0");
        }
        b.append(str);

        return b.toString();
    }


    //高位在前，低位在后
    public byte[] int2bytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);//说明一
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    //高位在前，低位在后
    public int bytes2int(byte[] bytes) {
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

    @Test
    public void genSeed(){

        String str = "ChessChessChessChessChessChessCh";
        char[] chars = str.toCharArray();

        int seed = 0;
        for (char c : chars) {
            seed += c;
        }
        System.out.println("seed = " + seed);

        // seed = 3183
    }

    /**
     * 测试将一个int数强转为一个byte数，直接舍弃高24位，只保留低8位
     *
     * <pre>
     *
     *
         gold = 992922
         00000000,00001111,00100110,10011010
         g = -102
         10011010
     *
     * </pre>
     */
    @Test
    public void testIntByte(){

        int gold = 992922;
//        int gold = 125;
        System.out.println("gold = " + gold);
        System.out.println(fullInt(gold));

        byte g = (byte)gold;
        System.out.println("g = " + g);
        System.out.println(fullByte(g));
    }
}
