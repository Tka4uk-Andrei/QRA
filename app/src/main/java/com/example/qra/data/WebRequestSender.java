package com.example.qra.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.util.Base64.DEFAULT;

public class WebRequestSender {

    /**
     * Method that send web request to receive check data \\
     *
     * @return the response to the http request \\
     * @autor : Ekaterina Novoselova
     */
    public static String getWebRequestData(final QrData targetQr, final UserDataForFns userData) throws Exception {
        final String[] response = new String[1];
        final WebRequestException[] exception = new WebRequestException[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;

                StringBuilder urlSB = new StringBuilder();
                urlSB.append("https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/");
                urlSB.append(targetQr.getFiscalNumber());
                urlSB.append("/tickets/");
                urlSB.append(targetQr.getFiscalDocument());
                urlSB.append("?fiscalSign=");
                urlSB.append(targetQr.getFiscalSignOfDocument());
                urlSB.append("&sendToEmail=no");

                String targetURL = urlSB.toString();
                try {
                    //Create connection
                    URL url = new URL(targetURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("device-id", "192.168.211.72");
                    connection.setRequestProperty("device-os", "android, v.8.0.0");
                    connection.setRequestProperty("Authorization", base64Encode(userData.getPhoneNumber(),
                            userData.getPassword()));

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
                    response[0] = resp.toString();
                } catch (IOException e) {
                    try {
                        int responseCode = 0;
                        if (connection != null) {
                            responseCode = connection.getResponseCode();
                            exception[0] = new WebRequestException(responseCode, e.getMessage());
                        }
                    } catch (IOException ex) {
                        exception[0] = new WebRequestException(ex.getMessage());
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
        t.start();
        t.join();
        if (exception[0] != null) {
            throw exception[0];
        }
        return response[0];
    }

    /**
     * @return the base64 code \\
     * @autor : Ekaterina Novoselova
     */
    private static String base64Encode(String phone, String password) {
        StringBuilder authorizationSB = new StringBuilder();
        authorizationSB.append(phone);
        authorizationSB.append(":");
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
