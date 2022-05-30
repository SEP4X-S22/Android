package com.example.apharma.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.apharma.R;
import com.example.apharma.ui.sensors.SensorsFragment;

public class NotificationService {
    Context context;

    public NotificationService(Context context) {
        this.context = context;
    }

    public void addNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Heads Up Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(context, CHANNEL_ID).setContentTitle("aPharma")
                    .setContentText("DANGER, check conditions").setSmallIcon(R.drawable.pharmacy_icon)
                    .setAutoCancel(true);

            Intent notificationIntent = new Intent(context, SensorsFragment.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context
                    , 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE);
            notification.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification.build());
//        NotificationManagerCompat.from(context).notify(1,notification.build());
        }
    }
}
