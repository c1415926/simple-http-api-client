package jp.amamon.simple.client.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class JsonMapper implements Mapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T read(String fromString, final Class<T> valueType) throws IOException {
        return objectMapper.readValue(fromString, valueType);
    }

    public <T> T read(Object fromValue, final Class<T> valueType, final Class<?> genericType) {
        JavaType javaType = null;

        if (valueType.equals(List.class)) {
            javaType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, genericType);
        } else if (valueType.equals(Set.class)) {
            javaType = objectMapper.getTypeFactory().constructCollectionType(HashSet.class, genericType);
        } else if (valueType.equals(Map.class)) {
            javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, genericType);
        }

        if (javaType == null) {
            return objectMapper.convertValue(fromValue, valueType);
        } else {
            return objectMapper.convertValue(fromValue, javaType);
        }
    }

}
