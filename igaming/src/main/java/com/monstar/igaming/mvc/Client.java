package com.monstar.igaming.mvc;

import org.junit.Test;

public class Client {

	/**
	 * 1.属性不加static<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple1<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple2<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple3<BR>
	 * 
	 * 2.属性加static<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple1<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple2<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple3<BR>
	 * 
	 * 3.总结:<BR>
	 * 3.1.不使用static每次创建对象都会对属性对象进行创建;<BR>
	 * 3.2.使用static除首次创建对象会对属性对象进行创建,再次创建对象不会对属性对象进行创建;<BR>
	 *
	 * @param args
	 */

	@Test
	public void compareStatic() {

		String pname = "apple";

		CO co = new CO();
		co.create(pname + 1);

		co = new CO();
		co.create(pname + 2);

		co = new CO();
		co.create(pname + 3);
	}

	/**
	 * 
	 * 加static,一次反射多次newInstance<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple1<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple2<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple3<BR>
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRef() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String pname = "apple";
		String clazzPath = "com.monstar.igaming.mvc.CO";

		Class<CO> clazz = (Class<CO>) Class.forName(clazzPath); // 使用反射的形式

		CO co = clazz.newInstance();
		co.create(pname + 1);

		co = clazz.newInstance();
		co.create(pname + 2);

		co = clazz.newInstance();
		co.create(pname + 3);
	}

	/**
	 * 
	 * 加static,一次反射对应一次newInstance<BR>
	 * com.monstar.igaming.mvc.VO<BR>
	 * com.monstar.igaming.mvc.MO<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple1<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple2<BR>
	 * com.monstar.igaming.mvc.CO<BR>
	 * com.monstar.igaming.mvc.CO = apple3<BR>
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRef2() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String pname = "apple";
		String clazzPath = "com.monstar.igaming.mvc.CO";

		Class<CO> clazz = (Class<CO>) Class.forName(clazzPath); // 使用反射的形式
		CO co = clazz.newInstance();
		co.create(pname + 1);

		clazz = (Class<CO>) Class.forName(clazzPath);
		co = clazz.newInstance();
		co.create(pname + 2);

		clazz = (Class<CO>) Class.forName(clazzPath);
		co = clazz.newInstance();
		co.create(pname + 3);
	}

}
