package com.example.qra;

public class CheckEditingException extends Exception {

    private String errorMessage;

    public CheckEditingException() {
        errorMessage = "this check from fns";
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
