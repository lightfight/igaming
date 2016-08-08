package com.lightfight.game.vo;

public class HeroVO {

	private int cid;
	private String name;

	public HeroVO() {
		System.out.println("HeroVO()");
	}

	public HeroVO(int cid, String name) {
		System.out.println("HeroVO(int cid, String name)");
		this.cid = cid;
		this.name = name;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
