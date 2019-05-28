package com.example.qra.model;

public class CheckAddingException extends Exception {

    private String errorMessage;
    public final String DEFAULT_ERROR_MESSAGE = "such check already exists";

    public CheckAddingException() {
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
