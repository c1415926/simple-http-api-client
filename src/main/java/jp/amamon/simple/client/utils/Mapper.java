package jp.amamon.simple.client.utils;

import java.io.IOException;

public interface Mapper {

    <T> T read(String fromString, final Class<T> valueType) throws IOException;

    <T> T read(Object fromValue, final Class<T> valueType, final Class<?> genericType);

}
