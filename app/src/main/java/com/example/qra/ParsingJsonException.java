package com.example.qra;

/**
 * This class is responsible for handling JSONException exceptions.
 * an object of this class has an attribute with an error message
 * Author: Marina Alekseeva
 */
public class ParsingJsonException extends Exception {

    private String errorMessage;

    public ParsingJsonException(String description) {
        super(description);
        errorMessage = description;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
