package com.lightfight.game.log;

public class LogBOImpl implements LogBO {

	@Override
	public void log(LogLO lo) {
		System.out.println(lo.write());
	}

}
