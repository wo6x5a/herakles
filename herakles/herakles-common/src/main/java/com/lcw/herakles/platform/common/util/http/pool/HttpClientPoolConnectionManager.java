package com.lcw.herakles.platform.common.util.http.pool;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * http client Connect Pool Manager
 * 
 * @author chenwulou
 *
 */
public class HttpClientPoolConnectionManager {

    private static final HttpClientPoolConnectionManager instance = new HttpClientPoolConnectionManager();

    public static synchronized HttpClientPoolConnectionManager getInstance() {
        return instance;
    }

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;

    private HttpClientPoolConnectionManager() {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(200);// 连接池大小
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(50);// 每条通道的并发连接数设置
    }

    public CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(this.poolingHttpClientConnectionManager).build();
    }
}
