package com.lightfight.game.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class RefreshDailyStatistic {

	@Test
	public void refreshDailyStatistic() throws Exception {

		String filenamesCfg = "2016-12-27,2016-12-28,2016-12-29,2016-12-30,2016-12-31,2017-01-01,2017-01-02,2017-01-03,2017-01-04";

		String[] platforms = { "xiaomi", "ea" };
		
//		String host = "http://120.26.4.146:81/cron/day/date/";
		String host = "http://120.26.4.146/cron/day/date/";
//		String host = "http://xx.com/cron/day/date/";
		
		CloseableHttpClient httpclient = HttpClients.custom().build();

		String[] filenames = filenamesCfg.split(",");
		for (String item : filenames) {
			String date = item.replace("-", "");

			String refreshUrl = host + date + "/game_name/";
			for (String platform : platforms) {
				String refreshPlatformUrl = refreshUrl + platform;
				System.out.println(refreshPlatformUrl);
				
				HttpGet httpGet = new HttpGet(refreshPlatformUrl);
				CloseableHttpResponse response = httpclient.execute(httpGet);
				
				String html = EntityUtils.toString(response.getEntity());
				System.out.println(html);
			}
		}
	}

}
