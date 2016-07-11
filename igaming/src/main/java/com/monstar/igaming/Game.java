package com.monstar.igaming;

import org.junit.Test;

import com.monstar.igaming.vo.HeroVO;
import com.monstar.igaming.vo.MonsterVO;

public class Game {

	/**
	 * 看看有参构造方法会不会调用无参数构造方法
	 */
	@Test
	public void testConstruction(){
		HeroVO vo = new HeroVO(0, "google"); // 不会调用自己的无参构造方法
		System.out.println(vo.getName());
		
		System.out.println("---------");
		
		MonsterVO mvo = new MonsterVO(100); // 会调用父类的无参构造方法
		System.out.println(mvo.getSkill());
	}
	
	@Test
	public void getClazzName(){
		System.out.println(MonsterVO.class);
	}
}
