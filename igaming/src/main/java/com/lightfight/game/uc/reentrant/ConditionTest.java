package com.lightfight.game.uc.reentrant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lightfight.game.uc.TaskPoolThreadFactory;

public class ConditionTest {
	
	public static void main(String[] args) throws InterruptedException {
		// 启动10个“写线程”，向BoundedBuffer中不断的写数据(写入0-9)；
		// 启动10个“读线程”，从BoundedBuffer中不断的读数据。
		TaskPoolThreadFactory putFactory = new TaskPoolThreadFactory("put");
		ExecutorService putExecutor = Executors.newCachedThreadPool(putFactory);
		
		TaskPoolThreadFactory takeFactory = new TaskPoolThreadFactory("take");
		ExecutorService takeExecutor = Executors.newCachedThreadPool(takeFactory);
		
		BoundedBuffer bb = new BoundedBuffer();
		
		for (int i = 0; i < 10; i++) {
			putExecutor.execute(new PutThread(bb, i));
			takeExecutor.execute(new TakeThread(bb));
		}
		
		putExecutor.shutdown();
		takeExecutor.shutdown();
	}
}
