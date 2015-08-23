package jp.amamon.simple.client.model;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class BaseResultHolder implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ProcessResultType resultType;

    private List<String> errorMessages;

    private Object responseObject;

    public BaseResultHolder() {
        this.resultType = ProcessResultType.ERROR;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public ProcessResultType getResultType() {
        return resultType;
    }

    public void setResultType(ProcessResultType resultType) {
        this.resultType = resultType;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessages(String errorMessage) {
        if (this.errorMessages == null) {
            this.errorMessages = new ArrayList<String>();
        }
        this.errorMessages.add(errorMessage);
    }

    public void addErrorMessages(List<String> errorMessages) {
        if (this.errorMessages == null) {
            this.errorMessages = new ArrayList<String>();
        }
        this.errorMessages.addAll(errorMessages);
    }

    public void addErrorMessages(Exception e) {
        if (this.errorMessages == null) {
            this.errorMessages = new ArrayList<String>();
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        this.errorMessages.add(stringWriter.toString());
    }
}
