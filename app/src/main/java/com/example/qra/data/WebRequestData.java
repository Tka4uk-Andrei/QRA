package com.example.qra.data;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequestData {
    private String response;

    private class GetRequestSender extends AsyncTask<QrData, Void, String> {

        @Override
        protected String doInBackground(QrData... qrDataParam) {
            HttpURLConnection connection = null;

            QrData qrData = qrDataParam[0];
            StringBuilder urlSB = new StringBuilder();

            try {
                urlSB.append("https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/");
                urlSB.append(qrData.getFiscalNumber());
                urlSB.append("/tickets/");
                urlSB.append(qrData.getFiscalDocument());
                urlSB.append("?fiscalSign=");
                urlSB.append(qrData.getFiscalSignOfDocument());
                urlSB.append("&sendToEmail=no");

                String targetURL = urlSB.toString();

                //Create connection
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("device-id", "192.168.211.72");
                connection.setRequestProperty("device-os", "android, v.8.0.0");
                connection.setRequestProperty("Authorization", "Basic Kzc5MDk3OTg0NjE2OjIyOTk2Mw==");

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder resp = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    resp.append(line);
                    resp.append('\r');
                }
                rd.close();
                response = resp.toString();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }  finally {
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
    public String getWebRequestData() {
        QrData targetQr = new QrData("9251440300003811","8947","3163913062");

        new GetRequestSender().execute(targetQr);
        return response;
    }

}
