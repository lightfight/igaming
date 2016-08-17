package com.lightfight.game.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AnnotationTest {

	
	@Autowired
	public void init(){
		
	}
	
	@Qualifier(value="abc")
	public void anno(){
		
	}
}
