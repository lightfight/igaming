package com.lightfight.game.lang.constructor;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;

import org.junit.Test;

public class LoadFolderClasses {

	/**
	 * 
	 * 加载某个文件夹下的class,通过反射获取class的信息<BR>
	 * 
	 * @throws Exception
	 */
	@Test
	public void loadFolderClasses() throws Exception {
		
		String classPath = "E:\\downloads\\workspace-d2\\foodsys\\target\\classes";
		String classPackage = "com.worklog.vo";
		String voPath = classPackage.replace(".", File.separator);
		
		System.out.println(classPath + File.separator + voPath);
		
		File classFolder = new File(classPath + File.separator + voPath); // 文件夹
		File[] files = classFolder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});
		
		DynamicClassLoader classLoader = new DynamicClassLoader(classPath);
		for (File file : files) {
			findClass(classLoader, file, classPackage);
		}
	}
	
	private void findClass(DynamicClassLoader classLoader, File file, String classPackage) throws Exception{
		
		String filename = file.getName();
		System.out.println(filename);
		
		filename = filename.substring(0, filename.length() - ".class".length());
		Class<?> c = classLoader.findClass(classPackage + "." + filename);
		// 创建实例
		Object o = c.newInstance();
		Method m = c.getMethod("getId");
		// 反射
		Object value = m.invoke(o);
		System.out.println("value = " + value);
		
		System.out.println(c.getClassLoader());
	}

}
