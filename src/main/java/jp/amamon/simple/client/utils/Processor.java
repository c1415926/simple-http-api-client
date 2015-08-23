package jp.amamon.simple.client.utils;

import jp.amamon.simple.client.exceptions.SimpleClientException;

public interface Processor {
    <T> T process(Request<T> request) throws SimpleClientException;
}
