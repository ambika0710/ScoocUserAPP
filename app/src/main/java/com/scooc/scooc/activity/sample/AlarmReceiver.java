package com.scooc.scooc.activity.sample;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.scooc.scooc.R;

import com.scooc.scooc.activity.sample.NotificationUtilsKt;

/**
 * created by Abhishek Kumar
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Booking notification trigger", Toast.LENGTH_SHORT).show();
             int a = intent.getIntExtra("extravalue",199);

        NotificationManager notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager.class
        );

        NotificationUtilsKt.sendNotification(context.getString(R.string.timer_running),
                context, notificationManager,a);
    }

}
