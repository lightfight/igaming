package com.lightfight.game.lang;

import org.junit.Test;

public class IntegerTest {
	
	
	// 返回具有至多单个 1 位的 int 值，在指定的 int 值中最高位（最左边）的 1 位的位置。如果指定的值在其二进制补码表示形式中不具有 1 位，即它等于零，则返回零。 
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
