package com.lightfight.game.config;

/**
 * 士兵配置表
 * 
 * @author deliang
 *
 */
public class SoldierConfig extends Config {

	/** 招募消耗 **/
	Integer[][] recruitCosts;

	/** 招募冷却秒数 **/
	Integer recruitCdSeconds;

	/** 士兵等级升级配置档ID,数组的序数对应等级 **/
	Integer[] lvUpgradeCfgIds;

	/** 士兵强化等级升级配置档ID,数组的序数对应等级 **/
	Integer[] reinforceUpgradeCfgIds;

	public Integer[][] getRecruitCosts() {
		return recruitCosts;
	}

	public void setRecruitCosts(Integer[][] recruitCosts) {
		this.recruitCosts = recruitCosts;
	}

	public Integer getRecruitCdSeconds() {
		return recruitCdSeconds;
	}

	public void setRecruitCdSeconds(Integer recruitCdSeconds) {
		this.recruitCdSeconds = recruitCdSeconds;
	}

	public Integer[] getLvUpgradeCfgIds() {
		return lvUpgradeCfgIds;
	}

	public void setLvUpgradeCfgIds(Integer[] lvUpgradeCfgIds) {
		this.lvUpgradeCfgIds = lvUpgradeCfgIds;
	}

	public Integer[] getReinforceUpgradeCfgIds() {
		return reinforceUpgradeCfgIds;
	}

	public void setReinforceUpgradeCfgIds(Integer[] reinforceUpgradeCfgIds) {
		this.reinforceUpgradeCfgIds = reinforceUpgradeCfgIds;
	}

}
