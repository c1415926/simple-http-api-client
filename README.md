
### A simple http json service Java caller


if you have a API like below with some json response:

    http://localhost:8080/test/ITestService/doSomething
    
then you can create an interface:

    public interface ITestService {
        Object doSomething();
    }

and use like:

    ITestService testService = ServiceFactory.getService(ITestService.class);
    TestResultObject resultObjectPost = testService.doSomething(requestObject);

For more information, see the sample test code in test folder.

### Version
1.0.0