package com.lightfight.game.log;

import org.junit.Test;

public class LogClientTest {

	@Test
	public void testLog() {
		
		LogBO logBO = new LogBOImpl();
		
		LogLO online = new OnlineLO(100, "test");
		logBO.log(online);
		
		
		LogLO payLO = new PayLO("pay", 100);
		logBO.log(payLO);
		
	}
}
