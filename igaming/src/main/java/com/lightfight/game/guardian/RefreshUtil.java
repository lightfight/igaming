package com.lightfight.game.guardian;

public class RefreshUtil {

	public static <T extends Refreshor> void refresh(T t){
		System.out.println("refresh...");
	}
}
