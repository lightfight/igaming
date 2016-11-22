package com.lightfight.game.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

/**
 * 
 * hold住多个文件的output,有数据的时候就往里面写
 * 
 * @author deliang
 *
 */
public class MultiFileWrite {

	/** 临时文件的文件夹路径 **/
	final static String tempFileFolderPath = "D:\\txt\\temp";

	/** <文件名,writer> **/
	Map<String, OutputStreamWriter> writers = new HashMap<>();
	
	@Test
	public void testMultiWrite() throws Exception{
		
		String[] filenames = {"t_online.csv", "t_create_player.csv", "t_pay.csv"};
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			String filename = filenames[random.nextInt(filenames.length)];
			
			StringBuilder content = new StringBuilder();
			
			content.append("apple");
			content.append(",").append("100");
			content.append(",").append("2016-10-28 16:29:00");
			content.append("\n");
			
			write(filename, content.toString());
		}
		
		close();
		
		System.out.println("运行结束");
	}

	/**
	 * 向文件中写内容
	 * 
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void write(String filename, String content) throws Exception {
		OutputStreamWriter writer = writers.get(filename);
		if (writer == null) {
			writer = createWriter(filename);
			writers.put(filename, writer);
		}

		writer.write(content);
	}

	/**
	 * 关闭所有的输出流
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		for (OutputStreamWriter item : writers.values()) {
			item.flush();
			item.close();
		}
	}

	/**
	 * 创造输出流
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public OutputStreamWriter createWriter(String filename) throws Exception {

		File file = new File(tempFileFolderPath);
		if (!file.exists())
			file.mkdirs();

		file = new File(tempFileFolderPath, filename);
		if (file.exists()) {
			file.delete();
		}
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
		return writer;
	}
}
