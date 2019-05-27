package com.example.qra;

public class CheckEditingException extends Exception {

    private String errorMessage;
    public final String DEFAULT_ERROR_MESSAGE = "unknown error";

    public static final String ERROR_MESSAGE_CHECK_FROM_FNS = "this check from fns";
    public static final String ERROR_MESSAGE_NO_CHECK_IN_DB = "there is no check in the database";

    @Deprecated
    public CheckEditingException() {
        errorMessage = DEFAULT_ERROR_MESSAGE;
    }

    public CheckEditingException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
