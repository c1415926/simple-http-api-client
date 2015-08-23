package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;
import jp.amamon.simple.client.model.BaseResultHolder;
import jp.amamon.simple.client.model.ProcessResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

public class BaseResultHolderParser implements ResponseParser {

    private static final Logger logger = LoggerFactory.getLogger(BaseResultHolderParser.class);

    private Mapper mapper;

    public BaseResultHolderParser(Mapper mapper) {
        this.mapper = mapper;
    }

    public <T> T parse(String responseString, final Class<T> valueType, final Type genericType) throws SimpleClientException {
        BaseResultHolder baseResultHolder;
        try {
            baseResultHolder = this.mapper.read(responseString, BaseResultHolder.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new SimpleClientException("error when deserialize, responseString is:" + responseString);
        }
        if (baseResultHolder == null) {
            throw new SimpleClientException("deserialize result is null, responseString is:" + responseString);
        }
        if (baseResultHolder.getResultType().getValue() == ProcessResultType.SUCCESS.getValue()) {
            Object responseObject = baseResultHolder.getResponseObject();
            if (responseObject == null) {
                throw new SimpleClientException("no responseObject! responseString is:" + responseString);
            }
            return this.mapper.read(responseObject, valueType, (Class<?>) genericType);
        } else if (baseResultHolder.getResultType().getValue() == ProcessResultType.ERROR.getValue()) {
            throw new SimpleClientException(baseResultHolder.getErrorMessages());
        } else {
            throw new SimpleClientException("Unknown error happened! responseString is:" + responseString);
        }
    }
}
