package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequestUtilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginWebRequest implements Runnable {

    public static final String HANDLE_RETURN_KEY_EMAIL = "email";
    public static final String HANDLE_RETURN_KEY_NAME = "name";

    private UserDataForFns userData;
    private Handler exceptionHandler;
    private Handler returnHandler;

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
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                reader.close();

                JSONObject json = new JSONObject(response.toString());

                // send return message
                Message message = returnHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString(HANDLE_RETURN_KEY_EMAIL, json.getString("email"));
                bundle.putString(HANDLE_RETURN_KEY_NAME, json.getString("name"));
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
