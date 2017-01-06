package com.lightfight.game.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpsRequestTest {
	
	@Test
	public void testTomcatHttps() throws Exception{
		
		// 浏览器可以访问,httpclient不可以访问
//		 String host = "https://localhost";
//		 String host = "http://localhost";
		
		// 浏览器可以访问,httpclient可以访问,主要原因在于进行了301或者302进行重定向,而httpclient进行了重定向处理
//		String host = "https://baidu.com";
//		String host = "https://aliyun.com";
		String host = "https://wind.waqi.info/mapq/bounds/?bounds=30,103,31,104";
		
		CloseableHttpClient httpclient = HttpClients.custom().build();
		
		HttpGet httpGet = new HttpGet(host);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
		String html = EntityUtils.toString(response.getEntity());
		System.out.println(html);
	}
	
	/**
	 * http://www.blogjava.net/stevenjohn/archive/2016/04/27/430267.html<BR>
	 * httpClient Https 单向不验证(httpClient连接池)<BR>
	 * 依然存在http302状态码<BR>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTomcatHttps2() throws Exception{
		
//		String host = "https://localhost/wechat";
//		String host = "https://aliyun.com";
//		String host = "https://baidu.com";
		
		// 可以获取某个范围内的空气质量指数
//		String host = "https://wind.waqi.info/mapq/bounds/?bounds=30,103,31,104";
		String host = "https://wind.waqi.info/mapq/bounds/?bounds=28.696050,107.004856,32.214269,102.013175";
		
		CloseableHttpClient httpsClient = getHttpsClient();
		
		HttpGet httpGet = new HttpGet(host);
		CloseableHttpResponse response = httpsClient.execute(httpGet);
		
		String html = EntityUtils.toString(response.getEntity());
		System.out.println(html);
		
	}
	
	public static CloseableHttpClient getHttpsClient() {
		
		CloseableHttpClient  httpsClient = null;
        try {
            //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            //Implementation of a trust manager for X509 certificates
            TrustManager x509TrustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{x509TrustManager}, null);
            //首先设置全局的标准cookie策略
//            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
            ConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(ctx);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", connectionSocketFactory).build();
            // 设置连接池
             httpsClient = HttpClients.custom()
                    .setConnectionManager(PoolsManager.getHttpsPoolInstance(socketFactoryRegistry))
                    .setDefaultRequestConfig(requestConfig())
                    .setRetryHandler(retryHandler())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpsClient;
    }
	
	public static class PoolManager {
        public static PoolingHttpClientConnectionManager clientConnectionManager = null;
        private static int maxTotal = 200;
        private static int defaultMaxPerRoute = 100;
        private PoolManager(){
            clientConnectionManager.setMaxTotal(maxTotal);
            clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        }
        private static class PoolManagerHolder{
            public static  PoolManager instance = new PoolManager();
        }
        public static PoolManager getInstance() {
            if(null == clientConnectionManager)
                clientConnectionManager = new PoolingHttpClientConnectionManager();
            return PoolManagerHolder.instance;
        }
        public static PoolingHttpClientConnectionManager getHttpPoolInstance() {
            PoolManager.getInstance();
            System.out.println("getAvailable=" + clientConnectionManager.getTotalStats().getAvailable());
            System.out.println("getLeased=" + clientConnectionManager.getTotalStats().getLeased());
            System.out.println("getMax=" + clientConnectionManager.getTotalStats().getMax());
            System.out.println("getPending="+clientConnectionManager.getTotalStats().getPending());
            return PoolManager.clientConnectionManager;
        }
    }
    public static class PoolsManager {
        public static PoolingHttpClientConnectionManager clientConnectionManager = null;
        private static int maxTotal = 200;
        private static int defaultMaxPerRoute = 100;
        private PoolsManager(){
            clientConnectionManager.setMaxTotal(maxTotal);
            clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        }
        private static class PoolsManagerHolder{
            public static  PoolsManager instance = new PoolsManager();
        }
        public static PoolsManager getInstance(Registry<ConnectionSocketFactory> socketFactoryRegistry) {
            if(null == clientConnectionManager)
                clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            return PoolsManagerHolder.instance;
        }
        public static PoolingHttpClientConnectionManager getHttpsPoolInstance(Registry<ConnectionSocketFactory> socketFactoryRegistry) {
            PoolsManager.getInstance(socketFactoryRegistry);
            System.out.println("getAvailable=" + clientConnectionManager.getTotalStats().getAvailable());
            System.out.println("getLeased=" + clientConnectionManager.getTotalStats().getLeased());
            System.out.println("getMax=" + clientConnectionManager.getTotalStats().getMax());
            System.out.println("getPending="+clientConnectionManager.getTotalStats().getPending());
            return PoolsManager.clientConnectionManager;
        }
}
	
	public static RequestConfig requestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();
        return requestConfig;
    }
    public static HttpRequestRetryHandler retryHandler(){
        //请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        return httpRequestRetryHandler;
    }

}
