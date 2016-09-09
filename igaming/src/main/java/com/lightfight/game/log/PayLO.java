package com.lightfight.game.log;

public class PayLO extends LogLO {

	public PayLO(String tableName, int payAmount) {
		logs.append("tableName=").append(tableName).append(",");
		logs.append("payAmount=").append(payAmount).append(",");
	}
}
