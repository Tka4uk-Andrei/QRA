package com.example.qra.model.webRequests;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import com.example.qra.model.UserDataForFns;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.view.ShowCheckInfoActivity;
import com.example.qra.view.WebRequestActivity;


import java.util.concurrent.TimeUnit;

import static com.example.qra.model.UserDataForFns.getInstanceDefault;
import static com.example.qra.model.webRequests.Notification.showNotification;


/**
 * This service is responsible for sending web requests in the background.
 * <p>
 * After receiving a response from the FNS,
 * the service sends a notification when it is clicked, and ShowCheckInfoActivity opens.
 * <p>
 * service starts with a command startService(Intent),
 * in intent command putExtra(WebRequestService.RAW_DATA, rawdata)
 * put string from which to recover class QrData
 * <p>
 * Example: startService(new Intent(this, WebRequestService.class)
 * .putExtra(WebRequestService.RAW_DATA, qrData.getRawData()));
 *
 * @author Marina Alekseeva
 */
public class WebRequestService extends Service {

    public static final String RAW_DATA = "rawData";
    private final int HOURS_SLEEP = 8;

    public WebRequestService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /**
     * this function sends a request every 8 hours until it receives a response,
     * after which it displays a notification when it is clicked, and ShowCheckInfoActivity opens.
     *
     * @param qrDataObject         - class object QrData
     * @param userDataForFnsObject - class object UserDataForFns
     */
    private void requestEvery8Hours(QrData qrDataObject, UserDataForFns userDataForFnsObject) {

        //этот Intent будет использоваться при отправке уведомления
        Intent intentInService = new Intent(this, ShowCheckInfoActivity.class);

        Context context = this;

        //создание нового потока при каждом обращении к сервису позволит
        // одновременно отсчитывать время для нескольких запросов
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String tempResponse = null;
                    while (tempResponse == null) {
                        TimeUnit.HOURS.sleep(HOURS_SLEEP);
                        try {
                            tempResponse = WebRequestSender.getWebRequestData(qrDataObject, userDataForFnsObject);
                        } catch (Exception e) {
                            //TODO обработка исключения в отдельном потоке
                            //хотя, в этот catch мы будем выпадать каждый раз, когда не будем
                            // получать ответ, так что вопрос, как обработать исключение,
                            // при том что обрабатывать его нужно только в случае если придет ошибка,
                            // при этом не трогая, если "чек не сущестует"
                        }
                    }

                    //когда мы вышли из цикла - у нас есть ответ от фнс, мы кладем строку в интент
                    // и посылаем уведомление
                    intentInService.putExtra(WebRequestActivity.JSON_DATA, tempResponse);
                    showNotification(context, intentInService); //

                } catch (InterruptedException e) {
                    //TODO обработка исключения от sleep
                    e.printStackTrace();
                }

                stopSelf();
            }

        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String rawData = intent.getStringExtra(RAW_DATA);

        requestEvery8Hours(new QrData(rawData), getInstanceDefault());

        return super.onStartCommand(intent, flags, startId);
    }

}