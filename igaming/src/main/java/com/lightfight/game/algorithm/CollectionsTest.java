package com.lightfight.game.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class CollectionsTest {

	@Test
	public void binarySearch(){
		
		Comparator<EndlessCfg> comparator = new Comparator<EndlessCfg>() {
			
			@Override
			public int compare(EndlessCfg left, EndlessCfg right) {
				return left.getWave() - right.getWave();
			}
		};
		
		List<EndlessCfg> list = new ArrayList<>();
		list.add(new EndlessCfg(0, 1));
		list.add(new EndlessCfg(1, 5));
		list.add(new EndlessCfg(2, 8));
		list.add(new EndlessCfg(3, 10));
		list.add(new EndlessCfg(4, 15));
		
		EndlessCfg cfg = new EndlessCfg(10, 23);
		int index = Collections.binarySearch(list, cfg, comparator);
		
		System.out.println(index);
	}
	
	public class EndlessCfg{
		
		private int id;
		private int wave;
		
		public EndlessCfg(int id, int wave) {
			super();
			this.id = id;
			this.wave = wave;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getWave() {
			return wave;
		}
		public void setWave(int wave) {
			this.wave = wave;
		}
		
	}
}
