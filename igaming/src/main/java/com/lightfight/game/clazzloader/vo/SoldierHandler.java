package com.lightfight.game.clazzloader.vo;

public class SoldierHandler {

	/**
	 * 
	 * @return
	 */
	public int getFightforce(SoldierVO vo) {

		/** 等级属性 **/
		int[] lvAttributes = vo.getLvAttributes();

		// int fightforce = lvAttributes[1] ;
		int fightforce = 11 * lvAttributes[1];

		return fightforce;
	}
}
