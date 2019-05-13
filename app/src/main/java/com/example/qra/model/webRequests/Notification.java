package com.example.qra.model.webRequests;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.qra.R;

/**
 * class responsible for send notification
 *
 * @author: Marina Alekseeva
 */
public class Notification {

    private static NotificationManager nm;
    private static int id = 1;

    private static final String CONTENT_TITLE = "уведомление от QRA";
    private static final String CONTENT_TEXT = "пришел ответ из фнс, нажмите чтобы перейти";
    private static final String TICKER = "пришел ответ из фнс"; //!!!!!!!!!!!(непомню за что отвечает)

    private static final String NAME_CHANNEL_ID = "your_id";
    private static final String TITLE_CHANNEL_ID = "something title";


    /**
     * this function changes id, because if the second and so on
     * notifications have the same id, they will overlap each other
     */
    private static void incrementId() {
        id++;
    }


    /**
     * This function is responsible for generating and displaying a notification in status bar
     *
     * @param context
     * @param intent  - in this intent developer should write activity,
     *                to which he wants to go by clicking on the notification
     */
    public static void showNotification(Context context, Intent intent) {

        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        android.app.Notification.Builder builder = new android.app.Notification.Builder(context);

        //флаг отвечает за то, что если ранее PendingIntent был создан,
        // он должен отмениться чтоб сгенерировать новый
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder
                .setContentIntent(pendingIntent)
                .setTicker(TICKER)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true) //чтоб закрывалось автоматически при нажатии на уведомление
                .setContentTitle(CONTENT_TITLE)
                .setContentText(CONTENT_TEXT)
                .setSmallIcon(R.drawable.ic_launcher_background)
        ;

        //для телефонов с android выше 8 необходимо прописывать
        if (Build.VERSION.SDK_INT >= 26) {
            String channelId = NAME_CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, TITLE_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        android.app.Notification notification = builder.build();

        //звук, вибрация, свет и важность
        notification.flags |= android.app.Notification.DEFAULT_SOUND;
        notification.flags |= android.app.Notification.DEFAULT_VIBRATE;
        notification.flags |= android.app.Notification.DEFAULT_LIGHTS;
        notification.flags |= android.app.Notification.FLAG_ONGOING_EVENT;


        incrementId();
        nm.notify(id, notification);

    }
}
