package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

class RequestBuilder {

    Request<?> request = null;

    private <T> RequestBuilder(Class<T> resultClass) throws SimpleClientException {
        this.request = new Request<T>(resultClass);
        String hostName = ServicePathConfigHolder.getProperties(ServicePathConfigHolder.HOST_NAME);
        if (StringUtils.isEmpty(hostName)) {
            throw new SimpleClientException("can not get host name to access! ");
        }
        String hostPort = ServicePathConfigHolder.getProperties(ServicePathConfigHolder.HOST_PORT);
        if (StringUtils.isNotEmpty(hostPort)) {
            hostName = hostName + ":" + hostPort;
        }
        request.setHostName(hostName);
        String hostRoot = ServicePathConfigHolder.getProperties(ServicePathConfigHolder.HOST_ROOT);
        if (StringUtils.isEmpty(hostRoot)) {
            throw new SimpleClientException("can not get host root to access! ");
        }
        request.setHostRoot(hostRoot);
    }

    public Request<?> build() {
        if (this.request.getMethodName().startsWith("get")) {
            request.setHttpMethodType(Request.HttpMethodType.GET);
        } else {
            request.setHttpMethodType(Request.HttpMethodType.POST);
        }
        return this.request;
    }

    public static RequestBuilder start(Class<?> resultClass) throws SimpleClientException {
        return new RequestBuilder(resultClass);
    }

    public RequestBuilder setServiceName(String serviceName) {
        this.request.setServiceName(serviceName);
        return this;
    }

    public RequestBuilder setMethodName(String methodName) {
        this.request.setMethodName(methodName);
        return this;
    }

    public RequestBuilder setParams(Map<String, Object> params) {
        this.request.setParams(params);
        return this;
    }

    public RequestBuilder setGenericType(Type genericType) {
        this.request.setGenericType(genericType);
        return this;
    }
}
