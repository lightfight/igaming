package com.lightfight.game.cfg;

public abstract class Cfg {

	/** 配置文件配置项的ID */
	protected Integer id;

	/**
	 * @desc: 验证配置文件的正确性
	 * @throws CfgException
	 */
	public abstract void validate() throws Exception;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
