package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServiceProcessor extends AbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProcessor.class);

    private ResponseParser responseParser;

    public ServiceProcessor(Class<?> clazz, ResultCallback callback, ResponseParser responseParser) {
        super(clazz, callback);
        this.responseParser = responseParser;
    }

    @Override
    public <T> T process(Request<T> request) throws SimpleClientException {
        try {
            String responseString;
            if (request.getHttpMethodType() == Request.HttpMethodType.GET) {
                URI uri = RequestURIBuilder.buildGet(request);
                responseString = HttpConnector.callByGet(uri);
            } else {
                URI uri = RequestURIBuilder.buildPost(request);
                responseString = HttpConnector.callByPost(uri, request.getParams());
            }
            T resultObject = this.responseParser.parse(responseString, request.getResultClass(), request.getGenericType());
            this.callback.onCompleted(resultObject);
            return resultObject;
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            throw new SimpleClientException(e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new SimpleClientException(e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new SimpleClientException(e);
        }
    }

}
