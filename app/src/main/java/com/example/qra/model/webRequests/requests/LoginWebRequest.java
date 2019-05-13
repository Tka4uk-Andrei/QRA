package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequestUtilities;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginWebRequest implements Runnable {

    UserDataForFns userData;
    Handler exceptionHandler;
    Handler returnHandler;

    public LoginWebRequest(UserDataForFns userData,
                           Handler exceptionHandler, Handler returnHandler) {
        this.userData = userData;
        this.exceptionHandler = exceptionHandler;
        this.returnHandler = returnHandler;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;

        // if connection not establish response code will still -1
        int responseCode = -1;
        try {
            //Create connection
            URL url = new URL("https://proverkacheka.nalog.ru:9999/v1/mobile/users/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("device-id", "192.168.211.72");
            connection.setRequestProperty("device-os", "android, v.8.0.0");
            connection.setRequestProperty("Authorization",
                    WebRequestUtilities.base64Encode(userData.getPhoneNumber(),
                            userData.getPassword()));

            // check response code
            responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                exceptionHandler.sendMessage(
                        WebRequestUtilities.getExceptionMessage(exceptionHandler,
                                new WebRequestException(responseCode, "")));
            } else {
                //if status code OK -> get Response
                String response = connection.getResponseMessage();
                JSONObject json = new JSONObject(response);

                // send return message
                Message message = returnHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("email", json.getString("email"));
                bundle.putString("name", json.getString("name"));
                message.setData(bundle);
                returnHandler.sendMessage(message);
            }
        } catch (Exception e) {
            exceptionHandler.sendMessage(WebRequestUtilities.getExceptionMessage(
                    exceptionHandler,
                    new WebRequestException(responseCode, e.getMessage())));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
