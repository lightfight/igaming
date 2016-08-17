package com.lightfight.game.mq;

import org.springframework.jms.core.JmsTemplate;

public class SpringJMS {
	
	private JmsTemplate jmsTemplate;
	
	public void send(){
		jmsTemplate.convertAndSend("", new Object());
	}

}
