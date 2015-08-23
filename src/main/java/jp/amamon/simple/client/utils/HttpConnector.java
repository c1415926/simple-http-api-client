package jp.amamon.simple.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.amamon.simple.client.exceptions.SimpleClientException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HttpConnector {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String callByGet(String url, Map<String, Object> map) throws IOException, SimpleClientException {
        if (url == null) {
            logger.error("url is null");
            return null;
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 2);
        }
        if (map != null) {
            url += "?";
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    String tmp = entry.getKey() + "=" + entry.getValue();
                    url += tmp;
                }
            }
        }
        return callByGet(url);
    }

    public static String callByGet(String url) throws IOException, SimpleClientException {
        if (url == null) {
            logger.error("url is null");
            return null;
        }
        return callByGet(URI.create(url));
    }

    public static String callByGet(URI uri) throws IOException, SimpleClientException {
        if (uri == null) {
            logger.error("uri is null");
            return null;
        }
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpClient httpClient = HttpClientManager.getInstance();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new SimpleClientException("Error happened when call http get. error code is " + response.getStatusLine().getStatusCode());
            }
        } finally {
            response.close();
        }
    }

    public static String callByPost(String url, Map<String, Object> map) throws IOException, SimpleClientException {
        if (url == null) {
            logger.error("url is null");
            return null;
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        return callByPost(URI.create(url), map);
    }

    public static String callByPost(URI uri, Map<String, Object> map) throws IOException, SimpleClientException {
        if (uri == null) {
            logger.error("uri is null");
            return null;
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        String content = objectMapper.writeValueAsString(map);
        return callByPost(uri, new StringEntity(content, Charset.forName("utf-8")));
    }

    public static String callByPost(URI uri, HttpEntity entity) throws IOException, SimpleClientException {

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(entity);
        CloseableHttpClient httpClient = HttpClientManager.getInstance();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new SimpleClientException("Error happened when call http post. error code is " + response.getStatusLine().getStatusCode());
            }
        } finally {
            response.close();
        }
    }
}
