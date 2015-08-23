package jp.amamon.simple.client.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.amamon.simple.client.ServiceFactory;
import jp.amamon.simple.client.model.BaseResultHolder;
import jp.amamon.simple.client.model.BaseResultHolderBuilder;
import jp.amamon.simple.client.model.ProcessResultType;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Sample {

    @Mocked
    CloseableHttpClient httpClient;

    @Mocked
    CloseableHttpResponse response;

    @Mocked
    EntityUtils entityUtils;

    @Test
    public void testRegular() throws IOException {

        TestRequestObject requestObject = new TestRequestObject();
        requestObject.setRequestId(1);
        requestObject.setRequestName("testRequest");

        TestResultObject testResultObject = new TestResultObject();
        testResultObject.setResultId(requestObject.getRequestId() + 1);
        testResultObject.setResultName("testResult");

        BaseResultHolder baseResultHolder = BaseResultHolderBuilder.start()
                .setResultType(ProcessResultType.SUCCESS)
                .setResponseObject(testResultObject)
                .create();

        ObjectMapper objectMapper = new ObjectMapper();

        final String responseString = objectMapper.writeValueAsString(baseResultHolder);

        new NonStrictExpectations() {
            {
                httpClient.execute((HttpPost) any);
                result = response;
            }

            {
                httpClient.execute((HttpGet) any);
                result = response;
            }

            {
                response.getStatusLine().getStatusCode();
                result = 200;
            }

            {
                EntityUtils.toString((HttpEntity) any);
                result = responseString;
            }
        };

        //also can get service throw spring by using ServiceBeanFactory.
        ITestService testService = ServiceFactory.getService(ITestService.class);

        TestResultObject resultObjectPost = testService.doSomething(requestObject);

        Assert.assertEquals(2, resultObjectPost.getResultId());
        Assert.assertEquals("testResult", resultObjectPost.getResultName());

        TestResultObject resultObjectGet = testService.getSomething(requestObject);

        Assert.assertEquals(2, resultObjectGet.getResultId());
        Assert.assertEquals("testResult", resultObjectGet.getResultName());
    }

}
