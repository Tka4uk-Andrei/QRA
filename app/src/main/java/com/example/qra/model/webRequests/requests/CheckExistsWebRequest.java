package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequestUtilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckExistsWebRequest implements Runnable {

    private QrData qrData;
    private UserDataForFns userData;
    private Handler exceptionHandler;
    private Handler responseHandler;

    public CheckExistsWebRequest(QrData qrData, UserDataForFns userData,
                                 Handler responseHandler, Handler exceptionHandler) {
        this.qrData = qrData;
        this.userData = userData;
        this.responseHandler = responseHandler;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        String urlAddress = "https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*" +
                "/fss/" + qrData.getFiscalNumber() +
                "/operations/" + qrData.getTypeOfFiscalDocument() +
                "/tickets/" + qrData.getFiscalDocument() +
                "?fiscalSign=" + qrData.getFiscalSignOfDocument() +
                "&date=" + qrData.getBuyTime() +
                "&sum=" + qrData.getTotalCheckSum();

        // if connection not establish response code will still -1
        int responseCode = -1;
        try {
            //Create connection
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("device-id", "192.168.211.72");
            connection.setRequestProperty("device-os", "android, v.8.0.0");
            connection.connect();

            // send message to handler
            Message responseMessage = responseHandler.obtainMessage();
            Bundle bundle = new Bundle();
            responseCode = connection.getResponseCode();
            bundle.putBoolean("return", responseCode == 204);
            responseMessage.setData(bundle);

        } catch (IOException e) {
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
