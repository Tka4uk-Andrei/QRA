package com.example.qra.model.webRequests;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.service.autofill.UserData;
import android.widget.Toast;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.MainActivity;
import com.example.qra.view.ShowCheckInfoActivity;
import com.example.qra.view.WebRequestActivity;

import java.util.concurrent.TimeUnit;

import static com.example.qra.model.UserDataForFns.getInstanceDefault;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private String tempResponse;

    //время счиается в отдельном потоке, это позволит запускать этот серер неск раз,
    //только вероятно не void, а стринг или нет, уже не
    // public void timer(QrData qrDataObject, UserDataForFns userDataForFnsObject, PendingIntent pi) {
    public void timer(QrData qrDataObject, UserDataForFns userDataForFnsObject, PendingIntent pi) {
        Intent intentinFunction = new Intent(this, ShowCheckInfoActivity.class);
        Context context = this;
        //  String response = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tempResponse = null;
                    try {
                          tempResponse = WebRequestSender.getWebRequestData(qrDataObject, userDataForFnsObject);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    // TimeUnit.SECONDS.sleep(10);
                    // tempResponse = LogInActivity.QR;

                    while (tempResponse == null) {

                        TimeUnit.HOURS.sleep(6);
                        // TimeUnit.SECONDS.sleep(10);

                       try{
                           tempResponse = WebRequestSender.getWebRequestData(qrDataObject, userDataForFnsObject);
                       }
                       catch (Exception e) {
                           Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                        //  tempResponse = LogInActivity.QR;

                    }

                    intentinFunction.putExtra("JSON_DATA", tempResponse);
                    tempResponse = null;
                    pi.send(MyService.this, 200, intentinFunction);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }

        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//
          String rawData = intent.getStringExtra("rawData");
        //String rawData = "t=20190508T1228&s=312.20&fn=9251440300006654&i=48585&fp=3966313266&n=1";
        PendingIntent pendingIntent = intent.getParcelableExtra("pendingIntent");

        int t = 0;
        timer(new QrData(rawData), getInstanceDefault(), pendingIntent);
      //  t();


        return super.onStartCommand(intent, flags, startId);
    }

    public void t() {

        Context context = this;
        //  String response = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 2;

                    while (i > 0) {
                        TimeUnit.SECONDS.sleep(10);
                        i--;
                        Notification.showNotiication(context, new Intent(context, MainActivity.class));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }

        }).start();
    }
}
