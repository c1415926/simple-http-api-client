package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.annotation.ParamName;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProcessor implements InvocationHandler, Processor {

    private Class<?> targetObject;

    protected Map<String, Type> collectionResultTypeMap = new HashMap<String, Type>();

    protected ResultCallback callback;

    public AbstractProcessor(Class<?> targetObject, ResultCallback callback) {
        this.targetObject = targetObject;
        this.callback = callback;
        this.initCollectionResultTypeMap();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            String serviceName = targetObject.getSimpleName();
            String methodName = method.getName();
            Map<String, Object> params = new HashMap<String, Object>();
            Class<?>[] parameters = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0; i < parameters.length; i++) {
                String parameterName = this.parseParamName(annotations[i]);
                if (StringUtils.isNotEmpty(parameterName)) {
                    params.put(parameterName, args[i]);
                }
            }
            Class<?> resultClass = method.getReturnType();
            Type genericType = this.collectionResultTypeMap.get(methodName);
            Request<?> request = RequestBuilder.start(resultClass)
                    .setServiceName(serviceName)
                    .setMethodName(methodName)
                    .setGenericType(genericType)
                    .setParams(params).build();
            return this.process(request);
        } catch (Exception e) {
            this.callback.onFailed(e);
        }
        return null;
    }

    private String parseParamName(Annotation[] annotations) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(ParamName.class)) {
                String value = ((ParamName) annotation).value();
                if (StringUtils.isEmpty(value)) {
                    return null;
                } else {
                    return value;
                }
            }
        }
        return null;
    }

    private void initCollectionResultTypeMap() {
        for (Method method : this.targetObject.getMethods()) {
            Class<?> returnClass = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnClass)) {
                Type returnType = method.getGenericReturnType();
                if (returnType instanceof ParameterizedType) {
                    ParameterizedType paramType = (ParameterizedType) returnType;
                    Type[] argTypes = paramType.getActualTypeArguments();
                    if (argTypes.length > 0) {
                        this.collectionResultTypeMap.put(method.getName(), argTypes[0]);
                    }
                }
            }
        }
    }
}
