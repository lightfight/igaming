package com.lightfight.game.config;

/**
 * 配置异常类
 * 
 */
public class CfgException extends RuntimeException {
	
	/**  **/
	private static final long serialVersionUID = 412281803294994655L;
	
	private String info;

	public CfgException(String info) {
		this.info = info;
	}

	@Override
	public String getMessage() {
		return info;
	}
}
