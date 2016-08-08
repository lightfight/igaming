package com.lightfight.game.vo;

public class MonsterVO extends HeroVO {

	private int skill;

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public MonsterVO(int skill) {
		System.out.println("MonsterVO(int skill)");
		this.skill = skill;
	}
	
	
}
