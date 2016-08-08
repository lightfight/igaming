package com.lightfight.game.mvc;

public class CO {
	
	static MO mo = new MO();

	public CO(){
		System.out.println(getClass().getName());
	}
	
	public void create(String pname){
		System.out.println(getClass().getName() + " = " + pname);
	}
}
