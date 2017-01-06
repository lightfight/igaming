package com.lightfight.game.clazz;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * https://gold.xitu.io/entry/57bd2557165abd006635ccc7
 * 
 * @author deliang
 *
 */
public class DynamicClassLoader extends ClassLoader {

	private String classPath;

	public DynamicClassLoader(String classPath) {
		this.classPath = classPath;
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			 Class<?> c = findLoadedClass(name);
			if (c == null) {
				byte[] b = getBytes(name);
				return defineClass(null, b, 0, b.length);
			}else {
				return c;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

	private byte[] getBytes(String name) throws IOException {
		
		name = name.replaceAll("\\.", "/");
		
		FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
		int len = fis.available();
		byte[] data = new byte[len];
		fis.read(data);
		fis.close();
		return data;

	}
}
