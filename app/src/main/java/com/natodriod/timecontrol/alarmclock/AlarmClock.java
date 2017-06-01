package com.natodriod.timecontrol.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.natodriod.timecontrol.common.DATE;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by prok on 3/22/2017.
 */

public class AlarmClock {

    private static final long DEFAULT_TIME = 15;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Context context;
    public AlarmClock(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public void setAlarm(AlarmType alarmType){
        this.setAlarm(alarmType, DEFAULT_TIME, 200);
    }

    public void setAlarm(AlarmType alarmType, float minute, int requestCode){

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmConfig.ALARM_TYPE, alarmType.toString());

        long ALARM_TIME = (long) (minute * 60 * 1000);

        int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);

        Log.d("AlarmReceiverProcess", "setAlarm: " + intent.getStringExtra(AlarmConfig.ALARM_TYPE));
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + ALARM_TIME), pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_TIME,
//                ALARM_TIME, pendingIntent);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 100 + ALARM_TIME, ALARM_TIME, pendingIntent);
        Log.d("AlarmReceiver", "setAlarm: " + DATE.now() + " ALARM_TIME = " + ALARM_TIME);

    }

    public void cancelAlarm(){
        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);
        Log.d("AlarmReceiver", "cancelAlarm: ");
    }
}
