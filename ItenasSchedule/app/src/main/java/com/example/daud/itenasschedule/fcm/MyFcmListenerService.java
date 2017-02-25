package com.example.daud.itenasschedule.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.daud.itenasschedule.BaseActivity;
import com.example.daud.itenasschedule.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
//import com.ptdam.gcmtofcm.BayarToko;
//import com.ptdam.gcmtofcm.R;

import java.util.Map;
import java.util.Random;

/**
 * Created by Debam on 8/4/2016.
 */

public class MyFcmListenerService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();
//        RemoteMessage.Notification Notif = remoteMessage.getNotification();

        Log.e("data", data.toString());

        String title = data.get("title").toString();
        String message = data.get("message").toString();
//        String otp = data.get("otp").toString();
//        String amount = data.get("amount").toString();
//
//        Log.e("title",title);
//        Log.e("message",message);
//        Log.e("otp",otp);
//        Log.e("amount",amount);

        showSmallNotification2(title,message);

        Log.e("data", data.toString());
    }

    private void showSmallNotification2(String title, String message) {
//        Intent intent = new Intent(this, BayarToko.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("from","notif");
//        intent.putExtra("otp",otp);
//        intent.putExtra("amount",amount);
        Intent intent = new Intent(this, BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Random random = new Random();
        final int notificationNumber = random.nextInt(9999 - 1000) + 1000;


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationNumber, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        inboxStyle.addLine(message);
        long[] vibrate = {0,500,800,1000};

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentTitle(title)
                .setVibrate(vibrate)
                .setContentIntent(pendingIntent)
                .setSound(sound)
                .setDefaults(Notification.DEFAULT_LIGHTS)
//                .setStyle(inboxStyle)
                .setSmallIcon(R.drawable.itenas)
                .setContentText(message);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationNumber, notification.build());
    }



// /
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String from = remoteMessage.getFrom();
//        Map data = remoteMessage.getData();
//
//        Log.e("data", data.toString());
//
//        String title = data.get("title").toString();
//        String message = data.get("message").toString();
//        String otp = data.get("otp").toString();
//        String amount = data.get("amount").toString();
//
//        Log.e("title",title);
//        Log.e("message",message);
//        Log.e("otp",otp);
//        Log.e("amount",amount);
//
//        showSmallNotification2(title,message,otp,amount);
//
//        Log.e("data", data.toString());
//    }
//
//    private void showSmallNotification2(String title, String message, String otp, String amount) {
//        Intent intent = new Intent(this, BayarToko.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("from","notif");
//        intent.putExtra("otp",otp);
//        intent.putExtra("amount",amount);
//
//        Random random = new Random();
//        final int notificationNumber = random.nextInt(9999 - 1000) + 1000;
//
//
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationNumber, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        inboxStyle.addLine(message);
//        long[] vibrate = {0,500,800,1000};
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
//                .setTicker(title)
//                .setWhen(0)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setContentTitle(title)
//                .setVibrate(vibrate)
//                .setContentIntent(pendingIntent)
//                .setSound(sound)
//                .setDefaults(Notification.DEFAULT_LIGHTS)
////                .setStyle(inboxStyle)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText(message);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationNumber, notification.build());
//    }

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String from = remoteMessage.getFrom();
//        Map data = remoteMessage.getData();
////        RemoteMessage.Notification Notif = remoteMessage.getNotification();
//
//        Log.e("data", data.toString());
//
//        String title = data.get("title").toString();
//        String message = data.get("message").toString();
////        String otp = data.get("otp").toString();
////        String amount = data.get("amount").toString();
////
//        Log.e("title",title);
//        Log.e("message",message);
////        Log.e("otp",otp);
////        Log.e("amount",amount);
//
//        showSmallNotification2(title,message);
//
//        Log.e("data", data.toString());
//    }
//
//    private void showSmallNotification2(String title, String message) {
////        Intent intent = new Intent(this, BayarToko.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.putExtra("from","notif");
////        intent.putExtra("otp",otp);
////        intent.putExtra("amount",amount);
//        Intent intent = new Intent(this, BaseActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        Random random = new Random();
//        final int notificationNumber = random.nextInt(9999 - 1000) + 1000;
//
//
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationNumber, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        inboxStyle.addLine(message);
//        long[] vibrate = {0,500,800,1000};
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
//                .setTicker(title)
//                .setWhen(0)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setContentTitle(title)
//                .setVibrate(vibrate)
//                .setContentIntent(pendingIntent)
//                .setSound(sound)
//                .setDefaults(Notification.DEFAULT_LIGHTS)
////                .setStyle(inboxStyle)
//                .setSmallIcon(R.drawable.itenas)
//                .setContentText(message);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationNumber, notification.build());
//    }


}
