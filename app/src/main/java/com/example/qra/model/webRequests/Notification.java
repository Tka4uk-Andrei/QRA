package com.example.qra.model.webRequests;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.qra.R;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.MainActivity;
import com.example.qra.view.ShowCheckInfoActivity;

public class Notification {

    private static NotificationManager nm;
    private static int id = 1;
    private static void incremetId(){
        id ++;
    }

    public static void showNotiication(Context context, Intent intent) {
        nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

      //  Intent intent = new Intent(context, ShowCheckInfoActivity.class);//любая активити
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context);
        //флаг отвечает за то, что если ранее PendingIntent был создан, он должен отмениться чтоб сгенерировать новый
        PendingIntent pendingIntent =  PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder
                .setContentIntent(pendingIntent)
        .setTicker("пришел ответ из фнс")
        .setWhen(System.currentTimeMillis())
        .setAutoCancel(true) //чтоб закрывалось автоматически
        .setContentTitle("уведомление от приложения QR")
        .setContentText("пришел ответ из фнс, нажмите чтобы перейти")
        .setSmallIcon(R.drawable.ic_launcher_background)
              //  .setSmallIcon(R.drawable.ic_launcher) иконка
        //.setSmallIcon(R.drawable.ic_)//черт его знает!
        ;

        android.app.Notification notification = builder.build();

        //звук вибрация и свет
        notification.flags |= android.app.Notification.DEFAULT_SOUND;
        notification.flags |= android.app.Notification.DEFAULT_VIBRATE;
        notification.flags |= android.app.Notification.DEFAULT_LIGHTS;
        //важн зае
        notification.flags |= android.app.Notification.FLAG_ONGOING_EVENT;


        incremetId();
        nm.notify(id, notification);

    //  context.startActivity(intent);
    }
}
