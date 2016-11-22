package com.lightfight.game.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class FileTest {

	@SuppressWarnings({ "resource", "unused" })
	@Test
	public void testModifyName() throws FileNotFoundException{
		
		String fileName = "info.log";
		String folderPath = "D:\\txt\\log";
		
		// 开始的时候游戏服务器和日志服务器指向到同一个文件
		String currPath = folderPath + File.separator + fileName;
		File gameServerPointFile = new File(currPath);
		File logServerPointFile = new File(currPath);
		
		String nextPath = folderPath + File.separator + "info-2016-10-27.log";
		File gameServerNextPointFile = new File(nextPath);
		
		// 因为换日期了,游戏服务器会将前一天的文件加上日期后缀变为一个新的文件,同时创建一个不加日期的文件
		
		InputStream logServerInputStream = new FileInputStream(logServerPointFile);
		
		boolean is = gameServerPointFile.renameTo(gameServerNextPointFile);
		if (is) {
			System.out.println("rename successfully");
		}else {
			System.out.println("rename failed");
		}
		
	}
}
