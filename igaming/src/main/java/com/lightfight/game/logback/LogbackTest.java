package com.lightfight.game.logback;

import java.util.Scanner;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogbackTest {

	// 注意这个logger不是slf4j的logger
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(LogbackTest.class);
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

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("cmd>");
			try {
				String cmd = sc.nextLine();
				process(cmd.split(" "));
			} catch (Exception e) {
				sc.close();
				e.printStackTrace();
			}
		}
	}

	private static void process(String[] cmd) {
		if (cmd[0].equals("debug")) {
			LOG.setLevel(Level.DEBUG);
		} else if (cmd[0].equals("info")) {
			LOG.setLevel(Level.INFO);
		} else if (cmd[0].equals("warn")) {
			LOG.setLevel(Level.WARN);
		} else if (cmd[0].equals("error")) {
			LOG.setLevel(Level.ERROR);
		}else if (cmd[0].equals("stdout")) {
			Logger log = (Logger) LoggerFactory.getLogger("ROOT");
			ConsoleAppender<?> std = (ConsoleAppender<?>)log.getAppender("STDOUT");
			if (cmd[1].equals("1")) {
				std.start();
			}else {
				std.stop();
			}
			
		}
		// TRACE < DEBUG < INFO < WARN < ERROR
		LOG.debug("debug" + cmd[1]);
		if (LOG.isDebugEnabled()) {
			LOG.debug("isDebugEnabled");
		}

		LOG.info("info" + cmd[1]);
		if (LOG.isInfoEnabled()) {
			LOG.info("isInfoEnabled");
		}

		LOG.warn("warn" + cmd[1]);
		if (LOG.isWarnEnabled()) {
			LOG.warn("isWarnEnabled");
		}

		LOG.error("error" + cmd[1]);
		if (LOG.isErrorEnabled()) {
			LOG.error("isErrorEnabled");
		}
	}

}
