package com.copart.fcm.andriod.poc;

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
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(FcmConstants.TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(FcmConstants.TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(FcmConstants.TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        sendNotifications(remoteMessage);
    }

    private void sendNotifications(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        // preparing the notifications
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setSound(defaultSoundUri);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(title + "-0", title, importance);
            notificationChannel.setDescription(body);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onMessageSent(String msgId) {
        Log.e(FcmConstants.TAG, "onMessageSent: " + msgId);
    }

    @Override
    public void onSendError(String msgId, Exception e) {
        Log.e(FcmConstants.TAG, "onSendError: " + msgId);
        Log.e(FcmConstants.TAG, "Exception: " + e);
    }
}
