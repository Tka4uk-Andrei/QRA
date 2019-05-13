package com.example.qra.model.webRequests.requests;

import android.os.Handler;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequestUtilities;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRecoveryWebRequest implements Runnable {

    UserDataForFns userData;
    Handler exceptionHandler;

    public UserRecoveryWebRequest(UserDataForFns userData, Handler exceptionHandler){
        this.exceptionHandler = exceptionHandler;
        this.userData = userData;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        String targetURL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/restore";

        // if connection not establish response code will still -1
        int responseCode = -1;
        try {
            JSONObject message = new JSONObject();
            message.put("phone", userData.getPhoneNumber());

            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("ClientVersion", "1.4.4.4");
            connection.setDoOutput(true);

            connection.connect();

            // Send POST output.
            OutputStream os = connection.getOutputStream();
            os.write(message.toString().getBytes("UTF-8"));

            // get response code
            responseCode = connection.getResponseCode();

            // if recovery not succeeded
            if (responseCode != 204) {
                exceptionHandler.sendMessage( WebRequestUtilities
                        .getExceptionMessage(exceptionHandler,
                                new WebRequestException(responseCode, "")));
            }
        } catch (Exception e) {
            exceptionHandler.sendMessage(
                    WebRequestUtilities.getExceptionMessage(
                            exceptionHandler, new WebRequestException(responseCode, e.getMessage())));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
