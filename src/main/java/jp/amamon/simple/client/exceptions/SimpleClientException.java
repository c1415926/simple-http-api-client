package jp.amamon.simple.client.exceptions;

import java.util.ArrayList;
import java.util.List;

public class SimpleClientException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<String> errorMessages = new ArrayList<String>();

    public SimpleClientException(Throwable cause) {
        super(cause);
        errorMessages.add(cause.getMessage());
    }

    public SimpleClientException(String errorMessage) {
        super(errorMessage);
        errorMessages.add(errorMessage);
    }

    public SimpleClientException(List<String> errorMessages) {
        this.errorMessages.addAll(errorMessages);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String errorMessage : this.errorMessages) {
            stringBuffer.append(errorMessage + ";");
        }
        return stringBuffer.toString();
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
