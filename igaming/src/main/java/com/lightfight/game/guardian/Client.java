package com.lightfight.game.guardian;

import java.util.ArrayList;
import java.util.List;

public class Client {

	public static void main(String[] args) {
		
		List<Refreshor> list = new ArrayList<Refreshor>();
		
		list.add(new Hero());
		list.add(new Soldier());
		
		for (Refreshor item : list) {
			item.refresh();
		}
		
		//
		
	}

}
