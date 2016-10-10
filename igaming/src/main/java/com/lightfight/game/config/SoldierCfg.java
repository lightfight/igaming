package com.lightfight.game.config;

import java.io.IOException;
import java.util.List;

public class SoldierCfg implements CfgValidate, CfgHandate{
	
	/** 士兵配置档对象 **/
	SoldierConfig config;
	
	/** 将策划数据进行处理后的数据,招募消耗 **/
	List<Integer[]> recruitCosts;

	@Override
	public void validate() throws CfgException {
		// TODO
	}

	@Override
	public void handle() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
