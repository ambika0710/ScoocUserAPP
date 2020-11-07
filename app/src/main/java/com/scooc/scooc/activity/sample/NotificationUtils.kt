package com.scooc.scooc.activity.sample

import com.scooc.scooc.R
import com.scooc.scooc.activity.LandingPageActivity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import java.util.*

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val REQUEST_CODE_CANCEL = 10
private val FLAGS = 0

const val REQUEST_CODE_PENDING_INTENT = 10
// TODO: Step 1.1 extension function to send messages (GIVEN)
/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */

fun sendNotification(messageBody: String, applicationContext: Context, notificationManager: NotificationManager,a:Int) {
    // Create the content intent for the notification, which launches
    // this activity

    // TODO: pending intent for setting alarm again
    checkForEndDate(applicationContext,a)

    // TODO: Step 1.11 create intent
    val contentIntent = Intent(applicationContext, LandingPageActivity::class.java)

    // TODO: Step 1.12 create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    // TODO: Step 2.0 add style

    // TODO: Step 2.2 add snooze action
    val snoozeIntent = Intent(applicationContext, BookYourRideReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE,
            snoozeIntent,
            FLAGS
    )

    // TODO: add cancel action
    val cancelIntent = Intent(applicationContext, CancelReceiver::class.java)
    val cancelPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE_CANCEL,
            cancelIntent,
            FLAGS
    )

    // Build the notification
    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.lataxi_notification_channel_id)
    )

            // TODO: Step 1.8 use the new 'breakfast' notification channel

            // TODO: Step 1.3 set title, text and icon to builder
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle(


                    applicationContext
                            .getString(R.string.notification_title)
            )
            .setContentText(messageBody)

            // TODO: Step 1.13 set content intent
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

            // TODO: Step 2.1 add style to builder

            // TODO: Step 2.3 add snooze action
            .addAction(

                    R.drawable.ic_car_la_landing_page,
                    applicationContext.getString(R.string.book),
                    snoozePendingIntent

            )
            .addAction(
                    R.drawable.ic_car_la_landing_page,
                    applicationContext.getString(R.string.cancel),
                    cancelPendingIntent
            )

            // TODO: Step 2.5 set priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    // TODO: Step 1.4 call notify
    notificationManager.notify(NOTIFICATION_ID, builder.build())

}

fun checkForEndDate(applicationContext: Context,a: Int) {
    val sharedPreferenceHelper = SharedPreferenceHelper(applicationContext)

    val calenderAlarm = Calendar.getInstance()
    val day = calenderAlarm.get(Calendar.DAY_OF_MONTH)
    val month = calenderAlarm.get(Calendar.MONTH)
    val year = calenderAlarm.get(Calendar.YEAR)
    val hour = calenderAlarm.get(Calendar.HOUR_OF_DAY)
    val minute = calenderAlarm.get(Calendar.MINUTE)
            // a=299
    // code
    // a-- 299 against row time date year
    if (year < sharedPreferenceHelper.endDateYear) {
        setAlarm(applicationContext, MINUTE_IN_MILLIS * 2,a)
        //setAlarm(applicationContext, DateUtils.DAY_IN_MILLIS)
    } else if (year == sharedPreferenceHelper.endDateYear) {
        if (month < sharedPreferenceHelper.endDateMonth) {
            setAlarm(applicationContext, MINUTE_IN_MILLIS * 2,a)
            //setAlarm(applicationContext, DateUtils.DAY_IN_MILLIS)
        } else if (month == sharedPreferenceHelper.endDateMonth && day < sharedPreferenceHelper.endDateDay) {
            setAlarm(applicationContext, MINUTE_IN_MILLIS * 2,a)
            //setAlarm(applicationContext, DateUtils.DAY_IN_MILLIS)
        }
    }

}

fun setAlarm(applicationContext: Context, timeInMilli: Long,a: Int) {
    val notifyIntent = Intent(applicationContext, AlarmReceiver::class.java)
    val notifyPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            a,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + timeInMilli,
            notifyPendingIntent
    )

}

// TODO: Step 1.14 Cancel all notifications
fun cancelNotifications(notificationManager: NotificationManager) {
    notificationManager.cancelAll()
}
