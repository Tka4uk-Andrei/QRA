package com.example.qra.model.webRequests;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class that creates messages for some web request exceptions \\
 *
 * @autor : Ekaterina Novoselova
 */
public class WebRequestException extends Exception implements Parcelable {

    public static final String CHECK_DOES_NOT_EXIST_MESSAGE = "Check doesn't exist";
    public static final String BAD_REQUEST_MESSAGE = "Bad request";
    public static final String CHECK_NOT_FOUND_MESSAGE = "Check not found";
    public static final String AUTHORIZATION_ERROR_MESSAGE = "Authorization error";
    public static final String WRONG_LOGIN_OR_PASSWORD_MESSAGE = "Wrong login or password";
    public static final String USER_EXISTS_MESSAGE = "User exists";
    public static final String INCORRECT_PHONE_MESSAGE = "Uncorrect phone";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String NO_INTERNET_CONNECTION_MESSAGE = "No internet connection";


    private String message;

    // TODO documentation

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
            message = INCORRECT_PHONE_MESSAGE;
        } else if (code == 404) {
            message = USER_NOT_FOUND_MESSAGE;
        // if code not recognized
        } else if (code > 0) {
            message = "Error code is " + code;
            // TODO string under may be bad
        } else if (msg.equals("Unable to resolve host \"proverkacheka.nalog.ru\": No address associated with hostname")) {
            message = NO_INTERNET_CONNECTION_MESSAGE;
        } else message = msg;
    }

    protected WebRequestException(Parcel in) {
        message = in.readString();
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
    }

    public static final Creator<WebRequestException> CREATOR = new Creator<WebRequestException>() {

        @Override
        public WebRequestException createFromParcel(Parcel in) {
            return new WebRequestException(in);
        }

        @Override
        public WebRequestException[] newArray(int size) {
            return new WebRequestException[size];
        }
    };
}
