package com.lightfight.game.clazzloader.vo;

public class SoldierVO {

	/** 等级属性 **/
	private int[] lvAttributes = { 100, 200 };
	
	/**
	 * 
	 * @return
	 */
	public int getFightforce() {
		int fightforce = 11 * lvAttributes[1];

		return fightforce;
	}

	public int[] getLvAttributes() {
		return lvAttributes;
	}

	public void setLvAttributes(int[] lvAttributes) {
		this.lvAttributes = lvAttributes;
	}

}
