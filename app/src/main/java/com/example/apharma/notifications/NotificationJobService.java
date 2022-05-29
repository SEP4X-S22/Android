package com.example.apharma.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;

import com.example.apharma.MainActivity;
import com.example.apharma.R;
import com.example.apharma.adapters.SensorAdapter;

//public class NotificationJobService extends JobService {
//
//    NotificationManager mNotifyManager;
//    JobScheduler jobScheduler;
//    private SensorAdapter sensorAdapter = SensorAdapter.getInstance();
//
//    // Notification channel ID.
//    private static final String PRIMARY_CHANNEL_ID =
//            "primary_notification_channel";
//
//    public void createNotificationChannel() {
//
//        // Define notification manager object.
//        mNotifyManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        // Notification channels are only available in OREO and higher.
//        // So, add a check on SDK version.
//        if (android.os.Build.VERSION.SDK_INT >=
//                android.os.Build.VERSION_CODES.O) {
//
//            // Create the NotificationChannel with all the parameters.
//            NotificationChannel notificationChannel = new NotificationChannel
//                    (PRIMARY_CHANNEL_ID,
//                            "Important events",
//                            NotificationManager.IMPORTANCE_HIGH);
//
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setDescription
//                    ("Notifications from aPharma");
//
//            mNotifyManager.createNotificationChannel(notificationChannel);
//        }
//    }
//
//
//    @Override
//    public boolean onStartJob(JobParameters jobParameters) {
////Create the notification channel
//        createNotificationChannel();
//        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//
//
////Set up the notification content intent to launch the app when clicked
//        PendingIntent contentPendingIntent = PendingIntent.getActivity
//                (this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            Notification.Builder notification = new Notification.Builder(this, PRIMARY_CHANNEL_ID).setContentTitle("aPharma")
//                    .setContentText("DANGER, check conditions").setSmallIcon(R.drawable.pharmacy_icon)
//                    .setAutoCancel(true);
//            notification.setContentIntent(contentPendingIntent);
//            mNotifyManager.notify(0, notification.build());
//
//
////            ComponentName serviceName = new ComponentName(getPackageName(),
////                    NotificationJobService.class.getName());
////            JobInfo.Builder builder = new JobInfo.Builder(0, serviceName)
////                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
////            JobInfo myJobInfo = builder.build();
////            jobScheduler.schedule(myJobInfo);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters jobParameters) {
//        return false;
//    }
//}
