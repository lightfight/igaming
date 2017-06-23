package com.lightfight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Logback {
	
	 private static final Logger LOG = LoggerFactory.getLogger(Logback.class);
	 
	public static void init(){
		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            String dir = System.getProperty("user.dir");
            configurator.doConfigure(dir + "/igaming/config/logback.xml");
       } catch (JoranException e) {
            e.printStackTrace();
        }
        
        LOG.info("LogbackConfig init successfully!");
	}
}
