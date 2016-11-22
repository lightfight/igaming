package com.lightfight.game.util;

import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

public class TestTreeMap {

	/**
	 * 比较ceilingEntry和higherEntry
	 */
	@Test
	public void testTreeMap() {
		TreeMap<Integer, Integer> map = new TreeMap<>();

		map.put(1, 10);
		map.put(10, 100);
		map.put(20, 200);

		// Entry<Integer, Integer> item = map.ceilingEntry(20); // 20 = 200
		Entry<Integer, Integer> item = map.higherEntry(20); // 未获取到Entry

		if (item != null) {
			System.out.println(item.getKey() + " = " + item.getValue());
		} else {
			System.out.println("未获取到Entry");
		}
	}
}
