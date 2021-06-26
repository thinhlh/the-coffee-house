package com.coffeehouse.the.services.local;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.LevelListDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.coffeehouse.the.R;
import com.coffeehouse.the.views.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public class FCMService extends FirebaseMessagingService {

    public static final String TOPIC = "notifications";



    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            //Contains data
            //Handle the data flow in the app
        }

        //Notification only
        if (remoteMessage.getNotification() != null) {
            Log.d("FCMService", "Service Notification Body: " + remoteMessage.getNotification().getTitle());
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage notification) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.project_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_coffee_24)
//                    .setLargeIcon(BitmapFactory.decodeStream(new URL(notification.get).openConnection().getInputStream()))
                .setContentTitle(notification.getNotification().getTitle() != null ? notification.getNotification().getTitle() : "The coffee house")
                .setContentText(notification.getNotification().getBody())
                .setContentInfo("INFO")
                .setSubText("Notification")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "The Coffee House",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        Log.d("","HERE");
        notificationManager.notify(0, notificationBuilder.build());
    }
}
