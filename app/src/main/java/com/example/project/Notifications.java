package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    ImageButton back_button;
    Button clear_notif;
    LinearLayout notif_layout;

    private static final String CHANNEL_ID = "my_channel_id"; // Notification Channel ID
    private List<NotificationModel> notificationsList = new ArrayList<>();

    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String notificationMessage = intent.getStringExtra(NotificationReceiver.NOTIFICATION_MESSAGE_EXTRA);

            // Add the new notification to the list
            notificationsList.add(new NotificationModel(notificationMessage));

            // Update the notifications layout
            updateNotificationsLayout();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        back_button = findViewById(R.id.back_notif);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notifications.this, Dashboard.class);
                startActivity(intent);
            }
        });

        clear_notif = findViewById(R.id.clear_notifications);
        notif_layout = findViewById(R.id.notification_layout);

        createNotificationChannel(); // Create the notification channel

        clear_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotifications();
                scheduleNotification();
            }
        });

        // Register the broadcast receiver
        IntentFilter filter = new IntentFilter("com.example.project.NOTIFICATION_RECEIVED");
        registerReceiver(notificationReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(notificationReceiver);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "MyNotificationChannel";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channelId", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void clearNotifications() {
        notif_layout.removeAllViews();
        TextView textView = new TextView(Notifications.this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText("Nothing here at the moment...");
        textView.setPadding(0, 20, 0, 0);
        notif_layout.addView(textView);

        TextView textView1 = new TextView(Notifications.this);
        textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView1.setText("Check again later");
        notif_layout.addView(textView1);
    }

    private void scheduleNotification() {
        // ... (existing code for scheduling notification)
    }

    private void updateNotificationsLayout() {
        LinearLayout notificationLayout = findViewById(R.id.notification_layout);
        notificationLayout.removeAllViews(); // Clear existing views

        for (NotificationModel notification : notificationsList) {
            TextView notificationTextView = new TextView(this);
            notificationTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            notificationTextView.setText(notification.getMessage());
            notificationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Customize text size if needed

            notificationLayout.addView(notificationTextView);
        }
    }
}
