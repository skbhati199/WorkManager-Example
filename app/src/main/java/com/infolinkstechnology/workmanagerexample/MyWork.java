package com.infolinkstechnology.workmanagerexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {

    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotificatin("task", "task has been done");
        return Result.success();
    }

    private void displayNotificatin(String task, String task_has_been_done) {

        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String notificationChannelId = "smartbox";
        String notificationChannelDescription = "Hire Indians Infotech Pvt Ltd";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId,
                    notificationChannelDescription,NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getApplicationContext(),
                notificationChannelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("" + task)
                .setContentText(task_has_been_done);

        manager.notify(0,builder.build());

    }
}
