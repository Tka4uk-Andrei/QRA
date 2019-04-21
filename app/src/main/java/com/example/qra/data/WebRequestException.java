package com.example.qra.data;

/**
 * Class that creates messages for some web request exceptions \\
 * @autor : Ekaterina Novoselova
 */
public class WebRequestException extends Exception {
    private String message;

    public WebRequestException(String msg) {
        message = msg;
    }

    public WebRequestException(int code, String msg) {
        if (code == 451) {
            message = "Check doesn't exist";
        } else if (code == 400) {
            message = "Bad request";
        } else if (code == 406) {
            message = "Check not found";
        } else if (code == 401) {
            message = "Authorization error";
        } else if (code == 403) {
            message = "Wrong login or password";
        } else message = msg;
    }

    public String getMessage() {
        return message;
    }
}
