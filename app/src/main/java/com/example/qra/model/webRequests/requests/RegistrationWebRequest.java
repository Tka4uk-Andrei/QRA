package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.WebRequestException;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationWebRequest implements Runnable {

    private UserDataForFns userData;
    private Handler exceptionHandler;

    // TODO documentation
    public RegistrationWebRequest(UserDataForFns userData, Handler exceptionHandler){
        this.userData = userData;
        this.exceptionHandler = exceptionHandler;
    }

    private Message getExceptionMessage(WebRequestException exception){
        Message exceptionMessage = exceptionHandler.obtainMessage();

        Bundle bundle = new Bundle();
        bundle.putParcelable("exception", exception);
        exceptionMessage.setData(bundle);

        return exceptionMessage;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        String targetURL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/signup";
        // if exception called before/while connect responseCode will be -1
        int responseCode = -1;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // connect
            connection.connect();

            // Send POST parameters.
            OutputStream os = connection.getOutputStream();
            JSONObject message = new JSONObject();
            message.put("email", userData.getUserEmail());
            message.put("name", userData.getUserName());
            message.put("phone", userData.getPhoneNumber());
            os.write(message.toString().getBytes("UTF-8"));

            // if we establish connection, but response is bad.
            responseCode = connection.getResponseCode();
            if (responseCode != 204)
                exceptionHandler.sendMessage(
                        getExceptionMessage(new WebRequestException(responseCode, "")));
        } catch (Exception e) {
            // Mostly if connection fails
            exceptionHandler.sendMessage(
                    getExceptionMessage(new WebRequestException(responseCode, e.getMessage())));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
