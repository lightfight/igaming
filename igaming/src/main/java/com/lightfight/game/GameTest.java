package com.lightfight.game;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lightfight.game.vo.QualifyRow;
import org.junit.Test;

import com.lightfight.game.vo.Fighter;

/**
 * 不好归类的都放在这个类里面
 * 
 * @author deliang
 *
 */
public class GameTest {

	@Test
	public void testSortRows(){

		List<QualifyRow> list = new ArrayList<>();
		list.add(new QualifyRow("060010", "apple", 6, 100, 10L));
		list.add(new QualifyRow("060020", "apple", 6, 100, 10L));

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
	public void testMax(){
		Map<Integer, Integer> map = new LinkedHashMap<>();

		map.put(128, 600);
		map.put(64, 300);
		map.put(32, 100);

		int signupCount = 200;
		int nextStepCount = 0;
		for (Map.Entry<Integer, Integer> item : map.entrySet()) {
			if (signupCount > item.getKey() && nextStepCount < item.getValue()) {
				nextStepCount = item.getValue();
			}
		}

		System.out.println("nextStepCount = " + nextStepCount);

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
