package com.lightfight.game.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {

	/**
	 * 获取输入流内容
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String getInputStreamContent(InputStream in) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		in.close();
		
		return sb.toString();
	}
}
