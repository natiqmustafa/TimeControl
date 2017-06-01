package com.natodriod.timecontrol.alarmclock;

/**
 * Created by natiqmustafa on 29.03.2017.
 */

public class AlarmConfig {
    public static final String INTENT_FILTER_ALARM = "com.natodroid.kcontrol.alarmreceiver.SEND_ALARM";
    public static final String INTENT_FILTER_ALARM_STOP = "com.natodroid.kcontrol.alarmreceiver.SEND_ALARM_STOP";
    public static final String FROM_RECEIVER = "from_receiver";
    public static final String ALARM_TYPE = "alarm_type";

    public static final String INTENT_FILTER_ALARM_EXTRA_PARAM = "alarm_extra_param";



    public static final int CONTROLLER_ALARM_TIME = 1;
    public static final int LIM_NIGHT_START = 0; // 00 MIDNIGHT
    public static final int LIM_NIGHT_END = 8; // 8


    public static final int REQUEST_CODE_STOP_ALARM = 100;
    public static final int REQUEST_CODE_REMAINDER = 200;
    public static final int REQUEST_CODE_CONTROLLER = 300;
}
