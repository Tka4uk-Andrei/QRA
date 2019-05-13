package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequestUtilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetCheckDataWebRequest implements Runnable {

    private QrData qrData;
    private UserDataForFns userData;
    private Handler exceptionHandler;
    private Handler returnHandler;

    public GetCheckDataWebRequest(QrData qrData, UserDataForFns userData,
                                  Handler exceptionHandler, Handler returnHandler) {
        this.qrData = qrData;
        this.userData = userData;
        this.exceptionHandler = exceptionHandler;
        this.returnHandler = returnHandler;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        String targetURL = "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*" +
                "/fss/" + qrData.getFiscalNumber() +
                "/tickets/" + qrData.getFiscalDocument() +
                "?fiscalSign=" + qrData.getFiscalSignOfDocument() +
                "&sendToEmail=no";

        // if connection not establish response code will still -1
        int responseCode = -1;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("device-id", "192.168.211.72");
            connection.setRequestProperty("device-os", "android, v.8.0.0");
            connection.setRequestProperty(
                    "Authorization",
                    WebRequestUtilities.base64Encode(userData.getPhoneNumber(), userData.getPassword()));

            // check response code
            responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                exceptionHandler.sendMessage(WebRequestUtilities.getExceptionMessage(
                        exceptionHandler,
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

                // send return message
                Message message = returnHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("return", response.toString());
                message.setData(bundle);
                returnHandler.sendMessage(message);
            }
        } catch (Exception e) {
            exceptionHandler.sendMessage(WebRequestUtilities.getExceptionMessage(
                    exceptionHandler,
                    new WebRequestException(responseCode, "")));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
