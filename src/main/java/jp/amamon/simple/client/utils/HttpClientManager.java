package jp.amamon.simple.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

class HttpClientManager {

    private static PoolingHttpClientConnectionManager httpClientConnectionManager;

    private static DefaultProxyRoutePlanner defaultProxyRoutePlanner;

    private static CloseableHttpClient httpClient;

    static {
        String maxTotalString = HttpClientConfigHolder.getProperties(HttpClientConfigHolder.MAX_CONNECTION);
        String maxPerRoute = HttpClientConfigHolder.getProperties(HttpClientConfigHolder.MAX_CONNECTION_PER_ROUTE);
        String proxyHost = HttpClientConfigHolder.getProperties(HttpClientConfigHolder.PROXY_HOST);
        String proxyPort = HttpClientConfigHolder.getProperties(HttpClientConfigHolder.PROXY_PORT);

        httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(StringUtils.isNotEmpty(maxTotalString) ? Integer.parseInt(maxTotalString) : 500);
        httpClientConnectionManager.setDefaultMaxPerRoute(StringUtils.isNotEmpty(maxPerRoute) ? Integer.parseInt(maxPerRoute) : 50);

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Accept", "application/json"));

        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultHeaders(headers);

        if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNotEmpty(proxyPort)) {
            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort));
            defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClientBuilder.setRoutePlanner(defaultProxyRoutePlanner);
        }

        httpClient = httpClientBuilder.build();
    }

    private HttpClientManager() {
    }

    static CloseableHttpClient getInstance() {
        return httpClient;
    }

}
