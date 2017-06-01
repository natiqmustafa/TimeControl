package com.natodriod.timecontrol.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.natodriod.timecontrol.MainActivity;
import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.activities.ImDoingActivity;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.common.MyNotification;
import com.natodriod.timecontrol.common.PrefManager;
import com.natodriod.timecontrol.data.ImDoingData;
import com.natodriod.timecontrol.model.ImDoing;

import java.util.Calendar;

import static com.natodriod.timecontrol.alarmclock.AlarmConfig.LIM_NIGHT_END;
import static com.natodriod.timecontrol.alarmclock.AlarmConfig.LIM_NIGHT_START;

/**
 * Created by natiqmustafa on 13.04.2017.
 */

public class AlarmReceiverProcess {
    public static final String TAG = "AlarmReceiver";

    AlarmReceiverProcess(Context context, Intent intent, AlarmType alarmType) {

        if (intent.getExtras() != null)
            Log.d(TAG, "AlarmReceiverProcess_inrent: " + intent.getStringExtra(AlarmConfig.ALARM_TYPE));

        Log.d(TAG, "AlarmReceiverProcess: " + alarmType.toString());
        switch (alarmType){
            case REMAINDER:
                this.remainder(context, intent);
                break;
            case CONTROLLER:
                this.controller(context);
                break;
            case ALARM_STOP:
                this.alarmStop(context);
                break;
        }

    }


    private void alarmStop(Context context) {
        if (ImDoingActivity.active) {
            Intent i = new Intent(AlarmConfig.INTENT_FILTER_ALARM);
            i.putExtra(AlarmConfig.INTENT_FILTER_ALARM_EXTRA_PARAM, "STOP_ACTIVITY");
            context.sendBroadcast(i);
        } else {
            modifyData(context);
        }
    }


    private void controller(Context context) {
        Calendar calendar = Calendar.getInstance();
        int cHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (cHour >= LIM_NIGHT_START && cHour <= LIM_NIGHT_END){
            Toast.makeText(context, "Controller is not working now", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Controller is not working now");
            return;
        }

        if (!new PrefManager(context).isStartTransaction()) {
            Toast.makeText(context, "NOTHING!!! NOTHING!!! NOTHING!!! ", Toast.LENGTH_SHORT).show();
            MyNotification notification = new MyNotification(context, false, true);
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.bell);
            notification.showNotification(MainActivity.class, new String[]{"Xatırlatma", "Nə edirsən ? Niyə bekarçılıqdır ? "}, 2, soundUri);
        }
        else
            Toast.makeText(context, "Good,  you are woking now...", Toast.LENGTH_SHORT).show();


    }

    private void remainder(Context context, Intent intent) {
        if (ImDoingActivity.active) {
            Intent i = new Intent(AlarmConfig.INTENT_FILTER_ALARM);
            i.putExtra(AlarmConfig.INTENT_FILTER_ALARM_EXTRA_PARAM, "PLAY");
            context.sendBroadcast(i);
        } else {
            Intent intent1 = new Intent(context, ImDoingActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra(AlarmConfig.FROM_RECEIVER, true);
            context.startActivity(intent1);
        }
        Toast.makeText(context, "Wake up !!! Wake up!!! ImDoingActivity = " + ImDoingActivity.active, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: " + DATE.now() + " Wake up !!! Wake up!!! ImDoingActivity = " +
                ImDoingActivity.active + " alarmType: " + intent.getExtras().getString(AlarmConfig.ALARM_TYPE, AlarmType.CONTROLLER.toString()));
    }


    private void modifyData(Context context) {
        PrefManager prefManager = new PrefManager(context);
        ImDoing imDoing = prefManager.getImDoing();
        Log.d(TAG, "modifyData2:  " + imDoing);
        if (imDoing != null) {
            imDoing.setStopDateTime(DATE.now());
            imDoing.setStopTimeMillis(System.currentTimeMillis());
            imDoing.setFinish(true);
            Log.d(TAG, "modifyData: " + imDoing.toString());
            if (new ImDoingData(context).updateImDoing(imDoing) >= 0) {
                prefManager.setImDoing(imDoing);
                prefManager.clearImDoing();
                prefManager.setStartTransaction(false);
            }
            new MyNotification(context).cancelNotification(1);
        }
    }
}
