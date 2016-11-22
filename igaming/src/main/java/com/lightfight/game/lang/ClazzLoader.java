package com.lightfight.game.lang;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.junit.Test;

public class ClazzLoader {

	@Test
	public void testGetResources() throws IOException {

		System.out.println(ClazzLoader.class.getClassLoader().getResource(""));
		System.out.println(ClazzLoader.class.getClassLoader().getResource("com"));
		System.out.println("+++++++++");

//		String path = "classpath*:" + "com.lightfight/" + "**/*.class";
		String path = "com/lightfight";
		Enumeration<URL> resourceUrls = ClazzLoader.class.getClassLoader().getResources(path);
		while (resourceUrls.hasMoreElements()) {
			URL url = resourceUrls.nextElement();
			System.out.println(url);
		}
	}
}
