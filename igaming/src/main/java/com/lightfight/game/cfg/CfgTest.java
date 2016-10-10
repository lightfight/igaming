package com.lightfight.game.cfg;

import java.lang.reflect.Field;

public class CfgTest {

	/**
	 * 测试类
	 *  
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Field[] fields = SoldierCfg.class.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getName() + " = " + field.getType());
		}
		
		Field field = SoldierCfg.class.getSuperclass().getDeclaredField("id");
		System.out.println(field.getName());
		
		
//		String[] strs = "1|2".split("\\|"); // 需要使用转义符号
		String[] strs = "1;2".split("\\;");
		System.out.println(strs);
	}
}
