package com.natodriod.timecontrol.alarmclock;

/**
 * Created by natiqmustafa on 29.03.2017.
 */

public enum AlarmType {
    REMAINDER(1), CONTROLLER(2), ALARM_STOP(3);

    private int value;

    AlarmType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AlarmType toAlarmType(String val){
        try {
            return valueOf(val);
        } catch (Exception ex) {
            return REMAINDER;
        }
    }
}
