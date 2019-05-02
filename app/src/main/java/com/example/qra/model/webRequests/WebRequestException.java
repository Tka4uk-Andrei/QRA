package com.example.qra.model.webRequests;

/**
 * Class that creates messages for some web request exceptions \\
 *
 * @autor : Ekaterina Novoselova
 */
public class WebRequestException extends Exception {

    public static final String CHECK_DOES_NOT_EXIST_MESSAGE = "Check doesn't exist";
    public static final String BAD_REQUEST_MESSAGE = "Bad request";
    public static final String CHECK_NOT_FOUND_MESSAGE = "Check not found";
    public static final String AUTHORIZATION_ERROR_MESSAGE = "Authorization error";
    public static final String WRONG_LOGIN_OR_PASSWORD_MESSAGE = "Wrong login or password";
    public static final String USER_EXISTS_MESSAGE = "User exists";
    public static final String UNCORRECT_PHONE_MESSAGE = "Uncorrect phone";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";


    private String message;

    public WebRequestException(String msg) {
        message = msg;
    }

    public WebRequestException(int code, String msg) {
        if (code == 451) {
            message = CHECK_DOES_NOT_EXIST_MESSAGE;
        } else if (code == 400) {
            message = BAD_REQUEST_MESSAGE;
        } else if (code == 406) {
            message = CHECK_NOT_FOUND_MESSAGE;
        } else if (code == 401) {
            message = AUTHORIZATION_ERROR_MESSAGE;
        } else if (code == 403) {
            message = WRONG_LOGIN_OR_PASSWORD_MESSAGE;
        } else if (code == 409) {
            message = USER_EXISTS_MESSAGE;
        } else if (code == 500) {
            message = UNCORRECT_PHONE_MESSAGE;
        } else if (code == 404) {
            message = USER_NOT_FOUND_MESSAGE;
        } else message = msg;
    }

    public String getMessage() {
        return message;
    }
}
