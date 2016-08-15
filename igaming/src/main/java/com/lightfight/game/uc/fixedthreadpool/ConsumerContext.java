package com.lightfight.game.uc.fixedthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lightfight.game.uc.TaskPoolThreadFactory;

/**
 * 消费者上下文
 * 
 * @author deliang
 *
 */
public class ConsumerContext {

	private ExecutorService consumer;

	public ConsumerContext() {
		TaskPoolThreadFactory threadFactory = new TaskPoolThreadFactory("consumer");
		consumer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1, threadFactory);
	}

	/**
	 * 执行命令/任务
	 * 
	 * @param command
	 */
	public void execute(Runnable command) {
		consumer.execute(command);
	}
}
