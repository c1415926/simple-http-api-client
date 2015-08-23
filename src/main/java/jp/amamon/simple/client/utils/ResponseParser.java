package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;

import java.lang.reflect.Type;

public interface ResponseParser {
    <T> T parse(String responseString, final Class<T> valueType, final Type genericType) throws SimpleClientException;
}
