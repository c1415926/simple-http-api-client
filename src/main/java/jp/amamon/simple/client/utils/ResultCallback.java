package jp.amamon.simple.client.utils;

public interface ResultCallback {

    <T> void onCompleted(T object);

    void onFailed(Throwable e);
}
