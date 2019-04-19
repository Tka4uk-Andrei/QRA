package com.example.qra.data;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.util.Base64.DEFAULT;

public class WebRequestData {
    private UserDataForFns currentUserData;

    private String response;
    private Exception exception;

    private class GetRequestSender extends AsyncTask<QrData, Void, String> {
        @Override
        protected String doInBackground(QrData... qrDataParam) {
            HttpURLConnection connection = null;

            QrData qrData = qrDataParam[0];
            StringBuilder urlSB = new StringBuilder();

            urlSB.append("https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/");
            urlSB.append(qrData.getFiscalNumber());
            urlSB.append("/tickets/");
            urlSB.append(qrData.getFiscalDocument());
            urlSB.append("?fiscalSign=");
            urlSB.append(qrData.getFiscalSignOfDocument());
            urlSB.append("&sendToEmail=no");

            String targetURL = urlSB.toString();
            try {
                //Create connection
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("device-id", "192.168.211.72");
                connection.setRequestProperty("device-os", "android, v.8.0.0");
                connection.setRequestProperty("Authorization", base64Encode(currentUserData.getPhoneNumber(),
                        currentUserData.getPassword()));

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder resp = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    resp.append(line);
                    resp.append('\r');
                }
                rd.close();
                response = resp.toString();
                return response;
            } catch (IOException e) {
                exception = e;
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    /**
     * @return the response to the http request \\
     * @autor : Ekaterina Novoselova
     */
    public String getWebRequestData(QrData targetQr, UserDataForFns currentUserData) throws Exception {
        this.currentUserData = currentUserData;

        GetRequestSender sender = new GetRequestSender();
        sender.execute(targetQr).get();

        if (exception != null) {
            throw exception;
        }

        return response;
    }

    /**
     * @return the base64 code \\
     * @autor : Ekaterina Novoselova
     */
    private String base64Encode(String phone, String password) {
        StringBuilder authorizationSB = new StringBuilder();
        authorizationSB.append(phone);
        authorizationSB.append(':');
        authorizationSB.append(password);
        String authorization = authorizationSB.toString();

        byte[] bytes = authorization.getBytes();
        String base64Code = android.util.Base64.encodeToString(bytes, DEFAULT);

        authorizationSB.delete(0, authorizationSB.capacity());
        authorizationSB.append("Basic ");
        authorizationSB.append(base64Code);
        base64Code = authorizationSB.toString();
        return base64Code;
    }
}
