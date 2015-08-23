package jp.amamon.simple.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResultCallback implements ResultCallback {

    private static final Logger logger = LoggerFactory.getLogger(DefaultResultCallback.class);

    @Override
    public <T> void onCompleted(T object) {
    }

    @Override
    public void onFailed(Throwable e) {
        logger.error(e.getMessage(), e);
    }
}
