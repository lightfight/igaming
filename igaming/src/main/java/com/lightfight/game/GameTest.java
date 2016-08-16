package com.lightfight.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.lightfight.game.vo.Fighter;

/**
 * 不好归类的都放在这个类里面
 * 
 * @author deliang
 *
 */
public class GameTest {
	
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
