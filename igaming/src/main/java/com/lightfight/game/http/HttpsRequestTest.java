package com.lightfight.game.http;

import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class HttpsRequestTest {
	
	@Test
	public void testTomcatHttps() throws Exception{
		
		// 浏览器可以访问,httpclient不可以访问
		 String host = "https://localhost";
//		 String host = "http://localhost";
		
		// 浏览器可以访问,httpclient可以访问,主要原因在于进行了301或者302进行重定向,而httpclient进行了重定向处理
//		String host = "https://baidu.com";
//		String host = "https://aliyun.com";
		
		CloseableHttpClient httpclient = HttpClients.custom().build();
		
		HttpGet httpGet = new HttpGet(host);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
		InputStream is = response.getEntity().getContent();
		String html = HttpUtil.getInputStreamContent(is);
		System.out.println(html);
	}

}
