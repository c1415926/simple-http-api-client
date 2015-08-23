package jp.amamon.simple.client.utils;

import java.lang.reflect.Type;
import java.util.Map;

class Request<T> {

    enum HttpMethodType {
        GET, POST
    }

    public Request(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    private String hostName;

    private String hostRoot;

    private String serviceName;

    private String methodName;

    private Map<String, Object> params;

    private Class<T> resultClass;

    private Type genericType;

    private HttpMethodType httpMethodType;

    public HttpMethodType getHttpMethodType() {
        return httpMethodType;
    }

    public void setHttpMethodType(HttpMethodType httpMethodType) {
        this.httpMethodType = httpMethodType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Class<T> getResultClass() {
        return resultClass;
    }

    public Type getGenericType() {
        return genericType;
    }

    public void setGenericType(Type genericType) {
        this.genericType = genericType;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostRoot() {
        return hostRoot;
    }

    public void setHostRoot(String hostRoot) {
        this.hostRoot = hostRoot;
    }
}
