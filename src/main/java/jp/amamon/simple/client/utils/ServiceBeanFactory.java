package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.ServiceFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ServiceBeanFactory extends AbstractFactoryBean<Object> {

    private String className;

    private ResultCallback resultCallback;

    @Override
    public Class<?> getObjectType() {
        try {
            //can do something here maybe
            return Class.forName(this.className).getClass();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected Object createInstance() throws Exception {
        if (this.resultCallback != null) {
            return ServiceFactory.getService(Class.forName(this.className), resultCallback);
        } else {
            return ServiceFactory.getService(Class.forName(this.className));
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }
}
