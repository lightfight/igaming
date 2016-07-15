package com.monstar.igaming.algorithm;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author dirk
 *	ref:http://developer.51cto.com/art/201403/430986.htm
 */
public class QuickSort {

	@Test
	public void testSort() {
		int[] arr = { 6, 1, 2, 7, 9, 3, 4, 5, 10, 8 };
		System.out.println("init = " + Arrays.toString(arr));

		sort(arr, 0, arr.length - 1);
		System.out.println("sort = " + Arrays.toString(arr));
	}

	/**
	 * 递归分区排序
	 * @param arr
	 * @param left
	 * @param right
	 */
	public void sort(int[] arr, int left, int right){
		if (left < right) { // 是判断条件也是结束条件
			int pivotIndex = partition(arr, left, right);
			sort(arr, 0, pivotIndex - 1); // 小于基准数的左边数据再次进行类似的操作
			sort(arr, pivotIndex + 1, right); // 大于基准数的右边数据再次进行类似的操作
		}
	}
	
	/**
	 * 以基准数分区,分左小右大
	 * 
	 * @param arr
	 * @param left 左边哨兵
	 * @param right 右边哨兵
	 * @return 一轮左小右大后分区后,作为基准数的数组序数
	 */
	public int partition(int[] arr, int left, int right) {
		int pivotIndex = left;
		int pivot = arr[pivotIndex];
		while (left < right) { // 只要左右两个哨兵没有相遇,就继续寻找

			// 右边哨兵移动
			// 如果左右两个哨兵没有相遇,而且右边哨兵数大于基准数,那么继续向左移动
			while (left < right && arr[right] >= pivot) {
				right--;
			}

			// 左边哨兵移动
			// 如果左右两个哨兵没有相遇,而且左边哨兵数小于基准数,那么继续向右移动
			while (left < right && arr[left] <= pivot) {
				left++;
			}

			if (left < right) { // 如果左右还是没有相遇,那么交换数据
				swap(arr, left, right);
			}
		}
		
		// 必然在(left == right)的时候结束循环
		
		// 分区结束后,交换此次被当作基准数的数序和分界点的序数对应的数
		swap(arr, pivotIndex, left);

		return left;
	}

	private void swap(int[] arr, int oneIndex, int anotherIndex) {
		int tempValue = arr[oneIndex];
		arr[oneIndex] = arr[anotherIndex];
		arr[anotherIndex] = tempValue;
	}
}
