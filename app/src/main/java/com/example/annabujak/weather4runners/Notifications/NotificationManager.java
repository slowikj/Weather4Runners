package com.example.annabujak.weather4runners.Notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by android on 2017-05-31.
 */

public class NotificationManager {
    private Map<Long,PendingIntent> PendingIntents;
    private AlarmManager alarmManager;
    private Context context;
    private Intent intent;

    public NotificationManager(Context _context){
        context = _context;
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        intent = new Intent(context , AlarmReceiver.class);
        PendingIntents = new HashMap<Long,PendingIntent>();
    }
    public void AddNotification(ChosenProposition proposition){

        PendingIntent resultPendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
       // alarmManager.set(AlarmManager.RTC_WAKEUP,GetMillisecondsFromNow(new Date(weatherInfo.getDate())),resultPendingIntent);
        PendingIntents.put(GetMillisecondsFromNow(new Date(proposition.getDate())), resultPendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ 5,resultPendingIntent);
    }
    public void RemoveNotification(ChosenProposition proposition){
        Iterator it = PendingIntents.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Date current = new Date(proposition.getDate());
            Date fromMap = new Date(((Long)pair.getKey()));
            if(GetMillisecondsFromNow(current) == GetMillisecondsFromNow(fromMap)) {
                alarmManager.cancel((PendingIntent) pair.getValue());
                it.remove();
                break;
            }
        }
    }

    private long GetMillisecondsFromNow(Date runDate){
        Date date = new Date();
        long hours;
        if(date.getHours() > runDate.getHours()-1)
            hours = date.getHours() + runDate.getHours()-1;
        else
            hours = runDate.getHours()-1 - date.getHours();
        return 3600000 * hours;
    }

}
