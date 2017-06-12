package com.lightfight.game.lang;

import org.junit.Test;

public class IntegerTest {

	/**
	 * Java 字节数组类型(byte[])与int类型互转
	 * http://blog.csdn.net/a19881029/article/details/13511729
	 *
	 */
	@Test
	public void testIntBytes(){

//		System.out.println((byte)386);

		int src = 655885478; // 655885478 = 0010 0111 0001 1000 0000 0100 1010 0110

		System.out.println("src = " + src);
		System.out.println("toBinaryString = " + Integer.toBinaryString(src));

		byte[] bytes = toBytes(src);
		int des = toInt(bytes);

		System.out.println("des = " + des); // 如果打印出来和原来的一样,就表示对了
	}

	/**
	 * 将int转换为byte数组
	 * 将[高字节]存在[低索引]
	 * @param v
	 * @return
	 */
	private byte[] toBytes(int v) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			int shift = (3 - i) * 8;

			// & 0xff的目的是将最低8位保持不变，其余位置为0
			// & 0xff其实是没有必要的，无论高位是多少，最终都会被截去

			bytes[i] = (byte)((v >> shift) & 0xFF);
		}

		return bytes;
	}

	/**
	 * 将byte数组转换为int
	 * 将[低索引]存在[高字节]
	 * @param bytes
	 * @return
	 */
	private int toInt(byte[] bytes) {
		int v = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (3 - i) * 8;

			// 首先会将byte[0]转化为int类型
			// （在位移运算前，会将byte类型转换为int类型，如果为正数，高位补0，如果为负数，高位补1）
			// 可以看到为了保证byte转换成int时，[补位]不对最终a | b | c | d的结果产生影响（置为0），& 0xff是必须的

			v += (bytes[i] & 0xFF) << shift;
			System.out.println("v = " + v);
		}
		return v;
	}



	// 返回具有至多单个 1 位的 int 值，在指定的 int 值中最高位（最左边）的 1 位的位置。
	// 如果指定的值在其二进制补码表示形式中不具有 1 位，即它等于零，则返回零。

	/**
	 *
	 * ## 源代码
	 *
	 * <code>
	 public static int highestOneBit(int i) {
		 // HD, Figure 3-1
		 i |= (i >>  1);
		 i |= (i >>  2);
		 i |= (i >>  4);
		 i |= (i >>  8);
		 i |= (i >> 16);
		 return i - (i >>> 1);
	 }
	 * </code>
	 *
	 * ## 求二进制数i[假设i = 1001 1101 = 157]的最高位的十进制的值v
	 *
	 * 1, 把非最高位全部变为1得到值为a[1111 1111],
	 * 2, a减去a无符号右移1位得到的差值就是v, 即是: v = a - a >>> 1 [v = 1111 1111 - 0111 1111 = 1000 0000]
	 * 备注：[2]也可以改为: a & a无符号右移1位得到的值就是v,
	 * 即是: v = a & (a >>> 1) [v = 1111 1111 & 0111 1111 = 1000 0000]
	 *
	 *
	 * ## 举例
	 *
	 * i = 1001 1101 = 157
	 *
	 * ## 步骤1(步骤列举得要详细些)
	 *
	 * i |= i >>  1
	 *
	 * 等号右边的移位运算
	 *
	 * i >>  1
	 *
	 * 1001 1101		原二进制数据
	 * 01001 1101		向右移动1位,向右移动1位相当于在最前面增加1个0
	 * 0100 1 1101		将前面4位化为一组
	 * 0100 1 110		去掉最后1位(因为向右移动了1位嘛)
	 * 0100 1110		将后面4位化为一组
	 *
	 * i = 0100 1110
	 *
	 * 再将原i与移位后的i进行与运算
	 *
	 * 1001 1101
	 * 0100 1110
	 * ---------
	 * 1101 1101
	 *
	 * 结果：i = 1101 1101
	 * 特征：i的最高2位是1
	 *
	 * ## 步骤2
	 *
	 * i |= i >>  2
	 *
	 * i >>  2
	 * i = 0011 0111
	 *
	 * 1101 1101
	 * 0011 0111
	 * ---------
	 * 1111 1111
	 *
	 * 结果：i = 1111 1111
	 * 特征：i的最高4位是1
	 *
	 * ## 步骤3
	 *
	 * i |= i >>  4
	 *
	 * i >>  4
	 * i = 0000 1111
	 *
	 * 1111 1111
	 * 0000 1111
	 * ---------
	 * 1111 1111
	 *
	 * 结果：i = 1111 1111
	 * 特征：i的最高8位是1
	 *
	 * ## 步骤4
	 *
	 * 类似,只是有一点不同,如果二进制各个位已经全部为1了,那么进行移位前面全部是0,而[ 1|0 = 1]也不改变什么
	 *
	 *
	 */
	@Test
	public void highestOneBit(){
		int value = 129;
		int bit = Integer.highestOneBit(value);
		System.out.println(bit); // {{value,bit},{value,bit},...}={{20,16},{129,128}}
	}
	
	@Test
	public void lowestOneBit(){
		int value = 20;
		int bit = Integer.lowestOneBit(value);
		System.out.println(bit); // {{value,bit},{value,bit},...}={{20,4},{129,1}}
	}
	
	@Test
	public void bitCount(){
		int value = 129;
		int count = Integer.bitCount(value);
		System.out.println(count); // {{value,count},{value,count},...}={{20,2},{129,2}}
	}

}
