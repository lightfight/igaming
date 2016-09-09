package com.lightfight.game.log;

public abstract class LogLO {

	StringBuilder logs = new StringBuilder();
	
	public String write(){
		return logs.toString();
	}
	
}
