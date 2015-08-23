package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

class RequestURIBuilder {

    private static final String HTTP = "http";

    public static URI buildPost(Request<?> request) throws SimpleClientException, URISyntaxException {

        URIBuilder builder = new URIBuilder()
                .setScheme(HTTP)
                .setHost(request.getHostName())
                .setPath("/" + request.getHostRoot() + "/" + request.getServiceName() + "/" + request.getMethodName());
        return builder.build();
    }

    public static URI buildGet(Request<?> request) throws SimpleClientException, URISyntaxException {
        URIBuilder builder = new URIBuilder()
                .setScheme(HTTP)
                .setHost(request.getHostName())
                .setPath("/" + request.getHostRoot() + "/" + request.getServiceName() + "/" + request.getMethodName());
        for (Map.Entry<String, Object> entry : request.getParams().entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }
}
