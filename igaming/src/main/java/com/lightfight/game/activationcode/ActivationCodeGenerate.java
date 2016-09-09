package com.lightfight.game.activationcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * 模仿苹果手机调时间那种转盘方式<BR>
 * 生成的时候是有序的,这个时候为每一个code加一个随机的ID,然后再根据ID排序,然后依次取就是随机的code了;
 * 
 * @author deliang
 *
 */
public class ActivationCodeGenerate {
	
	Random random = new Random();

	@Test
	public void generate() {
		
		long stt = System.currentTimeMillis();

		// disk=转盘,每一个一维数组就是一个转盘
		char[][] diskChars = {
				{ '3', '4', '5', '6', '7', '8', '9', 'A','C','D','E','F','G','H','K','L','M','P','R','T','X','Y' },
				{ '3', '4', '5', '6', '7', '8', '9', 'A','C','D','E','F','G','H','K','L','M','P','R','T','X','Y' },
				{ '3', '4', '5', '6', '7', '8', '9', 'A','C','D','E','F','G','H','K','L','M','P','R','T','X','Y' },
				{ '3', '4', '5', '6', '7', '8', '9', 'A','C','D','E','F','G','H','K','L','M','P','R','T','X','Y' },
		};

		// stt
		int genCount = 1; // 一共生成的个数
		for (int i = 0; i < diskChars.length; i++) {
			genCount *= diskChars[i].length;
		}
		
		List<Integer> indexes = new ArrayList<Integer>(); // 随机不重复生成ID,用来做插入数据库的ID
		for (int index = 0; index < genCount; index++) {
			indexes.add(index + 1);
		}
		
		// stt
		int[] diskCharCount = new int[diskChars.length]; // 每个转盘一共拥有字符的个数

		// stt
		List<CodePO> codes = new ArrayList<CodePO>();
		for (int countIndex = 0; countIndex < genCount; countIndex++) { // 一共要生成这么多个

			StringBuilder builder = new StringBuilder();
			
			
			for (int diskIndex = 0; diskIndex < diskChars.length; diskIndex++) { // 转盘序数

				int charIndex = diskCharCount[diskIndex];
				builder.append(diskChars[diskIndex][charIndex]);
			}

			check(diskChars, diskCharCount, diskCharCount.length - 1);

			int dbid = indexes.remove(random.nextInt(indexes.size()));
			CodePO po = new CodePO(dbid, builder.toString());
			codes.add(po);
		}
		
		Collections.sort(codes, new Comparator<CodePO>() {

			@Override
			public int compare(CodePO o1, CodePO o2) {
				return o1.getId() - o2.getId();
			}
		});
		
		for (CodePO code : codes) {
			System.out.println(code);
		}
		
		System.out.println("genCount = " + genCount);
		System.out.println("cost millis = " + (System.currentTimeMillis() - stt));
	}

	/**
	 * 递归检查,进位运算,如果一个转盘的格转满了就需要前面一个加1格
	 * 
	 * @param diskChars
	 * @param diskCharCount
	 * @param diskCheckIndex
	 */
	public void check(char[][] diskChars, int[] diskCharCount, int diskCheckIndex) {
		if (diskCharCount[diskCheckIndex] + 1 < diskChars[diskCheckIndex].length) { // 如果转动一格还是小于该盘的最大数,那么就转动一格
			diskCharCount[diskCheckIndex]++;
		} else {
			diskCharCount[diskCheckIndex] = 0; // 自己复原到0
			if (diskCheckIndex > 0) { // 如果前面还有数据,递归检查前面的一个
				check(diskChars, diskCharCount, diskCheckIndex - 1);
			}
		}
	}
}
