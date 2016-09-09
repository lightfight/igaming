package com.lightfight.game.log;

public class OnlineLO extends LogLO {
	
	final static String tableName = "online";
	
	public OnlineLO(int onlineCount, String date) {
		logs.append("tableName=").append(tableName).append(",");
		logs.append("onlineCount=").append(onlineCount).append(",");
	}
	
}
