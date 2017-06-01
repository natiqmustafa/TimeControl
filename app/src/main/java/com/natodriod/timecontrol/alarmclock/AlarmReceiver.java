package com.natodriod.timecontrol.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.natodriod.timecontrol.activities.ImDoingActivity;
import com.natodriod.timecontrol.common.DATE;

/**
 * Created by prok on 3/22/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmType alarmType = AlarmType.toAlarmType(intent.getExtras().getString(AlarmConfig.ALARM_TYPE, AlarmType.CONTROLLER.toString()));
        new AlarmReceiverProcess(context, intent, alarmType);
    }
}
