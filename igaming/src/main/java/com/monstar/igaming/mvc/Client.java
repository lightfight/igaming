package com.monstar.igaming.mvc;

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
	public static void main(String[] args) {

		String pname = "apple";

		CO co = new CO();
		co.create(pname + 1);

		co = new CO();
		co.create(pname + 2);

		co = new CO();
		co.create(pname + 3);
	}

}
