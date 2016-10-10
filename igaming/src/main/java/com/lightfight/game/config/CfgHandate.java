package com.lightfight.game.config;

import java.io.IOException;

/**
 * @desc: 配置档需要额外处理的
 */
public interface CfgHandate {

	/**
	 * @desc: 有些配置档需要额外处理
	 * @throws LogicException
	 */
	void handle() throws IOException;

}
