package jp.amamon.simple.client.sample;

import java.io.Serializable;

public class TestRequestObject implements Serializable {

    private String requestName;

    private int requestId;

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
