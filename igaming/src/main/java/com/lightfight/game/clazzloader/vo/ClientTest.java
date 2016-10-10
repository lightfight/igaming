package com.lightfight.game.clazzloader.vo;

public class ClientTest {

	public static void main(String[] args) {
		
		// stt 方式一
		SoldierVO vo = new SoldierVO();
		System.out.println(vo.getFightforce());
		
		// stt 方式二
		SoldierHandler handler = new SoldierHandler();
		System.out.println(handler.getFightforce(vo));
	}
}
