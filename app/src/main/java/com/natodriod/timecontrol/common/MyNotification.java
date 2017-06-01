package com.natodriod.timecontrol.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.natodriod.timecontrol.R;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by natiqmustafa on 12.04.2017.
 */

public class MyNotification {

    private Context context;
    private boolean ongoing;
    private boolean autoCancel;




    public MyNotification(Context context,  boolean ongoing, boolean autoCancel) {
        this.context = context;
        this.ongoing = ongoing;
        this.autoCancel = autoCancel;
    }

    public MyNotification(Context context) {
        this(context, true, false);
        this.context = context;

    }


    public void showNotification(Class<?> cls, String[] titleAndContent, long currentMillis, List<NotificationCompat.Action> actionList, int notifyId, Uri soundUri) {
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setAutoCancel(autoCancel)
                .setOngoing(ongoing)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentIntent(pendingIntent);

        if (titleAndContent.length >= 2) {
            notificationCompat.setContentTitle(titleAndContent[0])
                    .setContentText(titleAndContent[1]);
        }
        if (currentMillis > 0) {
            notificationCompat.setUsesChronometer(true).setWhen(currentMillis);
        }

        if (actionList != null && actionList.size() > 0){
            for (NotificationCompat.Action action : actionList){
                notificationCompat.addAction(action);
            }
        }



        if (soundUri != null) {
            notificationCompat.setSound(soundUri);
        }else{
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationCompat.setSound(uri);
        }

        //Vibration
        notificationCompat.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        notificationCompat.setLights(Color.RED, 3000, 3000);




        /*
        Intent intentBrodcast = new Intent(AlarmConfig.INTENT_FILTER_ALARM);
        PendingIntent ip = PendingIntent.getBroadcast(this, 0, intentBrodcast, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.addAction(0, "Add", ip);


        notificationCompat.addAction(android.R.drawable.ic_menu_report_image, "Cancle", pendingIntent);
        notificationCompat.addAction(android.R.drawable.ic_menu_report_image, "Minus", pendingIntent);
        */

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationCompat.build().flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(notifyId , notificationCompat.build());


    }

    public void showNotification(Class<?> cls, String[] titleAndContent, long currentMillis, List<NotificationCompat.Action> actionList, int notifyId){
        showNotification(cls, titleAndContent, currentMillis, actionList, notifyId, null);
    }

    public void showNotification(Class<?> cls, String[] titleAndContent, int notifyId){
        showNotification(cls, titleAndContent, 0, null, notifyId, null);
    }

    public void showNotification(Class<?> cls, String[] titleAndContent, int notifyId, Uri uri){
        showNotification(cls, titleAndContent, 0, null, notifyId, uri);
    }


    public void cancelNotification(int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) context.getApplicationContext().getSystemService(ns);
        nMgr.cancel(notifyId);
    }



}
