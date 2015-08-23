package jp.amamon.simple.client.sample;

import java.io.Serializable;

public class TestResultObject implements Serializable {

    private String resultName;
    private int resultId;

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }
}
