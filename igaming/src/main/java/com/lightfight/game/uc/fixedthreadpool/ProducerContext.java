package com.lightfight.game.uc.fixedthreadpool;

import java.util.concurrent.ExecutorService;

/**
 * 消费者上下文
 * 
 * @author deliang
 *
 */
public class ProducerContext {

	private ExecutorService consumer;

	/**
	 * 执行命令/任务
	 * 
	 * @param command
	 */
	public void execute(Runnable command) {
		consumer.execute(command);
	}
}
