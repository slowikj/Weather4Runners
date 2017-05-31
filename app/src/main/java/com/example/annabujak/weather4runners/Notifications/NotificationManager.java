package com.example.annabujak.weather4runners.Notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by android on 2017-05-31.
 */

public class NotificationManager {
    private List<Integer> UserNotifications;
    private int hourOfNotification = 9;
    private AlarmManager alarmManager;
    private Context context;
    private Intent intent;

    public NotificationManager(Context _context){
        context = _context;
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        intent = new Intent(context , NotificationManager.class);
    }
    public void AddNotification(Context context, WeatherInfo weatherInfo){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setContentTitle("Remember about your run!")
                        .setContentText("Remember that your run is today at "+ new Date(weatherInfo.getDate()).getHours() + ": 00");

        PendingIntent resultPendingIntent = PendingIntent.getService(context,0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,GetMilosecondsFromNow(),resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        @SuppressLint("ServiceCast") NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify();
    }
    private long GetMilosecondsFromNow(){
        Date date = new Date();
        long hours;
        if(date.getHours() > hourOfNotification)
            hours = date.getHours() + hourOfNotification;
        else
            hours = hourOfNotification - date.getHours();
        return 3600000 * hours;
    }

}
