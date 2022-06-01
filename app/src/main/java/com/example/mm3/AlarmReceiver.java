package com.example.mm3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver{

    AlarmFragment alarmFragment;
    private Context context;
    private String channelId="alarm_channel";
//    int randomV;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


        Intent busRouteIntent = new Intent(context, AlarmFragment.class);

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntentWithParentStack(busRouteIntent);
//        PendingIntent busRoutePendingIntent =
//                stackBuilder.getPendingIntent(randomV, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentTitle("알람")
                .setContentText("울림");
//                .setContentIntent(busRoutePendingIntent);


        final NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        int id=(int)System.currentTimeMillis();

        notificationManager.notify(id,notificationBuilder.build());

    }

   /* @Override
    public void onCategoryApplySelected(int randomV) {
        this.randomV = randomV;
    }*/
}