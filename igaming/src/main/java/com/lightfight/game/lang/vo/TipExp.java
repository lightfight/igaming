package com.lightfight.game.lang.vo;


/**
 * 描述：<br>
 * 		需要抛出，在顶层捕获的异常
 * @author mynff@sina.cn
 * @date 2014-2-8
 */
public class TipExp extends Exception{

	private static final long serialVersionUID = -872158834312613290L;

	private int code ;

	public TipExp(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
