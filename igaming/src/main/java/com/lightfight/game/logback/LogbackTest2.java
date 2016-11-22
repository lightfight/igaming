package com.lightfight.game.logback;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogbackTest2 {

	// 注意这个logger不是slf4j的logger
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(LogbackTest2.class);

	public static void init() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure("config/logback.xml");
		} catch (JoranException e) {
			e.printStackTrace();
		}

		StatusPrinter.print(lc);

		LOG.info("LogbackConfig init successfully!");
	}

	public static void main(String[] args) {

		init();

		for (int i = 0; i < 2; i++) {
			LOG.info(i + "");
		}

	}

}
