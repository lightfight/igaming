package com.lightfight.game.algorithm;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

public class SelectSort {

	@Test
	public void testSort() {
		int[] arr = { 52, -58, 8, -49, 45, -53, 9, 56, -1, -17 };
		System.out.println("init = " + Arrays.toString(arr));
		
		sort(arr);
		System.out.println("sort = " + Arrays.toString(arr));
	}

	public void sort(int[] arr) {

		for (int turnIndex = 0; turnIndex < arr.length; turnIndex++) { // 轮的序数
			int maxIndex = 0; // 一轮中最大的序数
			for (int selectIndex = 0; selectIndex < arr.length - turnIndex; selectIndex++) { // 一轮中选择的序数
				if (arr[selectIndex] > arr[maxIndex]) { // 如果选择的那个数比当前最大的还要大,那么重设最大
					maxIndex = selectIndex;
				}
			}
			swap(arr, maxIndex, arr.length - 1 - turnIndex); // 将一轮中找到的最大数放在一轮中的最后一个
		}
	}

	private void swap(int[] arr, int oneIndex, int anotherIndex) {
		int tempValue = arr[oneIndex];
		arr[oneIndex] = arr[anotherIndex];
		arr[anotherIndex] = tempValue;
	}
	
	@Test
	public void testTreeMap(){
		TreeMap<String, String> data = new TreeMap<>();
		data.put("appid", "a");
		data.put("attach", "支付测试attach");
		data.put("body", "JSAPI支付测试");
		data.put("mch_id", "mch");
		
		for (Entry<String, String> item : data.entrySet()) {
			System.out.println(item.getKey());
		}
	}

}
