package jp.amamon.simple.client;

import jp.amamon.simple.client.exceptions.SimpleClientException;
import jp.amamon.simple.client.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * DO NOT support any overload methods.
 * Never create overload method in the interfaces.
 */
public class ServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    private ServiceFactory() {
    }

    public static <T> T getService(Class<T> clazz) {
        return getService(clazz, new DefaultResultCallback());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> clazz, ResultCallback callback) {
        if (!clazz.isInterface()) {
            logger.error("class " + clazz + " is not an interface!");
            callback.onFailed(new SimpleClientException("class " + clazz + " is not an interface!"));
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new ServiceProcessor(clazz, callback, new BaseResultHolderParser(new JsonMapper())));
    }
}
