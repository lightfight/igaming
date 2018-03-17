package com.lightfight.game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lightfight.game.vo.Fighter;
import com.lightfight.game.vo.QualifyRow;
import org.junit.Test;

import java.util.*;

/**
 * 不好归类的都放在这个类里面
 * 
 * @author deliang
 *
 */
public class GameTest {


	@Test
	public void testNanotime() throws InterruptedException {

		long nanoTime = System.nanoTime(); // 纳秒

		/*

		协调世界时，又称世界标准时间或世界协调时间，简称UTC(Universal Time Coordinated)
		是最主要的世界时间标准，其以原子时秒长为基础，在时刻上尽量接近于格林尼治标准时间

		 */
		long millTime = System.currentTimeMillis();

		System.out.println("nanoTime = " + nanoTime);
		System.out.println("millTime = " + millTime);

		Thread.sleep(5);

		System.out.println(System.nanoTime() - nanoTime);
		System.out.println(System.currentTimeMillis() - millTime);
	}


	@Test
	public void testLastCouple(){

		String master_couple_step = "master:couple:step:";

		Set<String> keys = new HashSet<>();
		keys.add(master_couple_step + 7);
		keys.add(master_couple_step + 8);
		keys.add(master_couple_step + 9);
		keys.add(master_couple_step + 10);

		sortByString(master_couple_step, keys);
		sortByInt(master_couple_step, keys);

	}

	/**
	 * 按照string排序
	 * @param master_couple_step
	 * @param keys
	 */
	private void sortByInt(String master_couple_step, Set<String> keys){
		// ## 取出step的int值
		List<Integer> list = new ArrayList<>();
		int len = master_couple_step.length();
		for (String key : keys) {
			String _step = key.substring(len);
			list.add(Integer.valueOf(_step));
		}

		System.out.println("bef : " + Arrays.toString(list.toArray()));

		// ## 按照step的int值排序
		Collections.sort(list);

		System.out.println("aft : " + Arrays.toString(list.toArray()));

		// ## 刷新最后两个
		int _count = 2;
		int count = Math.min(_count, list.size());
		for (int i = 0; i < count; i++) {
			Integer _step = list.get(list.size() - count + i);
			System.out.println("int step = " + _step);
		}
	}
	/**
	 * 按照string排序
	 * @param master_couple_step
	 * @param keys
	 */
	private void sortByString(String master_couple_step, Set<String> keys){
		// ## 按照string排序
		List<String> list = new ArrayList<>(keys);
		System.out.println("bef : " + Arrays.toString(list.toArray()));

		Collections.sort(list);

		System.out.println("aft : " + Arrays.toString(list.toArray()));

		// ## 刷新最后两个
		int _count = 2;
		int count = Math.min(_count, list.size());
		for (int i = 0; i < count; i++) {
			String key = list.get(list.size() - count + i);
			String _step = key.substring(master_couple_step.length());
			int step = Integer.valueOf(_step);
			System.out.println("string step = " + step);
		}
	}

	@Test
	public void testSublist(){
		int count = 3;
		List<Integer> players = Arrays.asList(1,2,3,4,5,6);

		System.out.println(Arrays.toString(players.toArray()));

		if (players.size() > count){
			players = players.subList(players.size() - count, players.size());
		}

		System.out.println(Arrays.toString(players.toArray()));
	}

	@Test
	public void testClazzName(){
		System.out.println(GameTest.class.getName());
	}

	@Test
	public void testContinueWinCount(){
		List<Integer> masterQualifyBattle = new ArrayList<>();
		masterQualifyBattle.add(-1);
		masterQualifyBattle.add(1);
		masterQualifyBattle.add(1);
		masterQualifyBattle.add(1);

		int count = getMasterQualifyContinueWinCount(masterQualifyBattle);
		System.out.println(count);
	}

	/**
	 * 计算连胜的次数
	 * @param masterQualifyBattle
	 * @return
	 */
	private int getMasterQualifyContinueWinCount(List<Integer> masterQualifyBattle){

		int count = 0;
		for (int i = masterQualifyBattle.size() - 1; i > -1; i--) {
			if (masterQualifyBattle.get(i) == 1) {
				count++;
			} else {
				break;
			}
		}

		return count;
	}


	@Test
	public void testKnockOutCompare(){
		QualifyRow left = new QualifyRow("0601", "apple", 6, 3, 10L);
		QualifyRow right = new QualifyRow("0602", "apple", 6, 0, 10L);

		System.out.println(isLeftWin(left, right));
	}

	/**
	 * 是否左边胜利
	 * @param left
	 * @param right
	 * @return
	 */
	public static boolean isLeftWin(QualifyRow left, QualifyRow right){

		// 比胜利场次
		int c = left.getScore() - right.getScore();

		if (c == 0) {
			c = left.getScore() - right.getScore();
			if (c == 0) { // 比报名时间

				long space = left.getSignUpTime() - right.getSignUpTime();
				c = space == 0 ? 0 : (space > 0 ? -1 : 1);
			}

			if (c == 0) {// 比uuid
				c = left.getUuid().compareTo(right.getUuid()) > 0 ? -1 : 1;
			}
		}

		return c > 0;
	}


	@Test
	public void testSubString(){
		// "grpc@" + serverName + "//" + host + ":" + port;
		String address = "grpc@logic//192.168.1.62:51003";

		int index_0 = address.indexOf("@");
		int index_1 = address.indexOf("//");
		int index_2 = address.indexOf(":");

		String serverName = address.substring(index_0 + 1, index_1);
		String host = address.substring(index_1 + 2, index_2);
		int port = Integer.valueOf(address.substring(index_2 + 1, address.length()));

		System.out.println("serverName = "+ serverName + ", host = " + host + ", port = " + port);
	}

	@Test
	public void testFactorDiamonds(){

		// 128强

		int weightFactor = 3266;
		int signCount = 700;
		int fee = 300;
		int qualify2thKnockOutCount = 292;
		int knockOutCount = 64;

		int diamonds = calKnockOutDiamonds1(weightFactor, signCount, fee, qualify2thKnockOutCount, knockOutCount);


		System.out.println("diamonds = " + diamonds); // 544

	}

	private int calKnockOutDiamonds1(int weightFactor,
									 int signCount,
									 int fee,
									 int qualify2thKnockOutCount,
									 int knockOutCount){

		double expect = signCount * fee * weightFactor * 0.0001D
				* (1 - Math.min(0.29, 0.09 + signCount * 0.0001D)
				- Math.min(fee ,fee * 0.775 + signCount * 0.01D)
				* qualify2thKnockOutCount /(signCount * fee)) / knockOutCount;

		return (int) Math.ceil(expect);
	}

	@Test
	public void testFactorDiamonds2(){

		// 16强

		int weightFactor = 1605;
		int signCount = 500;
		int fee = 300;
		int qualify2thKnockOutCount = 236;
		int knockOutCount = 8;

		int diamonds = calKnockOutDiamonds2(weightFactor, signCount, fee, qualify2thKnockOutCount, knockOutCount);


		System.out.println("diamonds = " + diamonds);

	}

	private int calKnockOutDiamonds2(int weightFactor,
										  int signCount,
										  int fee,
										  int qualify2thKnockOutCount,
										  int knockOutCount){

		double expect = signCount * fee * weightFactor * 0.0001D
				* (1 - Math.min(0.29, 0.11 + signCount * 1D /4000)
				- Math.min(fee ,fee * 0.775 + signCount * 0.01D)
				* qualify2thKnockOutCount /(signCount * fee)) / knockOutCount;

		return (int) Math.ceil(expect);
	}

	@Test
	public void testFactorDiamonds3(){

		// 冠军

		int weightFactor = 1115;
		int signCount = 297;
		int fee = 300;
		int qualify2thKnockOutCount = 146;
		int knockOutCount = 1;

		int diamonds = calKnockOutDiamonds3(weightFactor, signCount, fee, qualify2thKnockOutCount, knockOutCount);


		System.out.println("diamonds = " + diamonds);

	}

	private int calKnockOutDiamonds3(int weightFactor,
									 int signCount,
									 int fee,
									 int qualify2thKnockOutCount,
									 int knockOutCount){

		double expect = signCount * fee * weightFactor * 0.0001D
				* (1 - Math.min(0.29, 0.1 + signCount * 1D /1660)
				- Math.min(fee ,fee * 0.775 + signCount * 0.01D)
				* qualify2thKnockOutCount /(signCount * fee)) / knockOutCount;

		return (int) Math.ceil(expect);
	}

	@Test
	public void testSortRows(){

		List<QualifyRow> list = new ArrayList<>();
		list.add(new QualifyRow("060010", "apple", 6, 100, 7L));
		list.add(new QualifyRow("060010", "apple", 6, 120, 10L));

		Collections.sort(list, QualifyRow.C);

		for (QualifyRow item : list) {
			System.out.println(item);
		}
	}

	@Test
	public void testCompares(){

		// 比较多个
		String left = "0600010";
		String right = "0600012";

		int c = left.compareTo(right);

		System.out.println(c);
	}


	@Test
	public void testOpenStep(){

		// [强数,floorSignUpCount最少需要报名的人数]
		Map<Integer, Integer> stepOpenSignCount = new LinkedHashMap<>();

		stepOpenSignCount.put(128, 600);
		stepOpenSignCount.put(64, 300);
		stepOpenSignCount.put(32, 100);
		stepOpenSignCount.put(16, 50);

		int signCount = 200; // 总报名人数

		int startStep = 0; // 在报名人数限制的条件下开启的开始阶段
		int startStepCount = 0;

		for (Map.Entry<Integer, Integer> item : stepOpenSignCount.entrySet()) {
			int floorSignUpCount = item.getValue();
			if (signCount < floorSignUpCount) { // 需要跳过的
				System.out.println("skip step = " + item.getKey());
			} else { // 寻找最大的step
				if (floorSignUpCount > startStepCount) {
					startStepCount = floorSignUpCount;
					startStep = item.getKey();
				}
			}
		}

		System.out.println("startStep = " + startStep + ", startStepCount = " + startStepCount);

	}

	@Test
	public void testJsonMap(){
		Map<Integer, Integer> map = new LinkedHashMap<>();

		map.put(128, 600);
		map.put(64, 300);
		map.put(32, 100);

		String str = JSON.toJSONString(map);
		System.out.println(str);

		map = JSONObject.parseObject(str, LinkedHashMap.class);
		for (Map.Entry<Integer, Integer> item : map.entrySet()) {
			System.out.println(item.getKey() + " = " + item.getValue());
		}

	}
	
	@Test
	public void getClazzName(){
		String clazzName = getClass().getName();
		System.out.println(clazzName);
		System.out.println(clazzName.substring(clazzName.lastIndexOf('.') + 1, clazzName.length()));
	}
	
	/**
	 * 重写compare看看升序或者降序
	 */
	@Test
	public void compare(){
		List<Fighter> list = new ArrayList<Fighter>();
		
		list.add(new Fighter(100));
		list.add(new Fighter(800));
		list.add(new Fighter(500));
		
		Collections.sort(list, new Comparator<Fighter>() {
			@Override
			public int compare(Fighter o1, Fighter o2) {
				return o1.getFightforce() - o2.getFightforce(); // 降序
//				return o2.getFightforce() - o1.getFightforce(); // 升序
			}
		});
		
		for (Fighter item : list) {
			System.out.println(item.getFightforce());
		}
	}

}
