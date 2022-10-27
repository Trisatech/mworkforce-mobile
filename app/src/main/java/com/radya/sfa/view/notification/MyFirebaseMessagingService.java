package com.radya.sfa.view.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.view.home.HomeActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID = "SFA_NOTIFICATION_ID";
    private static final String NOTIFICATION_NAME = "SFA_NOTIFICATION_NAME";

    private int notificationId;
    private String title, message;
    private String itemId;
    private String type;
    private String link;

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        AppUtils.logD("FirebaseMessagingService", "Refreshed token: " + refreshedToken);
        PrefManager.saveString(Constant.Preference.User.FCM_TOKEN, refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        notificationId = PrefManager.getInt(Constant.Preference.User.NOTIFICATION_ID);
        PrefManager.saveInt(Constant.Preference.User.NOTIFICATION_ID, notificationId);

        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("body");
        itemId = remoteMessage.getData().get("item_id");
        type = remoteMessage.getData().get("type");
        link = remoteMessage.getData().get("link");

        sendNotification();

    }

    private void sendNotification() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
