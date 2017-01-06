package com.lightfight.game.lunar;

public class Lunar extends Gregorian {
	public boolean isLeap;

	Lunar(int y, int m, int d) {
		super(y, m, d);
	}

	@Override
	public String toString() {
		return "Lunar{" + "year=" + year + ", month=" + month + ", day=" + day + ", isLeap=" + isLeap + '}';
	}
}
