package com.example.qra.model.webRequests;

import com.example.qra.model.qrCode.QrData;
import com.example.qra.model.UserDataForFns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.util.Base64.DEFAULT;

import org.json.JSONObject;

/**
 * Class that creates web requests \\
 *
 * @autor : Ekaterina Novoselova
 */
@Deprecated
public class WebRequests {

    /**
     * Method that send registration web request. \\
     * <em><b>Deprecated</b></em> use RegistrationWebRequest class instead
     *
     * @param userData current user data \\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
    public static void registrationWebRequest(final UserDataForFns userData) throws Exception {
        final WebRequestException[] exception = new WebRequestException[1];
        final int[] responseCode = {0};
        Thread t = new Thread(() -> {
            HttpURLConnection connection = null;
            String targetURL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/signup";
            try {
                JSONObject message = new JSONObject();
                message.put("email", userData.getUserEmail());
                message.put("name", userData.getUserName());
                message.put("phone", userData.getPhoneNumber());

                //Create connection
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);

                connection.connect();

                // Send POST output.
                OutputStream os = connection.getOutputStream();
                os.write(message.toString().getBytes("UTF-8"));

                responseCode[0] = connection.getResponseCode();
                if (responseCode[0] != 204) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error code is ").append(((Integer) responseCode[0]).toString());
                    exception[0] = new WebRequestException(responseCode[0], sb.toString());
                }
            } catch (Exception e) {
                if (exception[0] == null) {
                    try {
                        responseCode[0] = connection.getResponseCode();
                    } catch (IOException e1) {
                        exception[0] = new WebRequestException(0, e.getMessage());
                    }
                    exception[0] = new WebRequestException(responseCode[0], e.getMessage());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        t.start();
        t.join();
        if (exception[0] != null) {
            throw exception[0];
        }
    }


    /**
     * Method that send user recovery web request \\
     * <em><b>Deprecated</b></em> use UserRecoveryWebRequest class instead
     *
     * @param userData current user data \\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
    public static void userRecoveryWebRequest(final UserDataForFns userData) throws Exception {
        final WebRequestException[] exception = new WebRequestException[1];
        final int[] responseCode = {0};
        Thread t = new Thread(() -> {
            HttpURLConnection connection = null;
            String targetURL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/restore";
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

                responseCode[0] = connection.getResponseCode();
                if (responseCode[0] != 204) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error code is ").append(((Integer) responseCode[0]).toString());
                    exception[0] = new WebRequestException(responseCode[0], sb.toString());
                }
            } catch (Exception e) {
                if (exception[0] == null) {
                    try {
                        responseCode[0] = connection.getResponseCode();
                    } catch (IOException e1) {
                        exception[0] = new WebRequestException(0, e.getMessage());
                    }
                    exception[0] = new WebRequestException(responseCode[0], e.getMessage());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        t.start();
        t.join();
        if (exception[0] != null) {
            throw exception[0];
        }
    }

    /**
     * Method that log in user and updates registration data like email and name \\
     * <em><b>Deprecated</b></em> use LogInWebRequest class instead
     *
     * @param userData current user data \\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
    public static void logInWebRequest(final UserDataForFns userData) throws Exception {
        final WebRequestException[] exception = new WebRequestException[1];
        final int[] responseCode = new int[1];
        Thread t = new Thread(() -> {
            HttpURLConnection connection = null;
            String targetURL = new String("https://proverkacheka.nalog.ru:9999/v1/mobile/users/login");
            try {
                //Create connection
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("device-id", "192.168.211.72");
                connection.setRequestProperty("device-os", "android, v.8.0.0");
                connection.setRequestProperty("Authorization", base64Encode(userData.getPhoneNumber(),
                        userData.getPassword()));

                responseCode[0] = connection.getResponseCode();
                if (responseCode[0] != 200) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error code is ").append(((Integer) responseCode[0]).toString());
                    exception[0] = new WebRequestException(responseCode[0], sb.toString());
                } else {
                    String msg = connection.getResponseMessage();
                    JSONObject json = new JSONObject(msg);

                    userData.setUserName(json.getString("email"));
                    userData.setUserEmail(json.getString("name"));
                }
            } catch (Exception e) {
                if (exception[0] == null) {
                    try {
                        responseCode[0] = connection.getResponseCode();
                    } catch (IOException e1) {
                        exception[0] = new WebRequestException(0, e.getMessage());
                    }
                    exception[0] = new WebRequestException(responseCode[0], e.getMessage());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        t.start();
        t.join();
        if (exception[0] != null) {
            throw exception[0];
        }
    }

    /**
     * Method that send web request to check the existence of a check \\
     * <em><b>Deprecated</b></em> use CheckExistsWebRequest class instead
     *
     * @param targetQr target check data\\
     * @return true if check exists, false if doesn't exist\\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
    public static boolean isCheckExistsWebRequest(final QrData targetQr) throws Exception {
        final WebRequestException[] exception = new WebRequestException[1];
        final int[] responseCode = new int[1];
        Thread t = new Thread(() -> {
            HttpURLConnection connection = null;

            StringBuilder urlSB = new StringBuilder();
            urlSB.append("https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/");
            urlSB.append(targetQr.getFiscalNumber());
            urlSB.append("/operations/");
            urlSB.append(targetQr.getTypeOfFiscalDocument());
            urlSB.append("/tickets/");
            urlSB.append(targetQr.getFiscalDocument());
            urlSB.append("?fiscalSign=");
            urlSB.append(targetQr.getFiscalSignOfDocument());
            urlSB.append("&date=");
            urlSB.append(targetQr.getBuyTime());
            urlSB.append("&sum=");
            urlSB.append(targetQr.getTotalCheckSum());


            String targetURL = urlSB.toString();
            try {
                //Create connection
                URL url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("device-id", "192.168.211.72");
                connection.setRequestProperty("device-os", "android, v.8.0.0");
                connection.connect();

                responseCode[0] = connection.getResponseCode();

            } catch (IOException e) {
                if (exception[0] == null) {
                    try {
                        responseCode[0] = connection.getResponseCode();
                    } catch (IOException e1) {
                        exception[0] = new WebRequestException(0, e.getMessage());
                    }
                    exception[0] = new WebRequestException(responseCode[0], e.getMessage());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        t.start();
        t.join();
        if (exception[0] != null) {
            throw exception[0];
        }
        return responseCode[0] == 204;
    }

    /**
     * Method that send web request to receive check data \\
     * <em><b>Deprecated</b></em> use GetCheckDataWebRequest class instead
     *
     * @param targetQr target check data \\
     * @param userData current user data \\
     * @return the response to the http request \\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
    public static String getCheckDataWebRequest(final QrData targetQr, final UserDataForFns userData) throws Exception {
        final String[] response = new String[1];
        final WebRequestException[] exception = new WebRequestException[1];
        final int[] responseCode = new int[1];
        Thread t = new Thread(() -> {


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
                responseCode[0] = connection.getResponseCode();
                if (responseCode[0] != 200) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error code is ").append(((Integer) responseCode[0]).toString());
                    exception[0] = new WebRequestException(responseCode[0], sb.toString());
                }
            } catch (Exception e) {
                if (exception[0] == null) {
                    try {
                        responseCode[0] = connection.getResponseCode();
                    } catch (IOException e1) {
                        exception[0] = new WebRequestException(0, e.getMessage());
                    }
                    exception[0] = new WebRequestException(responseCode[0], e.getMessage());
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
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
