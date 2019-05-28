package com.example.qra;

public class CheckEditingException extends Exception {

    private String errorMessage;
    public final String DEFAULT_ERROR_MESSAGE = "this check from fns";

    public CheckEditingException() {
        errorMessage = DEFAULT_ERROR_MESSAGE;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Deprecated
    public String getErrorMessage() {
        return errorMessage;
    }
}
