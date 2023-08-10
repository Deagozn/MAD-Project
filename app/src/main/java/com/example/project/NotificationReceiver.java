package com.example.project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_MESSAGE_EXTRA = "notification_message";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create a notification channel (required for Android 8.0 and higher)
        createNotificationChannel(context);

        // Build the notification
        Notification.Builder builder = new Notification.Builder(context, "channelId")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Booking Reminder")
                .setContentText("Your booking is starting in 15 minutes.")
                .setAutoCancel(true);

        // Create a notification manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notify with the built notification
        notificationManager.notify(1, builder.build());

        // Send a broadcast with the notification message
        String notificationMessage = "Your booking is starting in 15 minutes.";
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.example.project.NOTIFICATION_RECEIVED");
        broadcastIntent.putExtra(NOTIFICATION_MESSAGE_EXTRA, notificationMessage);
        context.sendBroadcast(broadcastIntent);
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId", "Booking Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
