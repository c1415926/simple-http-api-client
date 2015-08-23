package jp.amamon.simple.client.model;

import java.util.List;

public class BaseResultHolderBuilder {

    private BaseResultHolder baseResultHolder = new BaseResultHolder();

    private BaseResultHolderBuilder() {
    }

    public BaseResultHolderBuilder setResponseObject(Object responseObject) {
        this.baseResultHolder.setResponseObject(responseObject);
        return this;
    }

    public BaseResultHolderBuilder setResultType(ProcessResultType resultType) {
        this.baseResultHolder.setResultType(resultType);
        return this;
    }

    public BaseResultHolderBuilder addErrorMessages(String errorMessage) {
        this.baseResultHolder.addErrorMessages(errorMessage);
        return this;
    }

    public BaseResultHolderBuilder addErrorMessages(List<String> errorMessages) {
        this.baseResultHolder.addErrorMessages(errorMessages);
        return this;
    }

    public BaseResultHolderBuilder addErrorMessages(Exception e) {
        this.baseResultHolder.addErrorMessages(e);
        return this;
    }

    public BaseResultHolder create() {
        return this.baseResultHolder;
    }

    public static BaseResultHolderBuilder start() {
        return new BaseResultHolderBuilder();
    }

}
