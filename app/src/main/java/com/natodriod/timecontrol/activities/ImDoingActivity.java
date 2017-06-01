package com.natodriod.timecontrol.activities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.alarmclock.AlarmClock;
import com.natodriod.timecontrol.alarmclock.AlarmConfig;
import com.natodriod.timecontrol.alarmclock.AlarmReceiver;
import com.natodriod.timecontrol.alarmclock.AlarmType;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.common.MyNotification;
import com.natodriod.timecontrol.common.PrefManager;
import com.natodriod.timecontrol.data.ImDoingData;
import com.natodriod.timecontrol.model.DoingList;
import com.natodriod.timecontrol.model.ImDoing;

import java.util.ArrayList;
import java.util.List;

import static com.natodriod.timecontrol.alarmclock.AlarmConfig.REQUEST_CODE_STOP_ALARM;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class ImDoingActivity extends AppCompatActivity {
    public static boolean active = false;
    public static final String TAG = "ImDoingActivity";
    private Chronometer chronometer;
    private boolean chronometerStart;
    private Button btnStartStop;
    private PrefManager prefManager;
    private ImDoing imDoing;
    private TextView tvStartDate;
    private AlarmClock alarmClock;
    private static Ringtone ringtone;
    private BroadcastReceiver receiver;
    private Button btnStopAlarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imdoing_activity);
        initVal();
        loadExtras();
        componentInit();
    }

    private void initVal() {
        prefManager = new PrefManager(this);
        alarmClock = new AlarmClock(this);
        //prefManager.clearImDoing();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras() != null) {
                    String strExtra = intent.getStringExtra(AlarmConfig.INTENT_FILTER_ALARM_EXTRA_PARAM);
                    Log.d(TAG, "onReceive: " + strExtra);
                    if (strExtra.equals("PLAY")) {
                        play();
                        Log.d("AlarmReceiver", "play from alarmDepentency");
                    } else if (strExtra.equals("STOP_ACTIVITY")) {
                        stopActivity();
                    }
                }
            }
        };
    }

    private void loadExtras() {
        imDoing = prefManager.getImDoing();
        if (imDoing == null) { // demeli preferncesde hec ne yoxdur. yeni yarimqalan ish yoxudr
            imDoing = new ImDoing();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                DoingList selectedDoing = (DoingList) getIntent().getSerializableExtra("IM_DOING");
                if (selectedDoing != null) {
                    imDoing.setDoingListId(selectedDoing.getDoingListId());
                    imDoing.setRemaining(selectedDoing.getRemaing());
                    imDoing.setDoingText(selectedDoing.getText());
                    imDoing.setStartTimeMillis(currentTimrMillid());
                    imDoing.setFinish(false);
                    prefManager.setStartTransaction(false);
                }
            }
        }
        Log.d(TAG, "loadExtras: " + imDoing);
    }

    private void startRemainding() {
        Log.d(TAG, "startRemainding: " + imDoing.getRemaining());
        if (imDoing != null && imDoing.getRemaining() > 0)
            alarmClock.setAlarm(AlarmType.REMAINDER, imDoing.getRemaining(), AlarmConfig.REQUEST_CODE_REMAINDER);
        else
            alarmClock.setAlarm(AlarmType.REMAINDER);
    }


    private void componentInit() {
        Log.d(TAG, "componentInit: " + imDoing.getDoingText());

        btnStopAlarm = (Button) findViewById(R.id.btn_stop_alarm_sound);

        TextView tvDoingText = (TextView) findViewById(R.id.tvDoingText);
        tvDoingText.setText(imDoing.getDoingText());

        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        tvStartDate.setText(imDoing.getStartDateTime());


        btnStartStop = (Button) findViewById(R.id.btn_start_stop);
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setFormat("00:%s");

        if (prefManager.isStartTransaction()) {
            // tranzaksiya davam edir
            btnStartStop.setText(getString(R.string.str_stop_activity));
            chronometerStart = true;
            chronometer.setBase(chronometer.getBase() - (currentTimrMillid() - imDoing.getStartTimeMillis()));
            chronometer.start();

            startRemainding();
            Log.d("AlarmReceiver", "prefManager.isStartTransaction(): ");
        }

        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });


    }


    private void startStop() {
        Log.d(TAG, "startStop:");
        if (!chronometerStart) {
            StartActivity();
        } else {
            stopActivity();

        }
    }

    private void StartActivity() {
        chronometerStart = true;
        chronometer.start();
        btnStartStop.setText(getString(R.string.str_stop_activity));
        startRemainding();
        addImDoing();
        showNotification(currentTimrMillid());
    }

    private void stopActivity() {
        chronometerStart = false;
        chronometer.stop();
        btnStartStop.setText(getString(R.string.str_start_activity));
        alarmClock.cancelAlarm();
        stop();
        modifyData();
        new MyNotification(this).cancelNotification(1);
    }

    private void addImDoing() {
        imDoing.setStartDateTime(DATE.now());
        imDoing.setStartTimeMillis(currentTimrMillid());
        imDoing.setFinish(false);
        imDoing.setImDoingId(new ImDoingData(this).addNewImDoing(imDoing));
        Log.d(TAG, "addImDoing: " + imDoing);
        prefManager.setImDoing(imDoing);
        prefManager.setStartTransaction(true);
        tvStartDate.setText(imDoing.getStartDateTime());
    }

    private void modifyData() {
        ImDoing imDoing = prefManager.getImDoing();
        Log.d(TAG, "modifyData2:  " + imDoing);
        if (imDoing != null) {
            imDoing.setStopDateTime(DATE.now());
            imDoing.setStopTimeMillis(currentTimrMillid());
            imDoing.setFinish(true);
            Log.d(TAG, "modifyData: " + imDoing.toString());
            if (new ImDoingData(this).updateImDoing(imDoing) >= 0) {
                prefManager.setImDoing(imDoing);
                prefManager.clearImDoing();
                prefManager.setStartTransaction(false);
            }

        }
    }

    private long currentTimrMillid() {
        return System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;

        myUnregisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmDepentency();
    }

    private void alarmDepentency() {
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(AlarmConfig.FROM_RECEIVER, false)) {
            play();
            Log.d("AlarmReceiver", "play from alarmDepentency");
        }

        if (receiver != null)
            registerReceiver(receiver, new IntentFilter(AlarmConfig.INTENT_FILTER_ALARM));
    }

    private void play() {
        btnStopAlarm.setVisibility(View.VISIBLE);
        if (ringtone == null || !ringtone.isPlaying()) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();
            Log.d("AlarmReceiver", "New playing");
        } else
            Log.d("AlarmReceiver", "play already playingd  ");
    }

    private void stop() {
        if (ringtone != null)
            ringtone.stop();
        Log.d("AlarmReceiver", "Stopped");
        btnStopAlarm.setVisibility(View.GONE);

//        myUnregisterReceiver();


    }

    private void myUnregisterReceiver() {
        try {
            if (receiver != null)
                unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showNotification(long millis) {
        if (imDoing == null)
            return;

        Intent intentBrodcast = new Intent(this, AlarmReceiver.class);
        intentBrodcast.putExtra(AlarmConfig.ALARM_TYPE, AlarmType.ALARM_STOP.toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE_STOP_ALARM, intentBrodcast, PendingIntent.FLAG_UPDATE_CURRENT);

        List<NotificationCompat.Action> actions = new ArrayList<>();
        actions.add(new NotificationCompat.Action(0, getString(R.string.str_stop_activity), pendingIntent));

      //  long millis = (chronometer.getBase() - (currentTimrMillid() - imDoing.getStartTimeMillis()));
        MyNotification notification = new MyNotification(this);
        notification.showNotification(ImDoingActivity.class,
                new String[]{imDoing.getDoingText(), imDoing.getDoingText()},
                millis, actions, 1);
    }
}
