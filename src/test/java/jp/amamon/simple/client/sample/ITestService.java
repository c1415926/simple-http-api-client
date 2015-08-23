package jp.amamon.simple.client.sample;

import jp.amamon.simple.client.annotation.ParamName;

public interface ITestService {

    TestResultObject doSomething(@ParamName("testRequestObject") TestRequestObject testRequestObject);

    TestResultObject getSomething(@ParamName("testRequestObject") TestRequestObject testRequestObject);
}
